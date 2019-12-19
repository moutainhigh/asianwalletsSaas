package com.asianwallets.permissions.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.utils.BeanToMapUtil;
import com.asianwallets.common.utils.ReflexClazzUtils;
import com.asianwallets.common.vo.*;
import com.asianwallets.permissions.service.ExportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description:
 * @author: YangXu
 * @create: 2019-11-28 13:49
 **/
@Service
public class ExportServiceImpl implements ExportService {

    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/28
     * @Descripate 导出机构
     **/
    @Override
    public ExcelWriter getInstitutionExcel(List<InstitutionExportVO> institutionExportVOS, Class clazz) {

        ExcelWriter writer = ExcelUtil.getBigWriter();
        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(clazz);
        //注释信息
        String[] comment = result.get(AsianWalletConstant.EXCEL_TITLES);
        //属性名信息
        String[] property = result.get(AsianWalletConstant.EXCEL_ATTRS);
        ArrayList<Object> oList1 = new ArrayList<>();
        LinkedHashSet<Object> oSet1 = new LinkedHashSet<>();
        for (InstitutionExportVO institutionExportVO : institutionExportVOS) {
            HashMap<String, Object> oMap = BeanToMapUtil.beanToMap(institutionExportVO);
            ArrayList<Object> oList2 = new ArrayList<>();
            Set<String> keySet = oMap.keySet();
            for (int p = 0; p < property.length; p++) {
                for (String s : keySet) {
                    if (s.equals(property[p])) {
                        oSet1.add(comment[p]);
                        if (s.equals("auditStatus")) {
                            if ((String.valueOf((oMap.get(s))).equals("1"))) {
                                oList2.add("待审核");
                            } else if ((String.valueOf((oMap.get(s))).equals("2"))) {
                                oList2.add("审核通过");
                            } else if ((String.valueOf((oMap.get(s))).equals("3"))) {
                                oList2.add("审核不通过");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("enabled")) {
                            if ((String.valueOf((oMap.get(s)))).equals("true")) {
                                oList2.add("启用");
                            } else if ((String.valueOf((oMap.get(s)))).equals("false")) {
                                oList2.add("禁用");
                            }
                        } else {
                            oList2.add(oMap.get(s));
                        }

                    }
                }
            }
            oList1.add(oList2);
        }
        oList1.add(0, oSet1);
        writer.write(oList1);
        return writer;


    }

    /**
     * @return
     * @Author YangXu
     * @Date 2019/11/28
     * @Descripate 导出商户
     **/
    @Override
    public ExcelWriter getMerchantExcel(ArrayList<MerchantExportVO> merchantExportVOS, Class clazz) {
        ExcelWriter writer = ExcelUtil.getBigWriter();
        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(clazz);
        //注释信息
        String[] comment = result.get(AsianWalletConstant.EXCEL_TITLES);
        //属性名信息
        String[] property = result.get(AsianWalletConstant.EXCEL_ATTRS);
        ArrayList<Object> oList1 = new ArrayList<>();
        LinkedHashSet<Object> oSet1 = new LinkedHashSet<>();
        for (MerchantExportVO merchantExportVO : merchantExportVOS) {
            HashMap<String, Object> oMap = BeanToMapUtil.beanToMap(merchantExportVO);
            ArrayList<Object> oList2 = new ArrayList<>();
            Set<String> keySet = oMap.keySet();
            for (int p = 0; p < property.length; p++) {
                for (String s : keySet) {
                    if (s.equals(property[p])) {
                        oSet1.add(comment[p]);
                        if (s.equals("auditStatus")) {
                            if ((String.valueOf((oMap.get(s))).equals("1"))) {
                                oList2.add("待审核");
                            } else if ((String.valueOf((oMap.get(s))).equals("2"))) {
                                oList2.add("审核通过");
                            } else if ((String.valueOf((oMap.get(s))).equals("3"))) {
                                oList2.add("审核不通过");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("enabled")) {
                            if ((String.valueOf((oMap.get(s)))).equals("true")) {
                                oList2.add("启用");
                            } else if ((String.valueOf((oMap.get(s)))).equals("false")) {
                                oList2.add("禁用");
                            }
                        } else if (s.equals("merchantType")) {
                            if ((String.valueOf((oMap.get(s)))).equals("3")) {
                                oList2.add("普通商户");
                            } else if ((String.valueOf((oMap.get(s)))).equals("4")) {
                                oList2.add("代理商");
                            } else if ((String.valueOf((oMap.get(s)))).equals("5")) {
                                oList2.add("集团商户");
                            } else {
                                oList2.add("");
                            }
                        } else {
                            oList2.add(oMap.get(s));
                        }

                    }
                }
            }
            oList1.add(oList2);
        }
        oList1.add(0, oSet1);
        writer.write(oList1);
        return writer;

    }

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/10
     * @Descripate 导出商户产品
     **/
    @Override
    public ExcelWriter getMerchantProductExcel(ArrayList<MerchantProductExportVO> merchantProExportVOS, Class clazz) {
        ExcelWriter writer = ExcelUtil.getBigWriter();
        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(clazz);
        //注释信息
        String[] comment = result.get(AsianWalletConstant.EXCEL_TITLES);
        //属性名信息
        String[] property = result.get(AsianWalletConstant.EXCEL_ATTRS);
        ArrayList<Object> oList1 = new ArrayList<>();
        LinkedHashSet<Object> oSet1 = new LinkedHashSet<>();
        for (MerchantProductExportVO merchantProductExportVO : merchantProExportVOS) {
            HashMap<String, Object> oMap = BeanToMapUtil.beanToMap(merchantProductExportVO);
            ArrayList<Object> oList2 = new ArrayList<>();
            Set<String> keySet = oMap.keySet();
            for (int p = 0; p < property.length; p++) {
                for (String s : keySet) {
                    if (s.equals(property[p])) {
                        oSet1.add(comment[p]);
                        if (s.equals("tradeDirection")) {
                            if ((String.valueOf((oMap.get(s))).equals("1"))) {
                                oList2.add("线上");
                            } else if ((String.valueOf((oMap.get(s))).equals("2"))) {
                                oList2.add("线下");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("enabled")) {
                            if ((String.valueOf((oMap.get(s)))).equals("true")) {
                                oList2.add("启用");
                            } else if ((String.valueOf((oMap.get(s)))).equals("false")) {
                                oList2.add("禁用");
                            }
                        } else {
                            oList2.add(oMap.get(s));
                        }

                    }
                }
            }
            oList1.add(oList2);
        }
        oList1.add(0, oSet1);
        writer.write(oList1);
        return writer;
    }

    /**
     * @return
     * @Author YangXu
     * @Date 2019/12/12
     * @Descripate 导出商户通道
     **/
    @Override
    public ExcelWriter getMerchantChannelExcel(ArrayList<MerChannelExportVO> merchantChannelExportVOS, Class clazz) {
        ExcelWriter writer = ExcelUtil.getBigWriter();
        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(clazz);
        //注释信息
        String[] comment = result.get(AsianWalletConstant.EXCEL_TITLES);
        //属性名信息
        String[] property = result.get(AsianWalletConstant.EXCEL_ATTRS);
        ArrayList<Object> oList1 = new ArrayList<>();
        LinkedHashSet<Object> oSet1 = new LinkedHashSet<>();
        for (MerChannelExportVO merChannelExportVO : merchantChannelExportVOS) {
            HashMap<String, Object> oMap = BeanToMapUtil.beanToMap(merChannelExportVO);
            ArrayList<Object> oList2 = new ArrayList<>();
            Set<String> keySet = oMap.keySet();
            for (int p = 0; p < property.length; p++) {
                for (String s : keySet) {
                    if (s.equals(property[p])) {
                        oSet1.add(comment[p]);
                        if (s.equals("enabled")) {
                            if ((String.valueOf((oMap.get(s)))).equals("true")) {
                                oList2.add("启用");
                            } else if ((String.valueOf((oMap.get(s)))).equals("false")) {
                                oList2.add("禁用");
                            }else {
                                oList2.add("");
                            }
                        } else {
                            oList2.add(oMap.get(s));
                        }

                    }
                }
            }
            oList1.add(oList2);
        }
        oList1.add(0, oSet1);
        writer.write(oList1);
        return writer;
    }

    /**
     * 导出订单信息
     *
     * @param exportOrdersVOList 订单集合
     * @param clazz              订单导出class
     * @param writer
     * @return
     */
    @Override
    public ExcelWriter exportOrders(List<ExportOrdersVO> exportOrdersVOList, Class<ExportOrdersVO> clazz, ExcelWriter writer) {
        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        BigDecimal totalTradeAmount = BigDecimal.ZERO;
        BigDecimal totalFee = BigDecimal.ZERO;
        BigDecimal totalChannelFee = BigDecimal.ZERO;
        for (ExportOrdersVO order : exportOrdersVOList) {
            totalOrderAmount = totalOrderAmount.add(order.getOrderAmount());
            totalTradeAmount = totalTradeAmount.add(order.getTradeAmount());
            totalFee = totalFee.add(order.getFee());
            totalChannelFee = totalChannelFee.add(order.getChannelFee());
        }
        writer.setColumnWidth(-1, 40);
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();
//        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(clazz);
        //注释信息
        String[] comment = result.get(AsianWalletConstant.EXCEL_TITLES);
        //属性名信息
        String[] property = result.get(AsianWalletConstant.EXCEL_ATTRS);
        ArrayList<Object> oList1 = new ArrayList<>();
        Set<Object> oSet1 = new LinkedHashSet<>();
        for (ExportOrdersVO orderTradeVO : exportOrdersVOList) {
            HashMap<String, Object> oMap = BeanToMapUtil.beanToMap(orderTradeVO);
            ArrayList<Object> oList2 = new ArrayList<>();
            Set<String> keySet = oMap.keySet();
            for (int i = 0; i < property.length; i++) {
                for (String s : keySet) {
                    if (s.equals(property[i])) {
                        oSet1.add(comment[i]);
                        if (s.equals("tradeDirection")) {
                            if (String.valueOf(oMap.get(s)).equals("1")) {
                                oList2.add("线上");
                            } else if (String.valueOf(oMap.get(s)).equals("2")) {
                                oList2.add("线下");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("tradeStatus")) {
                            if (String.valueOf(oMap.get(s)).equals("1")) {
                                oList2.add("待支付 ");
                            } else if (String.valueOf(oMap.get(s)).equals("2")) {
                                oList2.add("交易中");
                            } else if (String.valueOf(oMap.get(s)).equals("3")) {
                                oList2.add("交易成功");
                            } else if (String.valueOf(oMap.get(s)).equals("4")) {
                                oList2.add("交易失败");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("cancelStatus")) {
                            if (String.valueOf(oMap.get(s)).equals("1")) {
                                oList2.add("撤销中 ");
                            } else if (String.valueOf(oMap.get(s)).equals("2")) {
                                oList2.add("撤销成功");
                            } else if (String.valueOf(oMap.get(s)).equals("3")) {
                                oList2.add("撤销失败");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("refundStatus")) {
                            if (String.valueOf(oMap.get(s)).equals("1")) {
                                oList2.add("退款中 ");
                            } else if (String.valueOf(oMap.get(s)).equals("2")) {
                                oList2.add("部分退款成功");
                            } else if (String.valueOf(oMap.get(s)).equals("3")) {
                                oList2.add("退款成功");
                            } else if (String.valueOf(oMap.get(s)).equals("4")) {
                                oList2.add("退款失败");
                            } else {
                                oList2.add("");
                            }
                        } else if (s.equals("tradeType")) {
                            if (String.valueOf(oMap.get(s)).equals("1")) {
                                oList2.add("收");
                            } else if (String.valueOf(oMap.get(s)).equals("2")) {
                                oList2.add("付");
                            } else {
                                oList2.add("");
                            }
                        } else {
                            oList2.add(oMap.get(s));
                        }
                    }
                }
            }
            oList1.add(oList2);
        }
        int count = 0;
        List<Object> statistics = new ArrayList<>();
        statistics.add("金额总计");
        for (Object o : oSet1) {
            count++;
            switch (String.valueOf(o)) {
                case "订单金额":
                    statistics.add(count - 1, totalOrderAmount);
                    break;
                case "手续费":
                    statistics.add(count - 1, totalFee);
                    break;
                case "通道手续费":
                    statistics.add(count - 1, totalChannelFee);
                    break;
                case "交易金额":
                    statistics.add(count - 1, totalTradeAmount);
                    break;
                default:
                    statistics.add(count, "");
                    break;
            }
        }
        oList1.add(0, statistics);
        oList1.add(1, oSet1);
        writer.write(oList1);
        return writer;
    }
}
