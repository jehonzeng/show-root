package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.order.OrderRecord;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.context.OrderContext;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.util.UserUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weixin.popular.bean.paymch.UnifiedorderResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author Jehon Zeng
 */
@Validated
@Api(tags = {"订单专题：OrderController"})
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private OrderContext orderContext;

    @ApiOperation(value = "用户下单", notes = "用户下单")
    @PostMapping(value = "/create")
    public Result<Map<String, Object>> createOrder(HttpServletRequest request, @RequestBody @Validated OrderModel orderModel) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderModel.setUserId(userToken.getUserId());
        if (StrUtil.isNotEmpty(orderModel.getOrderSource()) && orderModel.getOrderSource().length() > 4) {
            orderModel.setOrderSource(null);
        }
        return showOrderClient.createOrder(orderModel);
    }

    @ApiOperation(value = "用户秒杀下单", notes = "用户秒杀下单")
    @PostMapping(value = "/create/seckill")
    public Result<SeckillOrder> createSeckillOrder(HttpServletRequest request, @RequestBody @Validated SeckillModel model) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        model.setUserId(userToken.getUserId());
        if (StrUtil.isNotEmpty(model.getOrderSource()) && model.getOrderSource().length() > 4) {
            model.setOrderSource(null);
        }
        return showOrderClient.createSeckillOrder(model);
    }

    @ApiOperation(value = "用户团购下单", notes = "用户团购下单")
    @PostMapping(value = "/create/teambuy")
    public Result createTeambuyOrder(HttpServletRequest request, @RequestBody @Validated TeambuyModel model) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        model.setUserId(userToken.getUserId());
        if (StrUtil.isNotEmpty(model.getOrderSource()) && model.getOrderSource().length() > 4) {
            model.setOrderSource(null);
        }
        return showOrderClient.createTeambuyOrder(model);
    }

    @ApiOperation(value = "微信内统一生成微信普通订单", notes = "微信内统一生成微信普通订单")
    @PostMapping(value = "/wechat/unifiedorder")
    public Result orderPay(HttpServletRequest request,
                              @RequestParam("orderNo") @NotBlank String orderNo) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String openId = userInfo.getWopenId();
        return orderPay(request, orderNo, openId);
    }

    @ApiOperation(value = "web统一生成微信普通订单", notes = "web统一生成微信普通订单")
    @PostMapping(value = "/web/unifiedorder")
    public Result orderPayWeb(HttpServletRequest request, @RequestParam("orderNo") @NotBlank String orderNo) {
        return orderPay(request, orderNo, null);
    }

    /**
     * 统一下单到微信
     *
     * @param request
     * @param orderNo
     * @param openId
     * @return
     */
    private Result orderPay(HttpServletRequest request, String orderNo, String openId) {
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        OrderBase orderBase = handler.getOrderBase(orderNo);
        long timeDiff = DateUtil.date().getTime() - orderBase.getOrderTime().getTime();
        ShowAssert.checkTrue(timeDiff > Contacts.ORDER_EXPIRED_TIME, StatusCode._5014);
        ShowAssert.checkTrue(!OrderStatus.NO_PAY.equals(orderBase.getOrderStatus()), StatusCode._5006);
        if (StrUtil.isNotEmpty(openId)) {
            return unifiedOrder(orderNo, request.getRemoteAddr(), openId, NumberUtil.toStr(NumberUtil.mul(orderBase.getPayAmount(), 100)));
        }
        return unifiedOrder(orderBase.getOrderNo(), request.getRemoteAddr(),
                NumberUtil.toStr(NumberUtil.mul(orderBase.getPayAmount(), 100)));
    }

    /**
     * 微信内触发下单到微信
     *
     * @param orderNo
     * @param remoteAddr
     * @param wopenId
     * @param price
     * @return
     * @date 2019年5月22日 下午5:05:01
     */
    private Result unifiedOrder(String orderNo, String remoteAddr, String wopenId, String price) {
        UnifiedorderResult unifiedResult = WechatUtils.unifiedOrder(wechatConfig, orderNo, remoteAddr, wopenId, price);
        // 构建验证签名信息
        SortedMap<String, String> validationInfo = WechatUtils.buildValidationInfo(wechatConfig,
                unifiedResult.getPrepay_id());
        Result<SortedMap<String, String>> result = new Result<>();
        validationInfo.put("errCode", unifiedResult.getErr_code());
        validationInfo.put("errDes", unifiedResult.getErr_code_des());
        if (StrUtil.isEmpty(unifiedResult.getResult_code()) || !"SUCCESS".equals(unifiedResult.getResult_code())) {
            result = new Result<>(StatusCode._5030);
        }
        result.setData(validationInfo);
        return result;
    }

    /**
     * web统一下单到微信
     *
     * @param orderNo
     * @param remoteAddr
     * @param price
     * @return
     * @date 2019年7月24日 上午11:58:28
     */
    private Result unifiedOrder(String orderNo, String remoteAddr, String price) {
        UnifiedorderResult unifiedResult = WechatUtils.unifiedOrder(wechatConfig, orderNo, remoteAddr, price);
        Result<String> result = new Result<>();
        String mwebUrl = unifiedResult.getMweb_url();
        if (StrUtil.isEmpty(unifiedResult.getResult_code()) || !"SUCCESS".equals(unifiedResult.getResult_code())) {
            result = new Result<>(StatusCode._5030);
        }
        result.setData(mwebUrl);
        return result;
    }

    @ApiOperation(value = "获取全部订单", notes = "获取全部订单")
    @PostMapping(value = "/all/list")
    public Result all(HttpServletRequest request, @RequestBody PageParam<String> orderPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderPage.setData(userToken.getUserId());
        return showOrderClient.listAll(orderPage);
    }

    @ApiOperation(value = "待支付订单列表", notes = "待支付订单列表")
    @PostMapping(value = "/unpaid/list")
    public Result unpaid(HttpServletRequest request, @RequestBody PageParam<String> orderPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderPage.setData(userToken.getUserId());
        return showOrderClient.listUnpaid(orderPage);
    }

    @ApiOperation(value = "获取待成团订单列表", notes = "获取待成团订单列表")
    @PostMapping(value = "/ungroup/list")
    public Result ungroup(HttpServletRequest request, @RequestBody PageParam<String> orderPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderPage.setData(userToken.getUserId());
        return showOrderClient.listUngroup(orderPage);
    }

    @ApiOperation(value = "获取待发货订单列表", notes = "获取待发货订单列表")
    @PostMapping(value = "/undelivery/list")
    public Result undelivery(HttpServletRequest request, @RequestBody PageParam<String> orderPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderPage.setData(userToken.getUserId());
        return showOrderClient.listUndelivery(orderPage);
    }

    @ApiOperation(value = "获取待收货订单列表", notes = "获取待收货订单列表")
    @PostMapping(value = "/unreceive/list")
    public Result unreceive(HttpServletRequest request, @RequestBody PageParam<String> orderPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderPage.setData(userToken.getUserId());
        return showOrderClient.listUnReceive(orderPage);
    }

    @ApiOperation(value = "获取待评价订单列表", notes = "获取待评价订单列表")
    @PostMapping(value = "/unjudge/list")
    public Result unjudge(HttpServletRequest request, @RequestBody PageParam<String> orderPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        orderPage.setData(userToken.getUserId());
        return showOrderClient.listUnjudge(orderPage);
    }

    @ApiOperation(value = "获取订单详情", notes = "获取订单详情")
    @GetMapping(value = "/detail")
    public Result getOrderDetail(HttpServletRequest request, @RequestParam("orderNo") @NotBlank String orderNo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.getOrderDetail(orderNo, userToken.getUserId());
    }

    @ApiOperation(value = "获取需要评价的商品列表", notes = "获取需要评价的商品列表")
    @GetMapping(value = "/item/judge/{orderNo}")
    public Result<List<Judge>> listItemJudge(HttpServletRequest request, @PathVariable("orderNo") @NotBlank String orderNo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.listOrderItemJudge(orderNo, userToken.getUserId());
    }

    @ApiOperation(value = "用户申请退款", notes = "用户申请退款")
    @PatchMapping(value = "/reqRefund")
    public Result reqRefund(HttpServletRequest request, @RequestParam("orderNo") @NotBlank String orderNo,
                               @RequestParam(value = "reason", required = false) String reason) {
        if (!StrUtil.isEmpty(reason)) {
            OrderRecord orderRecord = OrderRecord.builder().orderNo(orderNo).reason(reason).build();
            showOrderClient.addOrderRecord(orderRecord);
        }
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.modifyStatusByNo(orderNo, OrderStatus.REFUNDING, userToken.getUserId());
    }

    @ApiOperation(value = "用户取消申请退款", notes = "用户取消申请退款")
    @PatchMapping(value = "/reqRefund/cancel")
    public Result cancelRefund(HttpServletRequest request, @RequestParam("orderNo") @NotBlank String orderNo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.modifyStatusByNo(orderNo, OrderStatus.PAID, userToken.getUserId());
    }

    @ApiOperation(value = "用户取消订单", notes = "用户取消订单")
    @PatchMapping(value = "/cancel")
    public Result cancelOrder(HttpServletRequest request, @RequestParam("orderNo") @NotBlank String orderNo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.modifyStatusByNo(orderNo, OrderStatus.CANCELLED, userToken.getUserId());
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货")
    @PatchMapping(value = "/receive")
    public Result receiveOrder(HttpServletRequest request, @RequestParam("orderNo") @NotBlank String orderNo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.modifyStatusByNo(orderNo, OrderStatus.ARRIVED, userToken.getUserId());
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单")
    @DeleteMapping(value = "/delete/{orderNo}")
    public Result deleteOrder(HttpServletRequest request, @PathVariable("orderNo") @NotBlank String orderNo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder handler = orderContext.getInstance(orderType);
        return handler.modifyStatusByNo(orderNo, OrderStatus.WRONG, userToken.getUserId());
    }

    @ApiOperation(value = "通过订单id实时获取物流信息", notes = "通过订单id实时获取物流信息")
    @GetMapping(value = "/track/info")
    public Result<Map<String, Object>> getTrackByOrderId(@RequestParam("orderId") @NotBlank String orderId) {
        return showOrderClient.getTrackByOrderId(orderId);
    }

    @ApiOperation(value = "获取各状态订单数量", notes = "获取各状态订单数量")
    @GetMapping(value = "/status/count")
    public Result<List<Map<String, Object>>> getStatusCount(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showOrderClient.getStatusCount(userToken.getUserId());
    }
}
