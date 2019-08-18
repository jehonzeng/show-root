package com.szhengzhu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.CalcData;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.HolidayService;
import com.szhengzhu.service.OrderDeliveryService;
import com.szhengzhu.service.OrderService;
import com.szhengzhu.service.UserAddressService;
import com.szhengzhu.service.UserCouponService;
import com.szhengzhu.service.UserVoucherService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDeliveryService orderDeliveryService;

    @Resource
    private UserVoucherService userVoucherService;

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private UserAddressService userAddressService;

    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @Resource
    private HolidayService holidayService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<OrderInfo>> pageOrder(@RequestBody PageParam<OrderInfo> orderPage) {
        if (orderPage == null)
            return new Result<>(StatusCode._4004);
        return orderService.pageOrder(orderPage);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<OrderInfo> getOrderInfo(@PathVariable("markId") String markId) {
        if (StringUtils.isEmpty(markId))
            return new Result<>(StatusCode._4004);
        return orderService.getOrderInfo(markId);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<OrderInfo> getOrderByNo(@RequestParam("orderNo") String orderNo) {
        if (StringUtils.isEmpty(orderNo))
            return new Result<>(StatusCode._4004);
        return orderService.getOrderByNo(orderNo);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addOrder(@RequestBody OrderInfo order) {
        if (order == null || order.getItems().size() == 0 || order.getOrderDelivery() == null)
            return new Result<>(StatusCode._4004);
        List<Map<String, Object>> goodsList = new ArrayList<>();
        List<Map<String, Object>> mealList = new ArrayList<>();
        for (int index = 0, size = order.getItems().size(); index < size; index++) {
            OrderItem item = order.getItems().get(index);
            if (item.getProductType().equals(0)) {
                Map<String, Object> goods = new HashMap<>();
                goods.put("goodsId", item.getProductId());
                goods.put("specIds", item.getSpecificationIds());
                goods.put("quantity", item.getQuantity());
                goodsList.add(goods);
            } else if (item.getProductType().equals(2)) {
                Map<String, Object> meal = new HashMap<>();
                meal.put("mealId", item.getProductId());
                meal.put("quantity", item.getQuantity());
                mealList.add(meal);
            }
        }
        if (goodsList.size() > 0)
            return null;

        if (mealList.size() > 0)
            return null;

        List<String> markIds = new ArrayList<>();
        for (OrderItem item : order.getItems())
            markIds.add(item.getProductId());
        List<StockVo> stocks = new ArrayList<>();
        if (markIds.size() > 0)
            stocks = showGoodsClient.listGoodsStocks(markIds).getData();
        if (stocks.size() > 0 && (stocks.size() == markIds.size()))
            return orderService.addBackendOrder(order, stocks);
        else
            return new Result<>(StatusCode._5003);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modifyOrder(@RequestBody OrderInfo order) {
        if (order == null || order.getItems().size() == 0 || order.getOrderDelivery() == null
                || StringUtils.isEmpty(order.getMarkId()))
            return new Result<>(StatusCode._4004);
        List<String> markIds = new ArrayList<>();
        for (OrderItem item : order.getItems())
            markIds.add(item.getProductId());
        List<StockVo> stocks = new ArrayList<>();
        if (markIds.size() > 0)
            stocks = showGoodsClient.listGoodsStocks(markIds).getData();
        if (stocks.size() > 0 && stocks.size() == markIds.size())
            return orderService.modifyBackendOrder(order, stocks);
        else
            return new Result<>(StatusCode._5003);
    }

    @RequestMapping(value = "/delivery/page", method = RequestMethod.POST)
    public Result<PageGrid<OrderDelivery>> pageDelivery(@RequestBody PageParam<OrderDelivery> deliveryPage) {
        if (deliveryPage == null)
            return new Result<>(StatusCode._4004);
        return orderDeliveryService.pageDelivery(deliveryPage);
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public Result<OrderDelivery> getDeliveryByOrder(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId))
            return new Result<>(StatusCode._4004);
        return orderDeliveryService.getDeliveryByOrderId(orderId);
    }

    @RequestMapping(value = "/delivery/modify", method = RequestMethod.PATCH)
    public Result<?> modifyDelivery(@RequestBody OrderDelivery orderDelivery) {
        if (orderDelivery == null || StringUtils.isEmpty(orderDelivery.getMarkId()))
            return new Result<>(StatusCode._4004);
        return orderDeliveryService.modifyDelivery(orderDelivery);
    }

    @RequestMapping(value = "/item/list/{orderId}", method = RequestMethod.GET)
    public Result<List<OrderItem>> listItemByOrderId(@PathVariable("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId))
            return new Result<>(StatusCode._4004);
        return orderService.listItemByOrderId(orderId);
    }

    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<OrderItem>> pageItem(@RequestBody PageParam<OrderItem> itemPage) {
        if (itemPage == null)
            return new Result<>(StatusCode._4004);
        return orderService.pageItem(itemPage);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Result<?> modfiyStatus(@RequestParam("markId") String markId,
            @RequestParam("orderStatus") String orderStatus) {
        if (StringUtils.isEmpty(markId) || StringUtils.isEmpty(orderStatus))
            return new Result<>(StatusCode._4004);
        return orderService.updateStatus(markId, orderStatus);
    }

    @RequestMapping(value = "/fore/all/list", method = RequestMethod.POST)
    public Result<PageGrid<OrderBase>> listAll(@RequestBody PageParam<String> orderPage) {
        if (orderPage == null || StringUtils.isEmpty(orderPage.getData()))
            return new Result<>(StatusCode._4004);
        return orderService.listAll(orderPage);
    }

    @RequestMapping(value = "/fore/unpaid/list", method = RequestMethod.POST)
    public Result<PageGrid<OrderBase>> listUnpaid(@RequestBody PageParam<String> orderPage) {
        if (orderPage == null || StringUtils.isEmpty(orderPage.getData()))
            return new Result<>(StatusCode._4004);
        return orderService.listUnpaid(orderPage);
    }

    @RequestMapping(value = "/fore/ungroup/list", method = RequestMethod.POST)
    public Result<PageGrid<OrderBase>> listUngroup(@RequestBody PageParam<String> orderPage) {
        if (orderPage == null || StringUtils.isEmpty(orderPage.getData()))
            return new Result<>(StatusCode._4004);
        return orderService.listUngroup(orderPage);
    }

    @RequestMapping(value = "/fore/undelivery/list", method = RequestMethod.POST)
    public Result<PageGrid<OrderBase>> listUndelivery(@RequestBody PageParam<String> orderPage) {
        if (orderPage == null || StringUtils.isEmpty(orderPage.getData()))
            return new Result<>(StatusCode._4004);
        return orderService.listUndelivery(orderPage);
    }

    @RequestMapping(value = "/fore/unreceive/list", method = RequestMethod.POST)
    public Result<PageGrid<OrderBase>> listUnReceive(@RequestBody PageParam<String> orderPage) {
        if (orderPage == null || StringUtils.isEmpty(orderPage.getData()))
            return new Result<>(StatusCode._4004);
        return orderService.listUnReceive(orderPage);
    }

    @RequestMapping(value = "/fore/unjudge/list", method = RequestMethod.POST)
    public Result<PageGrid<OrderBase>> listUnjudge(@RequestBody PageParam<String> orderPage) {
        if (orderPage == null || StringUtils.isEmpty(orderPage.getData()))
            return new Result<>(StatusCode._4004);
        return orderService.listUnjudge(orderPage);
    }

    @RequestMapping(value = "/fore/detial", method = RequestMethod.GET)
    public Result<?> orderDetail(@RequestParam("orderNo") String orderNo) {
        if (StringUtils.isEmpty(orderNo))
            return new Result<>(StatusCode._4004);
        return orderService.getOrderDetail(orderNo);
    }

    @RequestMapping(value = "/item/fore/judge/{orderId}", method = RequestMethod.GET)
    public Result<List<Judge>> listItemJudge(@PathVariable("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId))
            return new Result<>(StatusCode._4004);
        return orderService.listItemJudge(orderId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<?> createOrder(@RequestBody OrderModel orderModel) throws CloneNotSupportedException {
        if (orderModel == null || orderModel.getItem().size() == 0 || StringUtils.isEmpty(orderModel.getAddressId())
                || StringUtils.isEmpty(orderModel.getUserId()))
            return new Result<>(StatusCode._4004);
        Result<CalcData> calcResult = showGoodsClient.calcTotal(orderModel);
        if (!calcResult.isSuccess())
            return calcResult;
        return orderService.createOrder(calcResult.getData());
    }
    
    @RequestMapping(value = "/error/back", method = RequestMethod.PATCH)
    public void orderErrorBack(@RequestParam("orderNo") String orderNo, @RequestParam("userId") String userId) {
        orderService.orderErrorBack(orderNo, userId);
    }
    
    @RequestMapping(value = "/info/back", method = RequestMethod.PATCH)
    public void orderInfoBack(@RequestParam("orderNo") String orderNo) {
        List<OrderItem> items = orderService.orderInfoBack(orderNo);
        // 修改库存
    }
}
