package com.asianwallets.permissions.controller;

import com.alibaba.fastjson.JSON;
import com.asianwallets.common.base.BaseController;
import com.asianwallets.common.constant.AsianWalletConstant;
import com.asianwallets.common.dto.ProductDTO;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.ResultUtil;
import com.asianwallets.permissions.feign.base.ProductFeign;
import com.asianwallets.permissions.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YangXu
 * @create: 2019-12-05 14:03
 **/
@RestController
@Api(description = "产品管理接口")
@RequestMapping("/product")
@Slf4j
public class ProductFeignController extends BaseController {

    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "添加产品")
    @PostMapping("/addProduct")
    public BaseResponse addProduct(@RequestBody @ApiParam ProductDTO productDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.ADD, JSON.toJSONString(productDTO),
                "添加产品"));
        return productFeign.addProduct(productDTO);
    }

    @ApiOperation(value = "更新产品")
    @PostMapping("/updateProduct")
    public BaseResponse updateProduct(@RequestBody @ApiParam ProductDTO productDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.UPDATE, JSON.toJSONString(productDTO),
                "更新产品"));
        return productFeign.updateProduct(productDTO);
    }

    @ApiOperation(value = "分页查询产品")
    @PostMapping("/pageProduct")
    public BaseResponse pageProduct(@RequestBody @ApiParam ProductDTO productDTO) {
        operationLogService.addOperationLog(this.setOperationLog(this.getSysUserVO().getUsername(), AsianWalletConstant.SELECT, JSON.toJSONString(productDTO),
                "分页查询产品"));
        return productFeign.pageProduct(productDTO);
    }


}
