package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.excel.OrderSendModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.OrderBatch;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "订单管理：OrderController")
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private Sender sender;

    @ApiOperation(value = "获取订单分页列表", notes = "获取订单分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<OrderInfo>> pageOrder(@RequestBody PageParam<OrderInfo> orderPage) {
        if (orderPage.getSidx().equals("mark_id") || StrUtil.isEmpty(orderPage.getSidx())) {
            orderPage.setSidx("o.orderTime");
        }
        return showOrderClient.pageOrder(orderPage);
    }

    @ApiOperation(value = "获取内部推销订单分页列表", notes = "获取内部推销订单分页列表")
    @PostMapping(value = "/share/page")
    public Result<PageGrid<OrderInfo>> pageShareOrder(@RequestBody PageParam<OrderInfo> orderPage) {
        if (orderPage.getSidx().equals("mark_id") || StrUtil.isEmpty(orderPage.getSidx())) {
            orderPage.setSidx("o.orderTime");
        }
        return showOrderClient.pageShareOrder(orderPage);
    }

    @ApiOperation(value = "获取发货订单列表", notes = "获取发货订单列表")
    @PostMapping(value = "/send/page")
    public Result<PageGrid<OrderSendModel>> pageSendInfo(@RequestBody PageParam<String> page) {
        if (page.getSidx().equals("mark_id") || StrUtil.isEmpty(page.getSidx())) {
            page.setSidx("o.orderTime");
        }
        return showOrderClient.pageSendInfo(page);
    }

    @ApiOperation(value = "后台添加订单", notes = "后台添加订单")
    @PostMapping(value = "")
    public Result<String> addOrder(@RequestBody @Validated OrderInfo order, HttpSession session) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        order.setUserId(userId);
        return showOrderClient.addOrder(order);
    }

    @ApiOperation(value = "后台修改订单", notes = "后台修改订单")
    @PatchMapping(value = "")
    public Result modifyOrder(@RequestBody @Validated OrderInfo order) {
        return showOrderClient.modifyOrder(order);
    }

    @ApiOperation(value = "获取订单详情及订单其他详细", notes = "获取订单详情及订单其他详细信息")
    @GetMapping(value = "/{markId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable("markId") @NotBlank String markId) {
        return showOrderClient.getOrderInfo(markId);
    }

    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @PatchMapping(value = "/status")
    public Result<?> modifyOrderStatus(@RequestParam("markId") @NotBlank String markId,
                                       @RequestParam("orderStatus") @NotBlank String orderStatus) {
        Result<OrderInfo> orderResult = showOrderClient.modifyOrderStatus(markId, orderStatus);
        if (orderResult.isSuccess()) {
            OrderInfo orderInfo = (OrderInfo) orderResult.getData();
            Map<String, String> map = new HashMap<>();
            map.put("orderNo", orderInfo.getOrderNo());
            map.put("userId", orderInfo.getUserId());
            map.put("orderId", orderInfo.getMarkId());
            if (OrderStatus.IN_DISTRIBUTION.equals(orderStatus)) {
                // 配送信息提醒
                sender.sendOrderDeliveryMsg(map);
            } else if (OrderStatus.REFUNDED.equals(orderStatus)) {
                // 退款
                sender.orderRefund(map);
            } else if (OrderStatus.STOCKING.equals(orderStatus)) {
                // 订单确认信息提醒
//                sender.sendOrderConfirmMsg(map);
            }
        }
        return orderResult;
    }

    @ApiOperation(value = "获取订单详情列表", notes = "获取订单详情列表")
    @GetMapping(value = "/item/list/{orderId}")
    public Result<List<OrderItem>> listItemByOrderId(@PathVariable("orderId") @NotBlank String orderId) {
        return showOrderClient.listItemByOrderId(orderId);
    }

    @ApiOperation(value = "获取销售订单子单分页列表", notes = "获取销售订单子单分页列表")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<OrderItem>> pageOrderItem(@RequestBody PageParam<OrderItem> itemPage) {
        if (itemPage.getSidx().equals("mark_id") || StrUtil.isEmpty(itemPage.getSidx())) {
            itemPage.setSidx("o.orderTime");
        }
        return showOrderClient.pageOrderItem(itemPage);
    }

    @ApiOperation(value = "批量修改订单状态", notes = "批量修改订单状态")
    @PostMapping(value = "/batchStatus")
    public Result<?> batchOrderStatus(@RequestBody OrderBatch base) {
        Result<List<OrderInfo>> result = showOrderClient.batchOrderStatus(base);
        if (result.isSuccess()) {
            List<OrderInfo> orderList = result.getData();
            ShowAssert.checkTrue(orderList.isEmpty(), StatusCode._4024);
            Map<String, String> map = new HashMap<>(3);
            for (OrderInfo orderInfo : orderList) {
                map.put("orderNo", orderInfo.getOrderNo());
                map.put("userId", orderInfo.getUserId());
                map.put("orderId", orderInfo.getMarkId());
                // 订单备货提醒
                if (OrderStatus.STOCKING.equals(orderInfo.getOrderStatus())) {
                    sender.sendOrderConfirmMsg(map);
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "获取当天备货商品数量", notes = "获取当天备货商品数量")
    @PostMapping(value = "/today/product/quantity/page")
    public Result<PageGrid<ProductModel>> pageTodayProductQuantity(PageParam<?> pageParam) {
        return showOrderClient.pageTodayProductQuantity(pageParam);
    }
}
