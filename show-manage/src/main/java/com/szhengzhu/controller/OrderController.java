package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.ShowUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单管理：OrderController")
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Resource
    private ShowOrderClient showOrderClient;
    
    @ApiOperation(value = "获取订单分页列表", notes = "获取订单分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<OrderInfo>> pageOrder(@RequestBody PageParam<OrderInfo> orderPage) {
        return showOrderClient.pageOrder(orderPage);
    }
    
    @ApiOperation(value = "后台添加订单", notes = "后台添加订单")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> addOrder(@RequestBody OrderInfo order, HttpSession session) {
        if (order == null || order.getOrderDelivery() == null || order.getItems().size() == 0)
            return new Result<>(StatusCode._4004);
        String userId = (String) session.getAttribute(Commons.SESSION);
        order.setUserId(userId);
        order.setOrderNo(ShowUtils.createOrderNo(1, userId));
        return showOrderClient.addOrder(order);
    }
    
    @ApiOperation(value = "后台修改订单", notes = "后台修改订单")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<?> modifyOrder(@RequestBody OrderInfo order, HttpSession session) {
        String userId = (String) session.getAttribute(Commons.SESSION);
        order.setUserId(userId);
        return showOrderClient.modifyOrder(order);
    }
    
    @ApiOperation(value = "获取订单详情及订单其他详细", notes = "获取订单详情及订单其他详细信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<OrderInfo> getOrderInfo(@PathVariable("markId") String markId) {
        return showOrderClient.getOrderInfo(markId);
    }
    
    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Result<?> modifyOrderStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus) {
        return showOrderClient.modifyOrderStatus(markId, orderStatus);
    }
    
    @ApiOperation(value = "获取订单详情列表", notes = "获取订单详情列表")
    @RequestMapping(value = "/item/list/{orderId}", method = RequestMethod.GET)
    public Result<List<OrderItem>> listItemByOrderId(@PathVariable("orderId") String orderId) {
        return showOrderClient.listItemByOrderId(orderId);
    }
    
    @ApiOperation(value = "获取销售订单子单分页列表", notes = "获取销售订单子单分页列表")
    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<OrderItem>> pageOrderItem(@RequestBody PageParam<OrderItem> itemPage) {
        return showOrderClient.pageOrderItem(itemPage);
    }
    
    @ApiOperation(value = "获取配送地址列表", notes = "获取配送地址列表")
    @RequestMapping(value = "/delivery/page", method = RequestMethod.POST)
    public Result<PageGrid<OrderDelivery>> pageDelivery(@RequestBody PageParam<OrderDelivery> deliveryPage) {
        return showOrderClient.pageDelivery(deliveryPage);
    }
    
    @ApiOperation(value = "获取订单配送详情", notes = "获取订单配送详情")
    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public Result<OrderDelivery> getDeliveryByOrderId(@RequestParam("orderId") String orderId) {
        return showOrderClient.getDeliveryById(orderId);
    }
    
    @ApiOperation(value = "修改订单配送信息", notes = "修改订单配送信息")
    @RequestMapping(value = "/delivery", method = RequestMethod.PATCH)
    public Result<?> modifyDelivery(@RequestBody OrderDelivery orderDelivery) {
        return showOrderClient.modifyDelivery(orderDelivery);
    }
    
    @ApiOperation(value = "获取秒杀订单分页列表", notes = "获取订单分页列表")
    @RequestMapping(value = "/seckill/page", method = RequestMethod.POST)
    public Result<PageGrid<SeckillOrder>> pageSeckill(@RequestBody PageParam<SeckillOrder> orderPage) {
        return showOrderClient.pageSeckillOrder(orderPage);
    }
    
    @ApiOperation(value = "修改秒杀订单状态", notes = "修改秒杀订单状态")
    @RequestMapping(value = "/seckill/status", method = RequestMethod.GET)
    public Result<?> modifySeckillStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus) {
        return showOrderClient.modifySeckillStatus(markId, orderStatus);
    }
    
    @ApiOperation(value = "获取团购团单组分页列表", notes = "获取团购团单组分页列表")
    @RequestMapping(value = "/teambuy/page", method = RequestMethod.POST)
    public Result<PageGrid<TeambuyGroup>> pageTeambuy(@RequestBody PageParam<TeambuyGroup> groupPage) {
        return showOrderClient.pageTeambuyGroup(groupPage);
    }
    
    @ApiOperation(value = "获取团购子订单分页列表", notes = "获取团单子订单分页列表")
    @RequestMapping(value = "/teambuy/order/page", method = RequestMethod.POST)
    public Result<PageGrid<TeambuyOrder>> pageTeambuyOrder(@RequestBody PageParam<TeambuyOrder> orderPage) {
        return showOrderClient.pageTeambuyOrder(orderPage);
    }
    
    @RequestMapping(value = "/teambuy/order/status", method = RequestMethod.GET)
    public Result<?> modifyItemStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus) {
        return showOrderClient.modifyItemStatus(markId, orderStatus);
    }
}
