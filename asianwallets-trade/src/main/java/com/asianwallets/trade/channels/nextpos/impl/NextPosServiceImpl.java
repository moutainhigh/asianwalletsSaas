package com.asianwallets.trade.channels.nextpos.impl;

import com.alibaba.fastjson.JSON;
import com.asianwallets.common.constant.AD3MQConstant;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.constant.TradeConstant;
import com.asianwallets.common.dto.RabbitMassage;
import com.asianwallets.common.dto.megapay.NextPosQueryDTO;
import com.asianwallets.common.dto.megapay.NextPosRefundDTO;
import com.asianwallets.common.entity.Channel;
import com.asianwallets.common.entity.OrderRefund;
import com.asianwallets.common.entity.Orders;
import com.asianwallets.common.entity.Reconciliation;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.vo.clearing.FundChangeDTO;
import com.asianwallets.trade.channels.ChannelsAbstractAdapter;
import com.asianwallets.trade.channels.nextpos.NextPosService;
import com.asianwallets.trade.dao.OrderRefundMapper;
import com.asianwallets.trade.dao.OrdersMapper;
import com.asianwallets.trade.dao.ReconciliationMapper;
import com.asianwallets.trade.feign.ChannelsFeign;
import com.asianwallets.trade.rabbitmq.RabbitMQSender;
import com.asianwallets.trade.service.ClearingService;
import com.asianwallets.trade.service.CommonBusinessService;
import com.asianwallets.trade.utils.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: YangXu
 * @create: 2019-12-19 16:47
 **/
@Slf4j
@Service
@Transactional
@HandlerType(TradeConstant.NEXTPOS)
public class NextPosServiceImpl extends ChannelsAbstractAdapter implements NextPosService {

    @Autowired
    private ChannelsFeign channelsFeign;
    @Autowired
    private OrderRefundMapper orderRefundMapper;
    @Autowired
    private CommonBusinessService commonBusinessService;
    @Autowired
    private ReconciliationMapper reconciliationMapper;
    @Autowired
    private ClearingService clearingService;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/19
     * @Descripate 退款接口
     **/
    @Override
    public BaseResponse refund(Channel channel, OrderRefund orderRefund, RabbitMassage rabbitMassage) {
        BaseResponse baseResponse = new BaseResponse();
        NextPosRefundDTO nextPosRefundDTO = new NextPosRefundDTO(orderRefund, channel);
        log.info("=================【NextPos退款】=================【请求Channels服务NextPos退款】请求参数 nextPosRefundDTO: {} ", JSON.toJSONString(nextPosRefundDTO));
        BaseResponse response = channelsFeign.nextPosRefund(nextPosRefundDTO);
        log.info("=================【NextPos退款】=================【Channels服务响应】 response: {} ", JSON.toJSONString(response));
        if (response.getCode().equals(TradeConstant.HTTP_SUCCESS)) {
            //请求成功
            Map<String, Object> respMap = (Map<String, Object>) response.getData();
            if (response.getMsg().equals(TradeConstant.HTTP_SUCCESS_MSG)) {
                log.info("=================【NextPos退款】=================【退款成功】 response: {} ", JSON.toJSONString(response));
                //退款成功
                orderRefundMapper.updateStatuts(orderRefund.getId(), TradeConstant.REFUND_SUCCESS, String.valueOf(respMap.get("transactionID")), null);
                //改原订单状态
                commonBusinessService.updateOrderRefundSuccess(orderRefund);
            } else {
                //退款失败
                log.info("=================【NextPos退款】=================【退款失败】 response: {} ", JSON.toJSONString(response));
                baseResponse.setMsg(EResultEnum.REFUND_FAIL.getCode());
                String type = orderRefund.getRemark4().equals(TradeConstant.RF) ? TradeConstant.AA : TradeConstant.RA;
                Reconciliation reconciliation = commonBusinessService.createReconciliation(type, orderRefund, TradeConstant.REFUND_FAIL_RECONCILIATION);
                reconciliationMapper.insert(reconciliation);
                FundChangeDTO fundChangeDTO = new FundChangeDTO(reconciliation);
                log.info("=========================【NextPos退款】======================= 【调账 {}】， fundChangeDTO:【{}】", type, JSON.toJSONString(fundChangeDTO));
                BaseResponse cFundChange = clearingService.fundChange(fundChangeDTO);
                if (cFundChange.getCode().equals(TradeConstant.CLEARING_SUCCESS)) {
                    //调账成功
                    log.info("=================【NextPos退款】=================【调账成功】 cFundChange: {} ", JSON.toJSONString(cFundChange));
                    orderRefundMapper.updateStatuts(orderRefund.getId(), TradeConstant.REFUND_FALID, null, null);
                    reconciliationMapper.updateStatusById(reconciliation.getId(), TradeConstant.RECONCILIATION_SUCCESS);
                    //改原订单状态
                    commonBusinessService.updateOrderRefundFail(orderRefund);
                } else {
                    //调账失败
                    log.info("=================【NextPos退款】=================【调账失败】 cFundChange: {} ", JSON.toJSONString(cFundChange));
                    RabbitMassage rabbitMsg = new RabbitMassage(AsianWalletConstant.THREE, JSON.toJSONString(reconciliation));
                    log.info("=================【NextPos退款】=================【调账失败 上报队列 RA_AA_FAIL_DL】 rabbitMassage: {} ", JSON.toJSONString(rabbitMsg));
                    rabbitMQSender.send(AD3MQConstant.RA_AA_FAIL_DL, JSON.toJSONString(rabbitMsg));
                }
            }
        } else {
            //请求失败
            baseResponse.setMsg(EResultEnum.REFUNDING.getCode());
            if (rabbitMassage == null) {
                rabbitMassage = new RabbitMassage(AsianWalletConstant.THREE, JSON.toJSONString(orderRefund));
            }
            log.info("===============【NextPos退款】===============【请求失败 上报队列 TK_SB_FAIL_DL】 rabbitMassage: {} ", JSON.toJSONString(rabbitMassage));
            rabbitMQSender.send(AD3MQConstant.TK_SB_FAIL_DL, JSON.toJSONString(rabbitMassage));
        }
        return response;
    }


    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/23
     * @Descripate 撤销
     **/
    @Override
    public BaseResponse cancel(Channel channel, OrderRefund orderRefund, RabbitMassage rabbitMassage) {
        BaseResponse response = new BaseResponse();
        NextPosQueryDTO nextPosQueryDTO = new NextPosQueryDTO(orderRefund.getOrderId(), channel);
        log.info("=================【NextPos撤销】=================【请求Channels服务NextPos查询】请求参数 nextPosQueryDTO: {} ", JSON.toJSONString(nextPosQueryDTO));
        BaseResponse baseResponse = channelsFeign.nextPosQuery(nextPosQueryDTO);
        log.info("=================【NextPos撤销】=================【Channels服务响应】请求参数 baseResponse: {} ", JSON.toJSONString(baseResponse));
        if (baseResponse.getCode().equals(TradeConstant.HTTP_SUCCESS)) {
            //请求成功
            Map<String, Object> map = (Map<String, Object>) baseResponse.getData();
            if (baseResponse.getMsg().equals(TradeConstant.HTTP_SUCCESS_MSG)) {
                //更新订单状态
                //TODO 查询报文
                if (ordersMapper.updateOrderByAd3Query(orderRefund.getOrderId(), TradeConstant.ORDER_PAY_SUCCESS,
                        null, new Date()) == 1) {
                    //更新成功
                } else {
                    //更新失败后去查询订单信息

                }
            } else {
                //请求失败
                log.info("=================【NextPos撤销】=================【查询订单失败】请求参数 baseResponse: {} ", JSON.toJSONString(baseResponse));
                //rabbitMQSender.send(AD3MQConstant.E_MQ_AD3_ORDER_QUERY, JSON.toJSONString(rabbitMassage));
                //TODO
            }
        } else {
            //请求失败
            log.info("=================【NextPos撤销】=================【查询订单失败】请求参数 baseResponse: {} ", JSON.toJSONString(baseResponse));
            //rabbitMQSender.send(AD3MQConstant.E_MQ_AD3_ORDER_QUERY, JSON.toJSONString(rabbitMassage))
        }
        return response;
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/23
     * @Descripate 退款不上报清结算
     **/
    @Override
    public BaseResponse cancelPaying(Channel channel, OrderRefund orderRefund, RabbitMassage rabbitMassage) {
        return null;
    }
}
