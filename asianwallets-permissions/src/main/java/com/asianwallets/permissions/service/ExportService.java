package com.asianwallets.permissions.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.asianwallets.common.vo.InstitutionExportVO;
import com.asianwallets.common.vo.MerchantExportVO;

import java.util.ArrayList;
import java.util.List;

public interface ExportService {

    /**
     * Excel 导出机构
     *
     * @param institutions 对象集合
     * @param clazz        类名Class对象
     * @return ExcelWriter writer
     */
    ExcelWriter getInstitutionExcel(List<InstitutionExportVO> institutions, Class clazz);


    /**
     * @Author YangXu
     * @Date 2019/11/28
     * @Descripate 导出商户
     * @return
     **/
    ExcelWriter getMerchantExcel(ArrayList<MerchantExportVO> merchantExportVOS,  Class clazz);
}