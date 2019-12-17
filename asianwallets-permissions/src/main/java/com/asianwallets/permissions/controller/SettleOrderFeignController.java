package com.asianwallets.permissions.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.asianwallets.common.base.BaseController;
import com.asianwallets.common.cache.CommonLanguageCacheService;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.dto.SettleOrderDTO;
import com.asianwallets.common.dto.SettleOrderExportDTO;
import com.asianwallets.common.entity.SettleOrder;
import com.asianwallets.common.exception.BusinessException;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.EResultEnum;
import com.asianwallets.common.response.ResultUtil;
import com.asianwallets.common.utils.ArrayUtil;
import com.asianwallets.common.utils.ExcelUtils;
import com.asianwallets.common.utils.SpringContextUtil;
import com.asianwallets.permissions.feign.base.SettleOrderFeign;
import com.asianwallets.permissions.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 机构结算交易相关模块
 */
@RestController
@Api(description = "机构结算交易接口")
@RequestMapping("/settleorder")
@Slf4j
public class SettleOrderFeignController extends BaseController {

    @Autowired
    private SettleOrderFeign settleOrderFeign;

    @Autowired
    private OperationLogService operationLogService;


    @ApiOperation(value = "机构结算交易分页查询一览")
    @PostMapping("/pageSettleOrder")
    public BaseResponse pageSettleOrder(@RequestBody @ApiParam SettleOrderDTO settleOrderDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(settleOrderDTO),
                "机构结算交易分页查询一览"));
        return settleOrderFeign.pageSettleOrder(settleOrderDTO);
    }

    @ApiOperation(value = "机构结算交易分页查询详情")
    @PostMapping("/pageSettleOrderDetail")
    public BaseResponse pageSettleOrderDetail(@RequestBody @ApiParam SettleOrderDTO settleOrderDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(settleOrderDTO),
                "机构结算交易分页查询详情"));
        return settleOrderFeign.pageSettleOrderDetail(settleOrderDTO);
    }

    /**
     * 后台机构结算审核导出
     *
     * @param settleOrderDTO
     * @return
     */
    @ApiOperation(value = "机构结算导出")
    @PostMapping("/exportSettleOrder")
    public BaseResponse exportSettleOrder(@RequestBody @ApiParam SettleOrderDTO settleOrderDTO, HttpServletResponse response) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(settleOrderDTO),
                "机构结算导出"));
        ExcelWriter writer = ExcelUtil.getBigWriter();
        try {
            List<SettleOrder> lists = settleOrderFeign.exportSettleOrder(settleOrderDTO);
            ServletOutputStream out = response.getOutputStream();
            if (ArrayUtil.isEmpty(lists)) {
                //数据不存在的场合
                HashMap errorMsgMap = SpringContextUtil.getBean(CommonLanguageCacheService.class).getLanguage(getLanguage());
                writer.write(Arrays.asList("message", errorMsgMap.get(String.valueOf(EResultEnum.DATA_IS_NOT_EXIST.getCode()))));
                writer.flush(out);
                return ResultUtil.success();
            }
            ExcelUtils excelUtils = new ExcelUtils();
            excelUtils.exportExcel(lists, SettleOrderExportDTO.class, writer);
            writer.flush(out);
        } catch (Exception e) {
            log.info("==========【机构结算导出】==========【机构结算导出】", e);
            throw new BusinessException(EResultEnum.INSTITUTION_INFORMATION_EXPORT_FAILED.getCode());
        } finally {
            writer.close();
        }
        return ResultUtil.success();
    }
}