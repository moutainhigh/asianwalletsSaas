package com.asianwallets.trade.rabbitmq.receive;

import com.alibaba.fastjson.JSON;
import com.asianwallets.common.constant.AD3MQConstant;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.constant.TradeConstant;
import com.asianwallets.common.dto.RabbitMassage;
import com.asianwallets.common.entity.Channel;
import com.asianwallets.common.entity.OrderRefund;
import com.asianwallets.common.entity.Orders;
import com.asianwallets.common.entity.Reconciliation;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.vo.clearing.FundChangeDTO;
import com.asianwallets.trade.channels.ChannelsAbstract;
import com.asianwallets.trade.dao.*;
import com.asianwallets.trade.feign.MessageFeign;
import com.asianwallets.trade.rabbitmq.RabbitMQSender;
import com.asianwallets.trade.service.ClearingService;
import com.asianwallets.trade.service.CommonBusinessService;
import com.asianwallets.trade.service.CommonRedisDataService;
import com.asianwallets.trade.utils.HandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 退款消费队列
 * @author: YangXu
 * @create: 2019-12-20 15:04
 **/
@Component
@Slf4j
public class RefundOrderMQReceive {

    @Value("${custom.warning.mobile}")
    private String developerMobile;

    @Value("${custom.warning.email}")
    private String developerEmail;

    @Autowired
    private OrderRefundMapper orderRefundMapper;
    @Autowired
    private CommonBusinessService commonBusinessService;
    @Autowired
    private MessageFeign messageFeign;
    @Autowired
    private ClearingService clearingService;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Autowired
    private CommonRedisDataService commonRedisDataService;
    @Autowired
    private ReconciliationMapper reconciliationMapper;
    @Autowired
    private HandlerContext handlerContext;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private TcsCtFlowMapper tcsCtFlowMapper;
    @Autowired
    private TcsStFlowMapper tcsStFlowMapper;

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/20
     * @Descripate RF or RV请求失败
     **/
    @RabbitListener(queues = "RV_RF_FAIL_DL")
    public void processRFSB(String value) {
        RabbitMassage rabbitMassage = JSON.parseObject(value, RabbitMassage.class);
        OrderRefund orderRefund = JSON.parseObject(rabbitMassage.getValue(), OrderRefund.class);
        log.info("=========================【RV_RF_FAIL_DL】==================== 【消费】 RabbitMassage : 【{}】", JSON.toJSONString(rabbitMassage));
        if (rabbitMassage.getCount() > 0) {
            //请求次数减一
            rabbitMassage.setCount(rabbitMassage.getCount() - 1);
            FundChangeDTO fundChangeDTO = new FundChangeDTO(orderRefund.getRemark4(), orderRefund);
            log.info("=========================【RV_RF_FAIL_DL】==================== 【上报清结算 {}】fundChangeDTO : 【{}】", orderRefund.getRemark4(), JSON.toJSONString(fundChangeDTO));
            BaseResponse cFundChange = clearingService.fundChange(fundChangeDTO);
            log.info("=========================【RV_RF_FAIL_DL】==================== 【上报清结算 {}返回】 cFundChange : 【{}】", orderRefund.getRemark4(), JSON.toJSONString(cFundChange));
            if (!cFundChange.getCode().equals(TradeConstant.CLEARING_SUCCESS)) {
                log.info("========================= 【RV_RF_FAIL_DL】 ==================== 【上报清结算 失败】 : 【{}】", JSON.toJSONString(rabbitMassage));
                rabbitMQSender.send(AD3MQConstant.RV_RF_FAIL_DL, JSON.toJSONString(rabbitMassage));
                return;
            }
            Channel channel = this.commonRedisDataService.getChannelByChannelCode(orderRefund.getChannelCode());
            ChannelsAbstract channelsAbstract = null;
            try {
                log.info("========================= 【RV_RF_FAIL_DL】 ==================== 【Channel Name】  channel: 【{}】", JSON.toJSONString(channel));
                channelsAbstract = handlerContext.getInstance(channel.getServiceNameMark());
            } catch (Exception e) {
                log.info("========================= 【RV_RF_FAIL_DL】 ==================== 【Exception】 e : 【{}】", e);
            }
            channelsAbstract.refund(channel, orderRefund, null);
        } else {
            //三次上报清结算失败，则退款单就是退款失败更新退款单状态以及失败原因
            orderRefundMapper.updateStatuts(orderRefund.getId(), TradeConstant.REFUND_FALID, null, "上报清结算失败:RV_RF_FAIL_DL");
            //更新订单表
            commonBusinessService.updateOrderRefundFail(orderRefund);
            messageFeign.sendSimple(developerMobile, "上报清结算失败 RV_RF_FAIL_DL ：{ " + value + " }");
            messageFeign.sendSimpleMail(developerEmail, "上报清结算失败 RV_RF_FAIL_DL 预警", "RV_RF_FAIL_DL 预警 ：{ " + value + " }");
        }

    }

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/20
     * @Descripate 调账请求失败
     **/
    @RabbitListener(queues = "RA_AA_FAIL_DL")
    public void processRAORRFSB(String value) {
        RabbitMassage rabbitMassage = JSON.parseObject(value, RabbitMassage.class);
        log.info("========================= 【RA_AA_FAIL_DL】 ==================== 【消费】 rabbitMassage : 【{}】 ", value);
        if (rabbitMassage.getCount() > 0) {
            rabbitMassage.setCount(rabbitMassage.getCount() - 1);
            Reconciliation reconciliation = JSON.parseObject(rabbitMassage.getValue(), Reconciliation.class);
            FundChangeDTO fundChangeDTO = new FundChangeDTO(reconciliation);
            log.info("========================= 【RA_AA_FAIL_DL】 ==================== 【上报清结算】 AccountType:【{}】，【fundChangeDTO】: {} ", reconciliation.getAccountType(), JSON.toJSONString(fundChangeDTO));
            BaseResponse cFundChange = clearingService.fundChange(fundChangeDTO);
            log.info("========================= 【RA_AA_FAIL_DL】 ==================== 【上报清结算 返回】 cFundChange : {} ", JSON.toJSONString(cFundChange));
            if (cFundChange.getCode().equals(TradeConstant.CLEARING_SUCCESS)) {
                //请求成功
                log.info("========================= 【RA_AA_FAIL_DL】 ==================== 【请求成功】 cFundChange : {} ", JSON.toJSONString(cFundChange));
                orderRefundMapper.updateStatuts(reconciliation.getRefundOrderId(), TradeConstant.REFUND_FALID, null, null);
                reconciliationMapper.updateStatusById(reconciliation.getId(), TradeConstant.RECONCILIATION_SUCCESS);
                //改原订单状态
                OrderRefund orderRefund = orderRefundMapper.selectByPrimaryKey(reconciliation.getId());
                if (reconciliation.getAccountType() == 1) {
                    orderRefund.setRemark4(TradeConstant.RA);
                } else if (reconciliation.getAccountType() == 2) {
                    orderRefund.setRemark4(TradeConstant.AA);
                }
                commonBusinessService.updateOrderRefundFail(orderRefund);
            } else {
                //请求失败
                log.info("========================= 【RA_AA_FAIL_DL】 ==================== 【请求失败】 cFundChange : {} ", JSON.toJSONString(cFundChange));
                log.info("=================【RA_AA_FAIL_DL】=================【退款操作 上报队列 RA_AA_FAIL_DL】 rabbitMassage: {} ", JSON.toJSONString(rabbitMassage));
                rabbitMQSender.send(AD3MQConstant.RA_AA_FAIL_DL, JSON.toJSONString(rabbitMassage));
            }
        } else {
            //预警机制
            messageFeign.sendSimple(developerMobile, "上报清结算调账失败队列 RA_AA_FAIL_DL 预警 ：{ " + value + " }");//短信通知
            messageFeign.sendSimpleMail(developerEmail, "上报清结算调账失败队列 RA_AA_FAIL_DL 预警", "RA_AA_FAIL_DL 预警 ：{ " + value + " }");//邮件通知
        }
    }


    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/20
     * @Descripate 退款上报失败队列
     **/
    @RabbitListener(queues = "TK_SB_FAIL_DL")
    public void processTKSBSB(String value) {
        RabbitMassage rabbitMassage = JSON.parseObject(value, RabbitMassage.class);
        log.info("========================= 【TK_SB_FAIL_DL】 ====================【消费】 rabbitMassage : 【{}】 ", value);
        if (rabbitMassage.getCount() > 0) {
            rabbitMassage.setCount(rabbitMassage.getCount() - 1);//请求次数减一
            OrderRefund orderRefund = JSON.parseObject(rabbitMassage.getValue(), OrderRefund.class);
            Channel channel = this.commonRedisDataService.getChannelByChannelCode(orderRefund.getChannelCode());
            ChannelsAbstract channelsAbstract = null;
            try {
                channelsAbstract = handlerContext.getInstance(channel.getServiceNameMark());
            } catch (Exception e) {
                log.info("========================= 【TK_RF_FAIL_DL】 ChannelsAbstract ==================== Exception : 【{}】,rabbitMassage : 【{}】", e, JSON.toJSONString(rabbitMassage));
            }
            channelsAbstract.refund(channel, orderRefund, rabbitMassage);
        } else {
            //预警机制
            messageFeign.sendSimple(developerMobile, "退款上请求上游失败 TK_SB_FAIL_DL 预警 ：{ " + value + " }");//短信通知
            messageFeign.sendSimpleMail(developerEmail, "退款上请求上游失败 TK_SB_FAIL_DL 预警", "RA_AA_FAIL_DL 预警 ：{ " + value + " }");//邮件通知
        }


    }

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/20
     * @Descripate 撤销更新订单失败
     **/
    @RabbitListener(queues = "CX_GX_FAIL_DL")
    public void processCXGXSB(String value) {
        RabbitMassage rabbitMassage = JSON.parseObject(value, RabbitMassage.class);
        log.info("========================= 【CX_GX_FAIL_DL】 ====================【消费】 rabbitMassage : 【{}】 ", value);
        if (rabbitMassage.getCount() > 0) {
            rabbitMassage.setCount(rabbitMassage.getCount() - 1);//请求次数减-1
            OrderRefund orderRefund = JSON.parseObject(rabbitMassage.getValue(), OrderRefund.class);
            //根据订单id判断订单状态是否付款成功
            Orders order = ordersMapper.selectByPrimaryKey(orderRefund.getOrderId());
            Channel channel = this.commonRedisDataService.getChannelByChannelCode(orderRefund.getChannelCode());
            log.info("========================= 【CX_GX_FAIL_DL】 ==================== orderId : 【{}】, status :【{}】 ", order.getId(), order.getTradeStatus());
            
            ChannelsAbstract channelsAbstract = null;
            try {
                log.info("=========================【CX_GX_FAIL_DL】========================= Channel ServiceName:【{}】", channel.getServiceNameMark());
                channelsAbstract = handlerContext.getInstance(channel.getServiceNameMark());
            } catch (Exception e) {
                log.info("=========================【CX_GX_FAIL_DL】========================= 【doRefundOrder Exception】 Exception:【{}】", e);
            }

            if (TradeConstant.ORDER_PAY_SUCCESS.equals(order.getTradeStatus())) {
                log.info("========================= 【CX_GX_FAIL_DL】 ====================【支付成功】 orderId : 【{}】, status :【{}】 ", order.getId(), order.getTradeStatus());
                //获取清算状态
                Integer ctState = tcsCtFlowMapper.getCTstatus(order.getId());
                //获取结算状态
                Integer stState = tcsStFlowMapper.getSTstatus(order.getId());
                String type = null;
                if (TradeConstant.ORDER_CLEAR_SUCCESS.equals(ctState) && TradeConstant.ORDER_SETTLE_SUCCESS.equals(stState)) {
                    type = TradeConstant.RF;
                } else if ((TradeConstant.ORDER_CLEAR_SUCCESS.equals(ctState) && TradeConstant.ORDER_SETTLE_WAIT.equals(stState))
                        || (TradeConstant.ORDER_CLEAR_WAIT.equals(ctState) && stState == null)
                ) {
                    type = TradeConstant.RV;
                }
                orderRefund.setRemark4(type);

                channelsAbstract.refund(channel, orderRefund, null);

            } else if (TradeConstant.ORDER_PAYING.equals(order.getTradeStatus())) {//付款中的队列继续放进查询队列
                log.info("========================= 【CX_GX_FAIL_DL】 ====================【付款中】 orderId : 【{}】, status :【{}】 ", order.getId(), order.getTradeStatus());
                channelsAbstract.cancel(channel, orderRefund, null);
            } else {
                log.info("========================= 【CX_GX_FAIL_DL】 ====================【其他状态】 orderId : 【{}】, status :【{}】 ", order.getId(), order.getTradeStatus());
            }
        } else {
            //预警机制
            messageFeign.sendSimple(developerMobile, "撤销更新订单失败 CX_GX_FAIL_DL 预警 ：{ " + value + " }");//短信通知
            messageFeign.sendSimpleMail(developerEmail, "撤销更新订单失败 CX_GX_FAIL_DL 预警", "CX_GX_FAIL_DL 预警 ：{ " + value + " }");//邮件通知
        }
    }
}
