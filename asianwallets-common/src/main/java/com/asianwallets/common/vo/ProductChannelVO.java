package com.asianwallets.common.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value = "产品通道输出实体", description = "产品通道输出实体")
public class ProductChannelVO {

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "支付方式")
    private String payType;

    @ApiModelProperty(value = "交易方向")
    private Byte transType;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "通道输出实体")
    private List<InstitutionConfigChannelVO> institutionConfigChannelVOList;

}
