package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
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
@Api(tags = {"团购活动管理：TeambuyController"})
@RestController
@RequestMapping("/v1/teambuy")
public class TeambuyController {

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private Sender sender;

    @ApiOperation(value = "获取团购活动分页列表", notes = "获取团购活动分页列表")
    @PostMapping(value = "/activity/page")
    public Result<PageGrid<TeambuyInfo>> pageTeambuy(@RequestBody PageParam<TeambuyInfo> teambuyPage) {
        return showActivityClient.pageTeambuy(teambuyPage);
    }

    @ApiOperation(value = "添加团购活动", notes = "添加团购活动")
    @PostMapping(value = "/activity")
    private Result<TeambuyInfo> addTeambuy(@RequestBody @Validated TeambuyInfo teambuyInfo) {
        return showActivityClient.addTeambuy(teambuyInfo);
    }

    @ApiOperation(value = "修改团购活动", notes = "修改团购活动")
    @PatchMapping(value = "/activity")
    private Result<TeambuyInfo> modifyTeambuy(@RequestBody @Validated TeambuyInfo teambuyInfo) {
        return showActivityClient.modifyTeambuy(teambuyInfo);
    }

    @ApiOperation(value = "获取团购活动详细信息", notes = "获取团购活动详细信息")
    @GetMapping(value = "/activity/{markId}")
    public Result<TeambuyInfo> getInfo(@PathVariable("markId") @Validated String markId) {
        return showActivityClient.getTeambuyInfo(markId);
    }

    @ApiOperation(value = "获取团购团单组分页列表", notes = "获取团购团单组分页列表")
    @PostMapping(value = "/group/page")
    public Result<PageGrid<TeambuyGroup>> pageTeambuyGroup(
            @RequestBody PageParam<TeambuyGroup> groupPage) {
        return showOrderClient.pageTeambuyGroup(groupPage);
    }

    @ApiOperation(value = "获取团购子订单分页列表", notes = "获取团单子订单分页列表")
    @PostMapping(value = "/order/page")
    public Result<PageGrid<TeambuyOrder>> pageTeambuyOrder(
            @RequestBody PageParam<TeambuyOrder> orderPage) {
        return showOrderClient.pageTeambuyOrder(orderPage);
    }

    @ApiOperation(value = "修改团购子订单状态", notes = "修改团单子订单状态")
    @PatchMapping(value = "/order/status")
    public Result<?> modifyItemStatus(@RequestParam("markId") @NotBlank String markId,
            @RequestParam("orderStatus") @NotBlank String orderStatus) {
        Result<TeambuyOrder> orderResult = showOrderClient.modifyItemStatus(markId, orderStatus);
        if (orderResult.isSuccess()) {
            TeambuyOrder order = orderResult.getData();
            Map<String, String> map = new HashMap<>(4);
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
