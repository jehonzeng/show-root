package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.bean.wechat.vo.CouponBase;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.bean.wechat.vo.VoucherBase;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient("show-order")
public interface ShowOrderClient {

    @RequestMapping(value = "/holiday/delivery/list", method = RequestMethod.GET)
    Result<List<DeliveryDate>> listDeliveryDate();
    
    @RequestMapping(value = "/orders/info", method = RequestMethod.GET)
    Result<OrderInfo> getOrderByNo(@RequestParam("orderNo") String orderNo);
    
    @RequestMapping(value = "/orders/fore/all/list/", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listAll(@RequestBody PageParam<String> orderPage);
    
    @RequestMapping(value = "/orders/fore/unpaid/list/", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUnpaid(@RequestBody PageParam<String> orderPage);
    
    @RequestMapping(value = "/orders/fore/ungroup/list/", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUngroup(@RequestBody PageParam<String> orderPage);
    
    @RequestMapping(value = "/orders/fore/undelivery/list/", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUndelivery(@RequestBody PageParam<String> orderPage);
    
    @RequestMapping(value = "/orders/fore/unreceive/list/", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUnReceive(@RequestBody PageParam<String> orderPage);
    
    @RequestMapping(value = "/orders/fore/unjudge/list/", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUnjudge(@RequestBody PageParam<String> orderPage);
    
    @RequestMapping(value = "/orders/create", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> createOrder(@RequestBody OrderModel orderModel);
    
    @RequestMapping(value = "/orders/fore/detial", method = RequestMethod.GET)
    Result<OrderDetail> getOrderDetail(@RequestParam("orderNo") String orderNo);
    
    @RequestMapping(value = "/orders/item/fore/judge/{orderId}", method = RequestMethod.GET)
    Result<List<Judge>> listOrderItemJudge(@PathVariable("orderId") String orderId);
    
    @RequestMapping(value = "/orders/error/back", method = RequestMethod.PATCH)
    void orderErrorBack(@RequestParam("orderNo") String orderNo, @RequestParam("userId") String userId);
    
    @RequestMapping(value = "/orders/status", method = RequestMethod.GET)
    Result<?> modifyOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("orderStatus") String orderStatus);
    
    @RequestMapping(value = "/teambuyorder/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<TeambuyOrder> addTeambuyOrder(@RequestBody TeambuyModel model);
    
    @RequestMapping(value = "/teambuyorder/info", method = RequestMethod.GET)
    Result<TeambuyOrder> getTeambuyOrderByNo(@RequestParam("orderNo") String orderNo);
    
    @RequestMapping(value = "/seckillorder/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<SeckillOrder> addSeckillOrder(@RequestBody SeckillModel model);
    
    @RequestMapping(value = "/seckillorder/info", method = RequestMethod.GET)
    Result<SeckillOrder> getSeckillOrderByNo(@RequestParam("orderNo") String orderNo);
    
    @RequestMapping(value = "/usercoupon/list/{userId}", method = RequestMethod.GET)
    Result<List<CouponBase>> listCouponByUser(@PathVariable("userId") String userId, @RequestParam("type") Integer type);
    
    @RequestMapping(value = "/vouchers/list/{userId}", method =  RequestMethod.GET)
    Result<List<VoucherBase>> listVoucherByUser(@PathVariable("userId") String userId);
    
    @RequestMapping(value = "/address/list/{userId}", method = RequestMethod.GET)
    Result<List<UserAddress>> listAddressByUser(@PathVariable("userId") String userId);
    
    @RequestMapping(value = "/address/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addAddress(@RequestBody UserAddress address);
    
    @RequestMapping(value = "/address/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> modifyAddress(@RequestBody UserAddress address);
    
    @RequestMapping(value = "/address/def/{userId}", method = RequestMethod.GET)
    Result<UserAddress> getDefAddressByUser(@PathVariable("userId") String userId);
    
    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    Result<UserAddress> getAddressInfo(@PathVariable("addressId") String addressId);
}
