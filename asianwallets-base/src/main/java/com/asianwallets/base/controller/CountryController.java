package com.asianwallets.base.controller;

import com.asianwallets.base.service.CountryService;
import com.asianwallets.common.base.BaseController;
import com.asianwallets.common.dto.CountryDTO;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CurrencyController
 * @Description 国家地区
 * @Author abc
 * @Date 2019/11/22 6:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/country")
@Api("国家地区")
public class CountryController extends BaseController {

    @Autowired
    private CountryService countryService;

    @ApiOperation(value = "新增国家")
    @PostMapping("addCountry")
    public BaseResponse addCountry(@RequestBody @ApiParam CountryDTO country) {
//        country.setCreator(this.getSysUserVO().getUsername());
        return ResultUtil.success(countryService.addCountry(country));
    }

    @ApiOperation(value = "修改国家")
    @PutMapping("updateCountry")
    public BaseResponse updateCountry(@RequestBody @ApiParam CountryDTO country) {
        country.setModifier(this.getSysUserVO().getUsername());
        return ResultUtil.success(countryService.updateCountry(country));
    }

    @ApiOperation(value = "查询国家")
    @PostMapping("pageCountry")
    public BaseResponse pageCountry(@RequestBody @ApiParam CountryDTO country) {
        return ResultUtil.success(countryService.pageCountry(country));
    }

    @ApiOperation(value = "启用禁用国家")
    @PutMapping("banCountry")
    public BaseResponse banCountry(@RequestBody @ApiParam CountryDTO country) {
        country.setModifier(this.getSysUserVO().getUsername());
        return ResultUtil.success(countryService.banCountry(country));
    }

    @ApiOperation(value = "查询所有国家地区")
    @GetMapping("inquireAllCountry")
    public BaseResponse inquireAllCountry() {
        return ResultUtil.success(countryService.inquireAllCountry());
    }
}