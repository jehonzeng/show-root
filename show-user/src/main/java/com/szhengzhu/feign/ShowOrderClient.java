package com.szhengzhu.feign;

import com.szhengzhu.bean.excel.*;
import com.szhengzhu.bean.order.*;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.ordering.param.GiveParam;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.bean.rpt.SaleParam;
import com.szhengzhu.bean.rpt.SaleStatistics;
import com.szhengzhu.bean.vo.*;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ExceptionAdvice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

@FeignClient(name = "show-order", fallback = ExceptionAdvice.class)
public interface ShowOrderClient {

    /**
     * 用户地址
     */
    @GetMapping(value = "/address/{addressId}")
    Result<UserAddress> getAddressInfo(@PathVariable("addressId") String addressId);

    @PostMapping(value = "/address/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<UserAddress>> pageAddress(@RequestBody PageParam<UserAddress> addressPage);

    @GetMapping(value = "/address/list/{userId}")
    Result<List<UserAddress>> listAddressByUser(@PathVariable("userId") String userId);

    @PostMapping(value = "/address/add", consumes = Contacts.CONSUMES)
    Result<UserAddress> addAddress(@RequestBody UserAddress address);

    @PatchMapping(value = "/address/modify", consumes = Contacts.CONSUMES)
    Result modifyAddress(@RequestBody UserAddress address);

    @GetMapping(value = "/address/def/{userId}")
    Result<UserAddress> getDefAddressByUser(@PathVariable("userId") String userId);

    /**
     * 地区信息
     */
    @GetMapping(value = "/attributes/combobox")
    Result<List<Combobox>> listCombobox(@RequestParam("type") String type);

    /**
     * 回滚信息
     */
    @PostMapping(value = "/back/history/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<BackHistory>> pageOrderBack(@RequestBody PageParam<BackHistory> pageParam);

    @PostMapping(value = "/back/refund/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<RefundBack>> pageRefundBack(@RequestBody PageParam<RefundBack> pageParam);

    @GetMapping(value = "/back/refund/{markId}")
    Result<RefundBack> getRefundBackInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/back/refund", consumes = Contacts.CONSUMES)
    Result addRefundBack(@RequestBody RefundBack back);

    @PostMapping(value = "/back/history", consumes = Contacts.CONSUMES)
    Result addOrderBack(@RequestBody BackHistory backHistory);

    @GetMapping(value = "/back/error")
    Result addErrorBack(@RequestParam("orderNo") String orderNo, @RequestParam("userId") String userId);

    @PostMapping(value = "/back/fore/record", consumes = Contacts.CONSUMES)
    Result addOrderRecord(@RequestBody OrderRecord orderRecord);

    /**
     * 到处excel
     */
    @GetMapping(value = "/excels/delivery")
    Result<List<DeliveryModel>> getDownDeliveryList();

    @GetMapping(value = "/excels/sauce")
    Result<List<SauceVo>> getDownSauceList();

    @GetMapping(value = "/excels/operator/product")
    Result<List<ProductModel>> getProductList();

    /**
     * 节假日
     */
    @GetMapping(value = "/holiday/{date}")
    Result<HolidayInfo> getHoliday(@PathVariable("date") Date date);

    @GetMapping(value = "/holiday/operate/{holiday}")
    Result operateHoliday(@PathVariable("holiday") String holiday);

    @GetMapping(value = "/holiday/list")
    Result<List<HolidayInfo>> listHoliday(@RequestParam("start") String start,
                                          @RequestParam("end") String end);

    @GetMapping(value = "/holiday/delivery/list")
    Result<List<DeliveryDate>> listDeliveryDate();

    /**
     * 收银订单
     */
    @GetMapping(value = "/indent/select/markId")
    Result<IndentInfo> selectById(@RequestParam("markId") String markId, @RequestParam(value = "userId", required = false) String userId);

    /**
     * 商城订单
     */
    @PostMapping(value = "/orders/create", consumes = Contacts.CONSUMES)
    Result<Map<String, Object>> createOrder(@RequestBody OrderModel orderModel);

    @PostMapping(value = "/orders/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderInfo>> pageOrder(@RequestBody PageParam<OrderInfo> orderPage);

    @PostMapping(value = "/orders/share/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderInfo>> pageShareOrder(@RequestBody PageParam<OrderInfo> orderPage);

    @PostMapping(value = "/orders/add", consumes = Contacts.CONSUMES)
    Result<String> addOrder(@RequestBody OrderInfo order);

    @PatchMapping(value = "/orders/modify", consumes = Contacts.CONSUMES)
    Result modifyOrder(@RequestBody OrderInfo order);

    @GetMapping(value = "/orders/{markId}")
    Result<OrderInfo> getOrderInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/orders/info")
    Result<OrderInfo> getOrderByNo(@RequestParam("orderNo") String orderNo);

    @GetMapping(value = "/orders/status")
    Result<OrderInfo> modifyOrderStatus(@RequestParam("orderId") String orderId,
                                        @RequestParam("orderStatus") String orderStatus);

    @PostMapping(value = "/orders/contact")
    Result<List<OrderInfo>> contactUser(@RequestBody ContactUser user);

    @GetMapping(value = "/orders/delivery")
    Result<OrderDelivery> getDeliveryByOrder(@RequestParam("orderId") String orderId);

    @GetMapping(value = "/orders/info/{orderId}")
    Result<OrderInfo> getOrderById(@PathVariable("orderId") String orderId);

    @GetMapping(value = "/orders/backend/index")
    Result<List<IndexDisplay>> getIndexStatusCount();

    @GetMapping(value = "/orders/print/{markId}")
    Result<OrderExportVo> getExportOrderInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/orders/batchStatus", consumes = Contacts.CONSUMES)
    Result<List<OrderInfo>> batchOrderStatus(@RequestBody OrderBatch base);

    @PostMapping(value = "/orders/send/page")
    Result<PageGrid<OrderSendModel>> pageSendInfo(PageParam<String> page);

    @GetMapping(value = "/orders/send/list")
    Result<List<OrderSendModel>> listSendInfo();

    @PostMapping(value = "/orders/today/product/quantity/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ProductModel>> pageTodayProductQuantity(@RequestBody PageParam<?> pageParam);

    @GetMapping(value = "/orders/today/product/quantity/list")
    Result<List<ProductModel>> listTodayProductQuantity();

    @PostMapping(value = "/orders/fore/all/list/", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listAll(@RequestBody PageParam<String> orderPage);

    @PostMapping(value = "/orders/fore/unpaid/list/", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUnpaid(@RequestBody PageParam<String> orderPage);

    @PostMapping(value = "/orders/fore/ungroup/list/", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUngroup(@RequestBody PageParam<String> orderPage);

    @PostMapping(value = "/orders/fore/undelivery/list/", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUndelivery(@RequestBody PageParam<String> orderPage);

    @PostMapping(value = "/orders/fore/unreceive/list/", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUnReceive(@RequestBody PageParam<String> orderPage);

    @PostMapping(value = "/orders/fore/unjudge/list/", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderBase>> listUnjudge(@RequestBody PageParam<String> orderPage);

    @GetMapping(value = "/orders/fore/detial")
    Result<OrderDetail> getOrderDetail(@RequestParam("orderNo") String orderNo, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/orders/fore/status")
    Result modifyOrderStatusByNo(@RequestParam("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/orders/item/fore/judge")
    Result<List<Judge>> listOrderItemJudge(@RequestParam("orderNo") String orderNo, @RequestParam(value = "userId", required = false) String userId);

//    @GetMapping(value = "/orders/list/status")
//    Result<List<OrderInfo>> listStatusOrder(@RequestParam("orderStatus") String orderStatus);

    @GetMapping(value = "/orders/fore/status/count")
    Result<List<Map<String, Object>>> getStatusCount(@RequestParam("userId") String userId);


    /**
     * 订单明细
     */
    @GetMapping(value = "/orders/item/todayList")
    Result<List<TodayProductVo>> listTodayItem();

    @GetMapping(value = "/orders/item/list/{orderId}")
    Result<List<OrderItem>> listItemByOrderId(@PathVariable("orderId") String orderId);

    @PostMapping(value = "/orders/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderItem>> pageOrderItem(@RequestBody PageParam<OrderItem> itemPage);

    /**
     * 订单配送
     */
    @PostMapping(value = "/orders/delivery/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<OrderDelivery>> pageDelivery(
            @RequestBody PageParam<OrderDelivery> deliveryPage);

    @GetMapping(value = "/orders/delivery")
    Result<OrderDelivery> getDeliveryById(@RequestParam("orderId") String orderId);

    @PatchMapping(value = "/orders/delivery/modify", consumes = Contacts.CONSUMES)
    Result modifyDelivery(@RequestBody OrderDelivery orderDelivery);

    @PostMapping(value = "/orders/delivery/batch", consumes = Contacts.CONSUMES)
    Result<Map<String, Object>> batchModifyDeliveryOrder(
            @RequestBody List<LogisticsModel> deliveryList);

    /**
     * 报表信息
     */
    @GetMapping(value = "/rpt/sale/statistic")
    Result<List<SaleStatistics>> rptSaleStatistics(@RequestParam("month") String month,
                                                   @RequestParam("partner") String partner);

    @GetMapping(value = "/rpt/year/sale")
    Result<List<Map<String, Object>>> rptYearSale();

    @GetMapping(value = "/rpt/city/delivery/count")
    Result<List<Map<String, Object>>> rptCityDeliveryCount();

    @PostMapping(value = "/rpt/sale/volume", consumes = Contacts.CONSUMES)
    Result<List<Map<String, Object>>> rptSaleVolume(@RequestBody SaleParam param);

    /**
     * 秒杀
     */
    @GetMapping(value = "/seckill/info")
    Result<SeckillOrder> getSeckillOrderByNo(@RequestParam("orderNo") String orderNo);

    @PostMapping(value = "/seckill/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SeckillOrder>> pageSeckillOrder(@RequestBody PageParam<SeckillOrder> orderPage);

    @GetMapping(value = "/seckill/status")
    Result<SeckillOrder> modifySeckillStatus(@RequestParam("orderId") String orderId,
                                             @RequestParam("orderStatus") String orderStatus);

    @GetMapping(value = "/seckill/print/{markId}")
    Result<ExportTemplateVo> getExportSekillOrderInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/seckill/create", consumes = Contacts.CONSUMES)
    Result<SeckillOrder> createSeckillOrder(@RequestBody SeckillModel model);

    @GetMapping(value = "/seckill/{orderId}")
    Result<SeckillOrder> getSeckillById(@PathVariable("orderId") String orderId);

    @GetMapping(value = "/seckill/fore/status")
    Result modifySeckillStatusByNo(@RequestParam("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/seckill/fore/detial")
    Result<OrderDetail> getSeckillDetail(@RequestParam("orderNo") String orderNo, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/seckill/item/fore/judge")
    Result<List<Judge>> listSeckillItemJudge(@RequestParam("orderNo") String orderNo, @RequestParam("userId") String userId);

    /**
     * 团购
     */
    @PatchMapping(value = "/teambuy/info")
    Result<TeambuyOrder> getTeambuyOrderByNo(@RequestParam("orderNo") String orderNo);

    @GetMapping(value = "/teambuy/print/{markId}")
    Result<ExportTemplateVo> getExportTeambuyOrderInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/teambuy/group/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<TeambuyGroup>> pageTeambuyGroup(@RequestBody PageParam<TeambuyGroup> groupPage);

    @GetMapping(value = "/teambuy/group/{markId}")
    Result<TeambuyGroup> getTeambuyGroupInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/teambuy/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<TeambuyOrder>> pageTeambuyOrder(@RequestBody PageParam<TeambuyOrder> orderPage);

    @GetMapping(value = "/teambuy/status")
    Result<TeambuyOrder> modifyItemStatus(@RequestParam("orderId") String orderId,
                                          @RequestParam("orderStatus") String orderStatus);

    @PostMapping(value = "/teambuy/create", consumes = Contacts.CONSUMES)
    Result<TeambuyOrder> createTeambuyOrder(@RequestBody TeambuyModel model);

    @GetMapping(value = "/teambuy/{orderId}")
    Result<TeambuyOrder> getTeambuyById(@PathVariable("orderId") String orderId);

    @GetMapping(value = "/teambuy/fore/status")
    Result modifyTeambuyStatusByNo(@RequestParam("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/teambuy/fore/detial")
    Result<OrderDetail> getTeambuyDetail(@RequestParam("orderNo") String orderNo, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/teambuy/item/fore/judge")
    Result<List<Judge>> listTeambuyItemJudge(@RequestParam("orderNo") String orderNo, @RequestParam("userId") String userId);


    /**
     * 物流信息
     */
    @GetMapping(value = "/track/info/delivery")
    Result<Map<String, Object>> getTrackByDeliveryId(@RequestParam("deliveryId") String deliveryId);

    @GetMapping(value = "/track/info/order")
    Result<Map<String, Object>> getTrackByOrderId(@RequestParam("orderId") String orderId);

    /**
     * 优惠券
     */
    @PostMapping(value = "/usercoupon/packs/coupon", consumes = Contacts.CONSUMES)
    Result<?> receivedPacksCoupon(@RequestBody List<UserCoupon> data);

    @GetMapping(value = "/usercoupon/{couponId}")
    Result<UserCoupon> getCouponInfo(@PathVariable("couponId") String couponId);

    @PostMapping(value = "/usercoupon/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<UserCoupon>> pageCoupon(@RequestBody PageParam<UserCoupon> couponPage);

    @GetMapping(value = "/usercoupon/addByUser")
    Result sendCoupon(@RequestParam("userId") String userId,
                      @RequestParam("templateId") String templateId);

    @GetMapping(value = "/usercoupon/addByRole")
    Result sendCouponByRole(@RequestParam("roleId") String roleId,
                            @RequestParam("templateId") String templateId);

    @GetMapping(value = "/usercoupon/list/{userId}")
    Result<List<CouponBase>> listCouponByUser(@PathVariable("userId") String userId, @RequestParam("type") Integer type);

    /**
     * 用户兑换券
     */
    @PostMapping(value = "/userTicket/give/res", consumes = Contacts.CONSUMES)
    Result giveUserTicket(@RequestBody GiveParam giveParam);

    /**
     * 菜品券
     */
    @GetMapping(value = "/vouchers/map")
    Result<Map<String, UserVoucher>> mapVoucherByIds(@RequestParam(value = "vouchers") List<String> vouchers);

    @GetMapping(value = "/vouchers/send")
    Result sendGoodsVoucher(@RequestParam("userId") String userId,
                            @RequestParam("voucherId") String voucherId);

    @GetMapping(value = "/vouchers/list/{userId}")
    Result<List<VoucherBase>> listVoucherByUser(@PathVariable("userId") String userId);

//    @GetMapping(value = "/vouchers/send")
//    Result sendGoodsVoucher(@RequestParam("userId") String userId, @RequestParam("voucherId") String voucherId);

    /* 推送模板 */
    @GetMapping(value = "/push/{markId}")
    Result<PushInfo> queryPushInfoById(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/push/template/list", consumes = Contacts.CONSUMES)
    Result<PageGrid<PushTemplate>> queryPushTemplateList(@RequestBody PageParam<PushTemplate> param);

    @PostMapping(value = "/push/info/list")
    Result<PageGrid<PushInfo>> queryPushInfoList(@RequestBody PageParam<PushInfo> param);

    @GetMapping(value = "/push/info/{templateId}")
    Result<List<PushInfo>> queryPushInfoByTemplateId(@PathVariable("templateId") @NotBlank String templateId);

    @GetMapping(value = "/push/use/{markId}")
    Result usePushInfo(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/push/template/add")
    Result addPushTemplate(@RequestBody PushInfoVo pushInfoVo);

    @PatchMapping(value = "/push/template/modify")
    Result modifyPushTemplate(@RequestBody PushTemplate pushTemplate);

    @PostMapping(value = "/push/info/add")
    Result addPushInfo(@RequestBody PushInfoVo infoVo);

    @PatchMapping(value = "/push/info/modify")
    Result modifyPushInfo(@RequestBody PushInfoVo infoVo);

    @DeleteMapping(value = "/push/template/{markId}")
    Result deletePushTemplateById(@PathVariable("markId") @NotBlank String markId);

    @DeleteMapping(value = "/push/info/{markId}")
    Result deletePushInfoById(@PathVariable("markId") @NotBlank String markId);

    @GetMapping(value = "/push/type/list")
    Result<List<PushType>> queryPushTypeList();

    @PostMapping(value = "/push/type/page")
    Result<PageGrid<PushType>> queryPushTypeByPage(@RequestBody PageParam<PushType> param);

    @PostMapping(value = "/push/type/add")
    Result addPushType(@RequestBody PushType pushType);

    @DeleteMapping(value = "/push/type/{markId}")
    Result deletePushTypeById(@PathVariable("markId") String markId);

    @GetMapping(value = "/push/template")
    Result<PushInfo> queryPushTemplate(@RequestParam("modelId") @NotBlank String modelId,
                                       @RequestParam(value = "typeId", required = false) String typeId);
}
