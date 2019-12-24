package com.asianwallets.trade.service;

import com.asianwallets.trade.dto.OfflineCheckOrdersDTO;
import com.asianwallets.trade.dto.OfflineLoginDTO;
import com.asianwallets.trade.dto.OfflineTradeDTO;
import com.asianwallets.trade.vo.BscDynamicScanVO;
import com.asianwallets.trade.vo.CsbDynamicScanVO;
import com.asianwallets.trade.vo.OfflineCheckOrdersVO;

import java.util.List;

public interface OfflineTradeService {

    /**
     * 线下登录
     *
     * @param offlineLoginDTO 线下登录实体
     * @return token
     */
    String login(OfflineLoginDTO offlineLoginDTO);

    /**
     * 线下同机构CSB动态扫码
     *
     * @param offlineTradeDTO 线下交易输入实体
     * @return 线下同机构CSB动态扫码输出实体
     */
    CsbDynamicScanVO csbDynamicScan(OfflineTradeDTO offlineTradeDTO);

    /**
     * 线下同机构BSC动态扫码
     *
     * @param offlineTradeDTO 线下交易输入实体
     * @return 线下同机构BSC动态扫码输出实体
     */
    BscDynamicScanVO bscDynamicScan(OfflineTradeDTO offlineTradeDTO);

    /**
     * 线下查询订单列表
     *
     * @param offlineCheckOrdersDTO 查询订单输入实体
     * @return 订单集合
     */
    List<OfflineCheckOrdersVO> checkOrder(OfflineCheckOrdersDTO offlineCheckOrdersDTO);
}
