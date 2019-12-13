package com.asianwallets.base.controller;

import com.asianwallets.base.service.OrdersService;
import com.asianwallets.common.base.BaseController;
import com.asianwallets.common.dto.OrdersDTO;
import com.asianwallets.common.response.BaseResponse;
import com.asianwallets.common.response.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description = "订单接口")
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value = "分页查询订单信息")
    @PostMapping("pageFindOrders")
    public BaseResponse pageFindOrders(@RequestBody @ApiParam OrdersDTO ordersAllDTO) {
        return ResultUtil.success(ordersService.pageFindOrders(ordersAllDTO));
    }

    @ApiOperation(value = "查询订单详情信息")
    @GetMapping("getOrdersDetail")
    public BaseResponse getOrdersDetail(@RequestParam @ApiParam String id) {
        return ResultUtil.success(ordersService.getOrdersDetail(id));
    }

    @ApiOperation(value = "分页查询退款订单信息")
    @PostMapping("pageFindOrdersRefund")
    public BaseResponse pageFindOrdersRefund(@RequestBody @ApiParam OrdersDTO ordersAllDTO) {
        return ResultUtil.success(ordersService.pageFindOrders(ordersAllDTO));
    }
}
