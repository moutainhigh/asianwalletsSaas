package com.asianwallets.permissions.controller;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.asianwallets.common.base.BaseController;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.dto.MerchantReportDTO;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.response.ResultUtil;
import com.asianwallets.common.utils.ErrorExcelUtils;
import com.asianwallets.common.utils.ExcelUtils;
import com.asianwallets.common.vo.MerchantReportExportVO;
import com.asianwallets.common.vo.MerchantReportVO;
import com.asianwallets.permissions.feign.base.MerchantReportFeign;
import com.asianwallets.permissions.service.ImportService;
import com.asianwallets.permissions.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName mcc
 * @Description 商户报备
 * @Author abc
 * @Date 2019/11/22 6:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/merchantReport")
@Api(description = "商户报备接口")
@Slf4j
public class MerchantReportFeignController extends BaseController {

    @Autowired
    private MerchantReportFeign merchantReportFeign;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ImportService importService;

    @ApiOperation(value = "新增商户报备信息")
    @PostMapping("addReport")
    public BaseResponse addReport(@RequestBody @ApiParam MerchantReportDTO merchantReportDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(),
                AsianWalletConstant.ADD, JSON.toJSONString(merchantReportDTO),
                "新增报备信息"));
        return ResultUtil.success(merchantReportFeign.addReport(merchantReportDTO));
    }

    @ApiOperation(value = "分页查询商户报备信息")
    @PostMapping("pageReport")
    public BaseResponse pageReport(@RequestBody @ApiParam MerchantReportDTO merchantReportDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(),
                AsianWalletConstant.SELECT, JSON.toJSONString(merchantReportDTO),
                "分页查询商户报备信息"));
        return ResultUtil.success(merchantReportFeign.pageReport(merchantReportDTO));
    }

    @ApiOperation(value = "修改商户报备信息")
    @PostMapping("updateReport")
    public BaseResponse updateReport(@RequestBody @ApiParam MerchantReportDTO merchantReportDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(),
                AsianWalletConstant.UPDATE, JSON.toJSONString(merchantReportDTO),
                "修改商户报备信息"));
        return ResultUtil.success(merchantReportFeign.updateReport(merchantReportDTO));
    }

    @ApiOperation(value = "启用禁用Report")
    @PostMapping("banReport")
    public BaseResponse banReport(@RequestBody @ApiParam MerchantReportDTO merchantReportDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(),
                AsianWalletConstant.UPDATE, JSON.toJSONString(merchantReportDTO),
                "启用禁用Report"));
        return ResultUtil.success(merchantReportFeign.banReport(merchantReportDTO));
    }

    @SneakyThrows
    @ApiOperation(value = "导出商户报备信息")
    @PostMapping("exportReport")
    public BaseResponse exportReport(@RequestBody @ApiParam MerchantReportDTO merchantReportDTO, HttpServletResponse response) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(),
                AsianWalletConstant.SELECT, JSON.toJSONString(merchantReportDTO),
                "导出Report"));
        ExcelWriter writer = ExcelUtil.getBigWriter();
        try {
            List<MerchantReportVO> lists = merchantReportFeign.exportReport(merchantReportDTO);
            ServletOutputStream out = response.getOutputStream();
            //判断是否出错
            if (ErrorExcelUtils.errorExportExcel(writer, lists, out, EResultEnum.DATA_IS_NOT_EXIST.getCode(), getLanguage())) {
                return ResultUtil.success();
            }
            ExcelUtils excelUtils = new ExcelUtils();
            excelUtils.exportExcel(lists, MerchantReportExportVO.class, writer);
            writer.flush(out);
        } catch (Exception e) {
            log.info("==========【导出报表】==========【导出报表失败】", e);
            if (ErrorExcelUtils.errorExportExcel(writer, null, response.getOutputStream(), EResultEnum.ERROR.getCode(), getLanguage())) {
                return ResultUtil.success();
            }
        } finally {
            writer.close();
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "导入商户报备信息")
    @PostMapping("/importMerchantReport")
    public BaseResponse importMerchantReport(@RequestParam("file") @ApiParam MultipartFile file) {
        operationLogService.addOperationLog(setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.ADD, null,
                "导入商户报备信息"));
        return merchantReportFeign.importMerchantReport(importService.importMerchantReport(this.getSysUserVO().getUsername(), file));
    }

    @ApiOperation(value = "根据商户编号获取国家代码")
    @PostMapping("/getMerchantCountryCode")
    public BaseResponse getMerchantCountryCode(@RequestBody @ApiParam MerchantReportDTO merchantReportDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(),
                AsianWalletConstant.SELECT, JSON.toJSONString(merchantReportDTO),
                "根据商户编号获取国家代码"));
        return ResultUtil.success(operationLogService.getMerchantCountryCode(merchantReportDTO.getMerchantId()));
    }
}

