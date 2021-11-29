package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.rabbitmq.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"秒杀活动管理：SeckillController"})
@RestController
@RequestMapping("/v1/seckill")
public class SeckillController {

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private Sender sender;

    @ApiOperation(value = "获取秒杀活动分页列表", notes = "获取秒杀活动分页列表")
    @PostMapping(value = "/activity/page")
    public Result<PageGrid<SeckillInfo>> pageSeckill(@RequestBody PageParam<SeckillInfo> seckillPage) {
        return showActivityClient.pageSeckill(seckillPage);
    }

    @ApiOperation(value = "添加秒杀活动", notes = "添加秒杀活动")
    @PostMapping(value = "/activity")
    public Result<SeckillInfo> addSeckill(@RequestBody @Validated SeckillInfo seckillInfo) {
        return showActivityClient.addSeckill(seckillInfo);
    }

    @ApiOperation(value = "修改秒杀活动", notes = "修改秒杀活动")
    @PatchMapping(value = "/activity")
    public Result<SeckillInfo> modifySeckill(@RequestBody @Validated SeckillInfo seckillInfo) {
        return showActivityClient.modifySeckill(seckillInfo);
    }

    @ApiOperation(value = "获取秒杀活动详细信息", notes = "获取秒杀活动详细信息")
    @GetMapping(value = "/activity/{markId}")
    public Result<SeckillInfo> getInfo(@PathVariable("markId") @NotBlank String markId) {
        return showActivityClient.getSeckillInfo(markId);
    }

    @ApiOperation(value = "获取秒杀订单分页列表", notes = "获取订单分页列表")
    @PostMapping(value = "/order/page")
    public Result<PageGrid<SeckillOrder>> pageSeckillOrder(
            @RequestBody PageParam<SeckillOrder> orderPage) {
        return showOrderClient.pageSeckillOrder(orderPage);
    }

    @ApiOperation(value = "修改秒杀订单状态", notes = "修改秒杀订单状态")
    @PatchMapping(value = "/order/status")
    public Result<?> modifySeckillStatus(@RequestParam("markId") @NotBlank String markId,
            @RequestParam("orderStatus") @NotBlank String orderStatus) {
        Result<SeckillOrder> orderResult = showOrderClient.modifySeckillStatus(markId, orderStatus);
        if (orderResult.isSuccess()) {
            SeckillOrder order = orderResult.getData();
            Map<String, String> map = new HashMap<>();
            map.put("orderNo", order.getOrderNo());
            map.put("userId", order.getUserId());
            map.put("orderId", order.getMarkId());
            if (OrderStatus.STOCKING.equals(orderStatus)) {
                // 订单确认信息提醒
                sender.sendOrderConfirmMsg(map);
            } else if (OrderStatus.IN_DISTRIBUTION.equals(orderStatus)) {
                // 配送信息提醒
                sender.sendOrderDeliveryMsg(map);
            } else if (OrderStatus.REFUNDED.equals(orderStatus)) {
                // 退款
                sender.orderRefund(map);
            }
        }
        return orderResult;
    }
}
