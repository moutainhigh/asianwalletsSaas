package com.asianwallets.base.service;

import com.asianwallets.common.dto.DccReportDTO;
import com.asianwallets.common.dto.OrdersDTO;
import com.asianwallets.common.entity.InsDailyTrade;
import com.asianwallets.common.entity.Orders;
import com.asianwallets.common.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrdersService {

    /**
     * 分页查询订单信息
     *
     * @param ordersDTO 订单输入实体
     * @return 订单集合
     */
    PageInfo<Orders> pageFindOrders(OrdersDTO ordersDTO);

    /**
     * 查询订单详情信息
     *
     * @param id 订单id
     * @return 订单详情输出实体
     */
    OrdersDetailVO getOrdersDetail(String id);

    /**
     * 订单导出
     *
     * @param ordersDTO 订单实体
     * @return 订单集合
     */
    List<ExportOrdersVO> exportOrders(OrdersDTO ordersDTO);

    /**
     * DCC报表查询
     *
     * @param dccReportDTO
     * @return
     */
    PageInfo<DccReportVO> getDccReport(DccReportDTO dccReportDTO);


    /**
     * DCC报表导出
     *
     * @param dccReportDTO
     * @return
     */
    List<DccReportVO> exportDccReport(DccReportDTO dccReportDTO);

    /**
     * 分页查询机构日交易汇总表
     *
     * @param ordersDTO 订单实体
     * @return 订单集合
     */
    PageInfo<InsDailyTrade> pageFindInsDailyTrade(OrdersDTO ordersDTO);

    /**
     * 导出机构日交易汇总表
     *
     * @param ordersDTO 订单实体
     * @return 订单集合
     */
    List<InsDailyTradeVO> exportInsDailyTrade(OrdersDTO ordersDTO);

    /**
     * 交易统计
     *
     * @param ordersDTO 订单实体
     * @return 订单统计信息
     */
    List<OrdersStatisticsVO> statistics(OrdersDTO ordersDTO);

    /**
     * 产品交易统计
     *
     * @param ordersDTO 订单实体
     * @return 订单统计信息
     */
    List<OrdersProStatisticsVO> productStatistics(OrdersDTO ordersDTO);
}
