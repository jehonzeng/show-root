package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.excel.LogisticsModel;
import com.szhengzhu.bean.excel.OrderSendModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.bean.vo.OrderBatch;
import com.szhengzhu.bean.vo.OrderExportVo;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.OrderAllService;
import com.szhengzhu.service.OrderDeliveryService;
import com.szhengzhu.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单操作
 *
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderAllService orderAllService;

    @Resource
    private OrderDeliveryService orderDeliveryService;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @PostMapping(value = "/page")
    public PageGrid<OrderInfo> pageOrder(@RequestBody PageParam<OrderInfo> orderPage) {
        return orderService.pageOrder(orderPage);
    }

    @PostMapping(value = "/share/page")
    public PageGrid<OrderInfo> pageShareOrder(@RequestBody PageParam<OrderInfo> orderPage) {
        return orderService.pageShareOrder(orderPage);
    }

    @GetMapping(value = "/{markId}")
    public OrderInfo getOrderDeliveryItem(@PathVariable("markId") @NotBlank String markId) {
        return orderService.getOrderDeliveryItem(markId);
    }

    @GetMapping(value = "/info/{orderId}")
    public OrderInfo getOrderById(@PathVariable("orderId") @NotBlank String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping(value = "/info")
    public OrderInfo getOrderByNo(@RequestParam("orderNo") @NotBlank String orderNo) {
        return orderService.getOrderByNo(orderNo);
    }

    @PostMapping(value = "/add")
    public Result<String> addOrder(@RequestBody @Validated OrderInfo order) throws Exception {
        OrderDelivery orderDelivery = order.getOrderDelivery();

        List<OrderItem> itemList = order.getItems();
        List<OrderItemModel> itemModelList = new ArrayList<>();
        OrderItemModel itemModel;
        for (int index = 0, size = itemList.size(); index < size; index++) {
            itemModel = OrderItemModel.builder().productId(itemList.get(index).getProductId())
                    .productType(itemList.get(index).getProductType())
                    .specificationIds(itemList.get(index).getSpecificationIds())
                    .quantity(itemList.get(index).getQuantity()).build();
            itemModelList.add(itemModel);
        }
        OrderModel orderModel = OrderModel.builder().item(itemModelList).userId(order.getUserId())
                .deliveryDate(order.getDeliveryDate()).remark(order.getRemark()).build();
        ShowAssert.checkNull(orderModel, StatusCode._4004);
        ShowAssert.checkTrue(orderModel.getItem().isEmpty() || StrUtil.isEmpty(orderModel.getUserId()), StatusCode._4004);
        Result<CalcData> calcResult = showGoodsClient.calcTotal(orderModel);
        ShowAssert.checkResult(calcResult);
        String orderId = orderService.addBackendOrder(calcResult.getData(), orderDelivery, order.getOrderType(),
                order.getDeliveryAmount());
        return new Result<>(orderId);
    }

    @PatchMapping(value = "/modify")
    public void modifyOrder(@RequestBody @Validated OrderInfo order) {
        orderService.modifyBackendOrder(order);
    }

    @PostMapping(value = "/delivery/page")
    public PageGrid<OrderDelivery> pageDelivery(@RequestBody PageParam<OrderDelivery> deliveryPage) {
        return orderDeliveryService.pageDelivery(deliveryPage);
    }

    @GetMapping(value = "/delivery")
    public OrderDelivery getDeliveryByOrder(@RequestParam("orderId") @NotBlank String orderId) {
        return orderDeliveryService.getDeliveryByOrderId(orderId);
    }

    @PatchMapping(value = "/delivery/modify")
    public void modifyDelivery(@RequestBody OrderDelivery orderDelivery) {
        orderDeliveryService.modifyDelivery(orderDelivery);
    }

    @GetMapping(value = "/item/list/{orderId}")
    public List<OrderItem> listItemByOrderId(@PathVariable("orderId") @NotBlank String orderId) {
        return orderService.listItemByOrderId(orderId);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<OrderItem> pageItem(@RequestBody PageParam<OrderItem> itemPage) {
        return orderService.pageItem(itemPage);
    }

    @GetMapping(value = "/backend/index")
    public List<IndexDisplay> getIndexStatusCount() {
        return orderService.getIndexStatusCount();
    }

    @GetMapping(value = "/status")
    public OrderInfo modfiyStatus(@RequestParam("orderId") @NotBlank String orderId,
                                  @RequestParam("orderStatus") @NotBlank String orderStatus) {
        return orderService.modifyStatus(orderId, orderStatus);
    }

    @GetMapping(value = "/fore/status")
    public void modifyStatusByNo(@RequestParam("orderNo") @NotBlank String orderNo,
                                 @RequestParam("orderStatus") @NotBlank String orderStatus,
                                 @RequestParam(value = "userId", required = false) String userId) {
        orderService.modifyStatusByNo(orderNo, orderStatus, userId);
    }

    @PostMapping(value = "/fore/all/list")
    public PageGrid<OrderBase> listAll(@RequestBody PageParam<String> orderPage) {
        ShowAssert.checkStrEmpty(orderPage.getData(), StatusCode._4004);
        return orderAllService.listAll(orderPage);
    }

    @PostMapping(value = "/fore/unpaid/list")
    public PageGrid<OrderBase> listUnpaid(@RequestBody PageParam<String> orderPage) {
        ShowAssert.checkStrEmpty(orderPage.getData(), StatusCode._4004);
        return orderAllService.listUnpaid(orderPage);
    }

    @PostMapping(value = "/fore/ungroup/list")
    public PageGrid<OrderBase> listUngroup(@RequestBody PageParam<String> orderPage) {
        ShowAssert.checkStrEmpty(orderPage.getData(), StatusCode._4004);
        return orderAllService.listUngroup(orderPage);
    }

    @PostMapping(value = "/fore/undelivery/list")
    public PageGrid<OrderBase> listUndelivery(@RequestBody PageParam<String> orderPage) {
        ShowAssert.checkStrEmpty(orderPage.getData(), StatusCode._4004);
        return orderAllService.listUndelivery(orderPage);
    }

    @PostMapping(value = "/fore/unreceive/list")
    public PageGrid<OrderBase> listUnReceive(@RequestBody PageParam<String> orderPage) {
        ShowAssert.checkStrEmpty(orderPage.getData(), StatusCode._4004);
        return orderAllService.listUnReceive(orderPage);
    }

    @PostMapping(value = "/fore/unjudge/list")
    public PageGrid<OrderBase> listUnjudge(@RequestBody PageParam<String> orderPage) {
        ShowAssert.checkStrEmpty(orderPage.getData(), StatusCode._4004);
        return orderAllService.listUnjudge(orderPage);
    }

    @GetMapping(value = "/fore/detial")
    public OrderDetail orderDetail(@RequestParam("orderNo") @NotBlank String orderNo,
                                   @RequestParam(value = "userId", required = false) String userId) {
        return orderService.getOrderDetail(orderNo, userId);
    }

    @GetMapping(value = "/item/fore/judge")
    public List<Judge> listItemJudge(@RequestParam("orderNo") @NotBlank String orderNo,
                                     @RequestParam("userId") @NotBlank String userId) {
        return orderService.listItemJudge(orderNo, userId);
    }

    @PostMapping(value = "/create")
    public Map<String, Object> create(@RequestBody OrderModel orderModel) throws Exception {
        Result<CalcData> calcResult = showGoodsClient.calcTotal(orderModel);
        ShowAssert.checkResult(calcResult);
        return orderService.create(calcResult.getData());
    }

    @GetMapping(value = "/list/status")
    public List<OrderInfo> listStatuaOrder(@RequestParam("orderStatus") @NotBlank String orderStatus) {
        return orderService.listStatusOrder(orderStatus);
    }

    @GetMapping(value = "/print/{markId}")
    public OrderExportVo getExportOrderInfo(@PathVariable("markId") @NotBlank String markId) {
        return orderService.getExportOrdersById(markId);
    }

    @GetMapping(value = "/item/todayList")
    public List<TodayProductVo> getTodayItemList() {
        return orderAllService.getTodayList();
    }

    @PostMapping(value = "/delivery/batch")
    public Map<String, Object> batchModifyDeliveryOrder(@RequestBody List<LogisticsModel> deliveryList) {
        return orderDeliveryService.batchModifyDeliveryOrder(deliveryList);
    }

    @GetMapping(value = "/fore/status/count")
    public List<Map<String, Object>> getStatusCount(@RequestParam("userId") @NotBlank String userId) {
        return orderAllService.getStatusCount(userId);
    }

    @PostMapping(value = "/batchStatus")
    public List<OrderInfo> batchOrderStatus(@RequestBody OrderBatch base) {
        return orderService.batchUpdateStatus(base);
    }

    @PostMapping(value = "/send/page")
    public PageGrid<OrderSendModel> pageSendInfo(@RequestBody PageParam<String> page) {
        return orderAllService.pageSendInfo(page);
    }

    @GetMapping(value = "/send/list")
    public List<OrderSendModel> listSendInfo() {
        return orderAllService.listSendInfo();
    }

    @PostMapping(value = "/today/product/quantity/page")
    public PageGrid<ProductModel> pageTodayProductQuantity(@RequestBody PageParam<?> pageParam) {
        return orderAllService.pageTodayProductQuantity(pageParam);
    }

    @GetMapping(value = "/today/product/quantity/list")
    public List<ProductModel> listTodayProductQuantity() {
        return orderAllService.listTodayProductQuantity();
    }
}
