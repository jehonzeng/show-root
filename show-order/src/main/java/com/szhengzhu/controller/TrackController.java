package com.szhengzhu.controller;

import com.szhengzhu.service.TrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 物流信息类
 *
 * @author Jehon Zeng
 */
@RestController
@RequestMapping("/track")
public class TrackController {

    @Resource
    private TrackService trackService;

    @ApiOperation(value = "通过订单id实时获取物流信息", notes = "通过订单id实时获取物流信息")
    @GetMapping(value = "/info/order")
    public Map<String, Object> getInfoByOrderId(@RequestParam("orderId") @Validated String orderId) throws Exception {
        return trackService.getInfoByOrderId(orderId);
    }

    @ApiOperation(value = "通过配送id实时获取物流信息", notes = "通过配送id实时获取物流信息")
    @GetMapping(value = "/info/delivery")
    public Map<String, Object> getInfoByDeliveryId(@RequestParam("deliveryId") @NotBlank String deliveryId)
            throws Exception {
        return trackService.getInfoByDeliveryId(deliveryId);
    }
}
