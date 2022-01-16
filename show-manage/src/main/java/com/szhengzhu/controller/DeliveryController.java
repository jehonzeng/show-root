package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"配送管理：DeliveryController"})
@RestController
@RequestMapping("/v1/delivery")
public class DeliveryController {

    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "获取配送地址列表", notes = "获取配送地址列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<OrderDelivery>> pageDelivery(
            @RequestBody PageParam<OrderDelivery> deliveryPage) {
        if (deliveryPage.getSidx().equals("mark_id") || StringUtils.isEmpty(deliveryPage.getSidx())) { deliveryPage.setSidx("orderTime");}
        return showOrderClient.pageDelivery(deliveryPage);
    }

    @ApiOperation(value = "获取订单配送详情", notes = "获取订单配送详情")
    @GetMapping(value = "/{orderId}")
    public Result<OrderDelivery> getDeliveryByOrderId(@PathVariable("orderId") @NotBlank String orderId) {
        return showOrderClient.getDeliveryById(orderId);
    }

    @ApiOperation(value = "修改订单配送信息", notes = "修改订单配送信息")
    @PatchMapping(value = "")
    public Result modifyDelivery(@RequestBody OrderDelivery orderDelivery) {
        return showOrderClient.modifyDelivery(orderDelivery);
    }

    @ApiOperation(value = "通过配送id实时获取物流信息", notes = "通过配送id实时获取物流信息")
    @GetMapping(value = "/track/info")
    public Result<Map<String, Object>> getTrackByDeliveryId(
            @RequestParam("deliveryId") @NotBlank String deliveryId) {
        return showOrderClient.getTrackByDeliveryId(deliveryId);
    }  
}
