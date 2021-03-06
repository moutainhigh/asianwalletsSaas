package com.asianwallets.permissions.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.asianwallets.common.base.BaseController;
import com.asianwallets.common.cache.CommonLanguageCacheService;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.dto.InstitutionDTO;
import com.asianwallets.common.exception.BusinessException;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.response.ResultUtil;
import com.asianwallets.common.utils.ArrayUtil;
import com.asianwallets.common.utils.ExcelUtils;
import com.asianwallets.common.utils.SpringContextUtil;
import com.asianwallets.common.vo.InstitutionExportVO;
import com.asianwallets.permissions.feign.base.InstitutionFeign;
import com.asianwallets.permissions.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: YangXu
 * @create: 2019-11-27 10:02
 **/
@RestController
@Api(description = "机构管理接口")
@RequestMapping("/institution")
@Slf4j
public class InstitutionFeignController extends BaseController {

    @Autowired
    private InstitutionFeign institutionFeign;

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "添加机构")
    @PostMapping("addInstitution")
    public BaseResponse addInstitution(@RequestBody @ApiParam InstitutionDTO institutionDTO) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.ADD, JSON.toJSONString(institutionDTO),
                "添加机构"));
        return institutionFeign.addInstitution(institutionDTO);
    }


    @ApiOperation(value = "修改机构")
    @PostMapping("updateInstitution")
    public BaseResponse updateInstitution(@RequestBody @ApiParam InstitutionDTO institutionDTO) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.UPDATE, JSON.toJSONString(institutionDTO),
                "修改机构"));
        return institutionFeign.updateInstitution(institutionDTO);
    }

    @ApiOperation(value = "分页查询机构信息列表")
    @PostMapping("/pageFindInstitution")
    public BaseResponse pageFindInstitution(@RequestBody @ApiParam InstitutionDTO institutionDTO) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(institutionDTO),
                "分页查询机构信息列表"));
        return institutionFeign.pageFindInstitution(institutionDTO);
    }

    @ApiOperation(value = "分页查询机构审核信息列表")
    @PostMapping("/pageFindInstitutionAudit")
    public BaseResponse pageFindInstitutionAudit(@RequestBody @ApiParam InstitutionDTO institutionDTO) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(institutionDTO),
                "分页查询机构审核信息列表"));
        return institutionFeign.pageFindInstitutionAudit(institutionDTO);
    }

    @ApiOperation(value = "根据机构Id查询机构信息详情")
    @GetMapping("/getInstitutionInfo")
    public BaseResponse getInstitutionInfo(@RequestParam @ApiParam String id) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(id),
                "根据机构Id查询机构信息详情"));
        return institutionFeign.getInstitutionInfo(id);
    }


    @ApiOperation(value = "根据机构Id查询机构审核信息详情")
    @GetMapping("/getInstitutionInfoAudit")
    public BaseResponse getInstitutionInfoAudit(String id) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(id),
                "根据机构Id查询机构审核信息详情"));
        return institutionFeign.getInstitutionInfoAudit(id);
    }

    @ApiOperation(value = "审核机构信息接口")
    @GetMapping("/auditInstitution")
    public BaseResponse auditInstitution(@RequestParam @ApiParam String institutionId, @RequestParam @ApiParam Boolean enabled, @RequestParam(required = false) @ApiParam String remark) {
        //添加操作日志
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.UPDATE, JSON.toJSONString(this.getRequest().getParameterMap()),
                "审核机构信息接口"));
        return institutionFeign.auditInstitution(institutionId, enabled, remark);
    }

    @ApiOperation(value = "机构下拉框")
    @GetMapping("/getAllInstitution")
    public BaseResponse getAllInstitution() {
        return institutionFeign.getAllInstitution();
    }

    @ApiOperation(value = "禁用启用机构")
    @GetMapping("/banInstitution")
    public BaseResponse banInstitution(@RequestParam @ApiParam String institutionId, @RequestParam @ApiParam Boolean enabled) {
        return institutionFeign.banInstitution(institutionId, enabled);
    }

    @ApiOperation(value = "导出机构")
    @PostMapping("/exportInstitution")
    public BaseResponse exportInstitution(@RequestBody @ApiParam InstitutionDTO institutionDTO, HttpServletResponse response) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(institutionDTO),
                "导出机构"));
        ExcelWriter writer = ExcelUtil.getBigWriter();
        try {
            List<InstitutionExportVO> dataList = institutionFeign.exportInstitution(institutionDTO);
            ServletOutputStream out = response.getOutputStream();
            if (ArrayUtil.isEmpty(dataList)) {
                //数据不存在的场合
                HashMap errorMsgMap = SpringContextUtil.getBean(CommonLanguageCacheService.class).getLanguage(getLanguage());
                writer.write(Arrays.asList("message", errorMsgMap.get(String.valueOf(EResultEnum.DATA_IS_NOT_EXIST.getCode()))));
                writer.flush(out);
                return ResultUtil.success();
            }
            ExcelUtils excelUtils = new ExcelUtils();
            excelUtils.exportExcel(dataList, InstitutionExportVO.class, writer);
            writer.flush(out);
        } catch (Exception e) {
            log.info("==========【机构导出】==========【机构导出异常】", e);
            throw new BusinessException(EResultEnum.INSTITUTION_INFORMATION_EXPORT_FAILED.getCode());
        } finally {
            writer.close();
        }
        return ResultUtil.success();
    }
}
