package com.asianwallets.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "退款订单输出实体", description = "退款订单输出实体")
public class OrdersRefundVO {

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime;

    @ApiModelProperty(value = "机构编号")
    private String institutionCode;

    @ApiModelProperty(value = "机构名称")
    private String institutionName;

    @ApiModelProperty(value = "商户编号")
    private String merchantId;

    @ApiModelProperty(value = "商户名")
    private String merchantName;

    @ApiModelProperty(value = "商户类型")
    private String merchantType;

    @ApiModelProperty(value = "退款订单号")
    private String id;

    @ApiModelProperty(value = "商户订单号")
    private String merchantOrderId;

    @ApiModelProperty(value = "交易流水号")
    private String orderId;

    @ApiModelProperty(value = "订单币种")
    private String orderCurrency;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "退款渠道名称")
    private String channelName;

    @ApiModelProperty(value = "渠道退款币种")
    @Column(name = "trade_currency")
    private String tradeCurrency;

    @ApiModelProperty(value = "渠道退款金额")
    private BigDecimal tradeAmount;

    @ApiModelProperty(value = "退款完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date completeTime;

    @ApiModelProperty(value = "退款状态 1:退款中 2:退款成功 3:退款失败 4:系统创建失败")
    private Byte refundStatus;

    @ApiModelProperty(value = "退款类型 - 1：全额退款 2：部分退款")
    private Byte refundType;

    @ApiModelProperty(value = "退款方式 - 1：系统退款 2：人工退款")
    private Byte refundMode;

    @ApiModelProperty(value = "交易类型:1-线上 2-线下")
    private Byte tradeDirection;

    @ApiModelProperty(value = "退款类型  导出用")
    private String refundTypeStr;

    @ApiModelProperty(value = "退款状态 导出用")
    private String refundStatusStr;

    @ApiModelProperty(value = "备注")
    private String remark;

}
