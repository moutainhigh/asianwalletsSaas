package com.asianwallets.permissions.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.asianwallets.common.entity.TmMerChTvAcctBalance;
import com.asianwallets.common.vo.*;

import java.util.ArrayList;
import java.util.List;

public interface ExportService {

    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/28
     * @Descripate 导出商户
     **/
    ExcelWriter getMerchantExcel(ArrayList<MerchantExportVO> merchantExportVOS, Class clazz);

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/10
     * @Descripate 导出商户产品
     **/
    ExcelWriter getMerchantProductExcel(ArrayList<MerchantProductExportVO> merchantProExportVOS, Class clazz);

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/10
     * @Descripate 导出商户通道
     **/
    ExcelWriter getMerchantChannelExcel(ArrayList<MerChannelExportVO> merchantChannelExportVOS, Class clazz);

    /**
     * 导出订单信息
     *
     * @param exportOrdersVOList  订单集合
     * @param exportOrdersVOClass 订单导出class
     * @param writer
     * @return
     */
    ExcelWriter exportOrders(List<ExportOrdersVO> exportOrdersVOList, Class<ExportOrdersVO> exportOrdersVOClass, ExcelWriter writer);

    /**
     *导出清算户余额流水详情
     * @param clearAccountVOS
     * @param clazz
     * @return
     */
    ExcelWriter getClearBalanceWriter(List<ClearAccountVO> clearAccountVOS, Class clazz);

    /**
     * 导出结算户余额流水详情
     * @param institutions
     * @param clazz
     * @return
     */
    ExcelWriter getTmMerChTvAcctBalanceWriter(List<TmMerChTvAcctBalanceVO> institutions, Class clazz);

    /**
     * 导出冻结余额流水详情
     * @param institutions
     * @param clazz
     * @return
     */
    ExcelWriter getFrozenLogsWriter(List<FrozenMarginInfoVO> institutions, Class clazz);
}
