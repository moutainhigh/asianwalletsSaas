package com.asianwallets.trade.service;

import com.alibaba.fastjson.JSON;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.entity.*;
import com.asianwallets.common.exception.BusinessException;
import com.asianwallets.common.response.EResultEnum;

/**
 * 通用获取数据接口
 */
public interface CommonRedisDataService {

    /**
     * 根据币种编码获取币种
     *
     * @param code 币种编码
     * @return 币种
     */
    Currency getCurrencyByCode(String code);

    /**
     * 根据商户ID获取密钥对象
     *
     * @param merchantId 商户ID
     * @return 密钥
     */
    Attestation getAttestationByMerchantId(String merchantId);

    /**
     * 根据本币与外币获取汇率
     *
     * @param localCurrency   本币
     * @param foreignCurrency 外币
     * @return 汇率
     */
    ExchangeRate getExchangeRateByCurrency(String localCurrency, String foreignCurrency);

    /**
     * 根据机构ID获取机构
     *
     * @param institutionId 机构ID
     * @return 机构
     */
    Institution getInstitutionById(String institutionId);

    /**
     * 根据机构ID与交易方向查询获取机构请求参数
     *
     * @param institutionId  机构ID
     * @param tradeDirection 交易方向
     * @return 机构
     */
    InstitutionRequestParameters getInstitutionRequestByIdAndDirection(String institutionId, Byte tradeDirection);

    /**
     * 根据商户ID获取商户
     *
     * @param merchantId 商户ID
     * @return 商户
     */
    Merchant getMerchantById(String merchantId);

    /**
     * 根据通道code从redis获取通道信息
     *
     * @param channelCode 通道code
     */
    Channel getChannelByChannelCode(String channelCode);
}
