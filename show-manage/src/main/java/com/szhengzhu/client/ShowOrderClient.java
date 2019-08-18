package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient("show-order")
public interface ShowOrderClient {

    @RequestMapping(value = "/usercoupon/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<UserCoupon>> pageCoupon(@RequestBody PageParam<UserCoupon> couponPage);
    
    @RequestMapping(value = "/orders/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderInfo>> pageOrder(@RequestBody PageParam<OrderInfo> orderPage);
    
    @RequestMapping(value = "/orders/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addOrder(@RequestBody OrderInfo order);
    
    @RequestMapping(value = "/orders/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> modifyOrder(@RequestBody OrderInfo order);
    
    @RequestMapping(value = "/orders/{markId}", method = RequestMethod.GET)
    Result<OrderInfo> getOrderInfo(@PathVariable("markId") String markId);
    
    @RequestMapping(value = "/orders/status", method = RequestMethod.GET)
    Result<?> modifyOrderStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus);
    
    @RequestMapping(value = "/orders/item/list/{orderId}", method = RequestMethod.GET)
    Result<List<OrderItem>> listItemByOrderId(@PathVariable("orderId") String orderId);
    
    @RequestMapping(value = "/orders/item/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderItem>> pageOrderItem(@RequestBody PageParam<OrderItem> itemPage);
    
    @RequestMapping(value = "/orders/delivery/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderDelivery>> pageDelivery(@RequestBody PageParam<OrderDelivery> deliveryPage);
    
    @RequestMapping(value = "/orders/delivery", method = RequestMethod.GET)
    Result<OrderDelivery> getDeliveryById(@RequestParam("orderId") String orderId);
    
    @RequestMapping(value = "/orders/delivery/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> modifyDelivery(@RequestBody OrderDelivery orderDelivery);
    
    @RequestMapping(value = "/seckillorder/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SeckillOrder>> pageSeckillOrder(@RequestBody PageParam<SeckillOrder> orderPage);
    
    @RequestMapping(value = "/seckillorder/status", method = RequestMethod.GET)
    Result<?> modifySeckillStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus);
    
    @RequestMapping(value = "/teambuyorder/group/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<TeambuyGroup>> pageTeambuyGroup(@RequestBody PageParam<TeambuyGroup> groupPage);
    
    @RequestMapping(value = "/teambuyorder/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<TeambuyOrder>> pageTeambuyOrder(@RequestBody PageParam<TeambuyOrder> orderPage);
    
    @RequestMapping(value = "/teambuyorder/status", method = RequestMethod.GET)
    Result<?> modifyItemStatus(@RequestParam("markId") String markId, @RequestParam("orderStatus") String orderStatus);
    
    @RequestMapping(value = "/holiday/operate/{holiday}", method = RequestMethod.PATCH)
    Result<?> operateHoliday(@PathVariable("holiday") String holiday);
    
    @RequestMapping(value = "/holiday/list", method = RequestMethod.GET)
    Result<List<HolidayInfo>> listHoliday(@RequestParam("start") String start, @RequestParam("end") String end);
    
    @RequestMapping(value = "/address/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<UserAddress>> pageAddress(@RequestBody PageParam<UserAddress> addressPage);
    
    @RequestMapping(value="/usercoupon/addByUser",method = RequestMethod.POST,consumes = Contacts.CONSUMES)
    Result<?> sendCoupon(@RequestBody List<UserCoupon> base);

    @RequestMapping(value="/usercoupon/addByRole",method = RequestMethod.POST,consumes = Contacts.CONSUMES)
    Result<?> sendCouponByRole(@RequestBody List<UserCoupon> base, @RequestParam("roleId") String roleId);
}
