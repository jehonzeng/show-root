package com.szhengzhu.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.application.WechatConfig;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;
import com.szhengzhu.util.WechatUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import weixin.popular.bean.paymch.UnifiedorderResult;

@Api(tags = { "订单操作：OrderController" })
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
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "用户下单", notes = "用户下单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<?> createOrder(HttpServletRequest request, @RequestBody OrderModel orderModel) {
        if (orderModel == null || orderModel.getItem().size() == 0 || StringUtils.isEmpty(orderModel.getAddressId()))
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderModel.setUserId(userId);
            return showOrderClient.createOrder(orderModel);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "微信内统一生成微信普通订单", notes = "微信内统一生成微信普通订单")
    @RequestMapping(value = "/wechat/unifiedorder", method = RequestMethod.POST)
    public Result<?> orderPay(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("orderNo") String orderNo) {
        String token = request.getHeader("Show-Token");
        String openId = null;
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess())
            openId = userResult.getData().getWopenId();
        if (StringUtils.isEmpty(openId))
            return new Result<>(StatusCode._4012);
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        if (orderResult.getCode().equals("200")) {
            OrderInfo orderInfo = orderResult.getData();
            if (orderInfo == null)
                return new Result<>(StatusCode._4014);
            if (!orderInfo.getOrderStatus().equals("OT01"))
                return new Result<>(StatusCode._5006);
            if (TimeUtils.today().getTime() - orderInfo.getOrderTime().getTime() > 15 * 60 * 1000)
                return new Result<>(StatusCode._5014);
            return unifiedOrder(orderNo, request.getRemoteAddr(), openId,
                    orderInfo.getPayAmount().multiply(new BigDecimal(100)).toBigInteger().toString());
        }
        return orderResult;
    }
    
    @ApiOperation(value = "H5统一生成微信普通订单", notes = "H5统一生成微信普通订单")
    @RequestMapping(value = "/h5/unifiedorder", method = RequestMethod.POST)
    public Result<?> orderPayWeb(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("orderNo") String orderNo) {
        String token = request.getHeader("Show-Token");
        String openId = null;
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess())
            openId = userResult.getData().getWopenId();
        if (StringUtils.isEmpty(openId))
            return new Result<>(StatusCode._4012);
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        if (orderResult.getCode().equals("200")) {
            OrderInfo orderInfo = orderResult.getData();
            if (orderInfo == null)
                return new Result<>(StatusCode._4014);
            if (!orderInfo.getOrderStatus().equals("OT01"))
                return new Result<>(StatusCode._5006);
            if (TimeUtils.today().getTime() - orderInfo.getOrderTime().getTime() > 15 * 60 * 1000)
                return new Result<>(StatusCode._5014);
            return unifiedOrder(orderNo, request.getRemoteAddr(),
                    orderInfo.getPayAmount().multiply(new BigDecimal(100)).toBigInteger().toString());
        }
        return orderResult;
    } 

    @ApiOperation(value = "微信内统一生成微信团购订单", notes = "微信内统一生成微信团购订单")
    @RequestMapping(value = "/wechat/teambuy/unifiedorder", method = RequestMethod.POST)
    public Result<?> teambuyOrder(HttpServletRequest request, HttpServletResponse response,
            @RequestBody TeambuyModel model) {
        String token = request.getHeader("Show-Token");
        String openId = null;
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess())
            openId = userResult.getData().getWopenId();
        if (StringUtils.isEmpty(openId))
            return new Result<>(StatusCode._4012);
        Result<TeambuyOrder> itemResult = showOrderClient.addTeambuyOrder(model);
        if (itemResult.getCode().equals("200")) {
            TeambuyOrder item = itemResult.getData();
            String price = item.getPayAmount().multiply(new BigDecimal(100)).toString();
            return unifiedOrder(item.getOrderNo(), request.getRemoteAddr(), openId, price);
        }
        return itemResult;
    }

    @ApiOperation(value = "统一生成微信秒杀订单", notes = "统一生成微信秒杀订单")
    @RequestMapping(value = "/wechat/seckill/unifiedorder", method = RequestMethod.POST)
    public Result<?> seckillOrder(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SeckillModel model) {
        String token = request.getHeader("Show-Token");
        String openId = null;
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess())
            openId = userResult.getData().getWopenId();
        if (StringUtils.isEmpty(openId))
            return new Result<>(StatusCode._4012);
        Result<SeckillOrder> orderResult = showOrderClient.addSeckillOrder(model);
        if (orderResult.getCode().equals("200")) {
            SeckillOrder order = orderResult.getData();
            String price = order.getPayAmount().multiply(new BigDecimal(100)).toString();
            return unifiedOrder(order.getOrderNo(), request.getRemoteAddr(), openId, price);
        }
        return orderResult;
    }

    /**
     * 微信内触发下单到微信
     * 
     * @date 2019年5月22日 下午5:05:01
     * @param orderNo
     * @param remoteAddr
     * @param wopenId
     * @param price
     * @return
     */
    private Result<?> unifiedOrder(String orderNo, String remoteAddr, String wopenId, String price) {
        UnifiedorderResult unifiedResult = WechatUtils.unifiedOrder(wechatConfig, orderNo, remoteAddr, wopenId, price);
        if (!StringUtils.isEmpty(unifiedResult.getResult_code()) && unifiedResult.getResult_code().equals("SUCCESS")) {
            SortedMap<String, String> auth = WechatUtils.buildValidationInfo(wechatConfig,
                    unifiedResult.getPrepay_id());
            return new Result<>(auth);
        } else {
            return new Result<>(unifiedResult.getResult_code(), unifiedResult.getReturn_msg());
        }
    }
    
    /**
     * H5统一下单到微信
     * 
     * @date 2019年7月24日 上午11:58:28
     * @param orderNo
     * @param remoteAddr
     * @param price
     * @return
     */
    private Result<?> unifiedOrder(String orderNo, String remoteAddr, String price) {
        UnifiedorderResult unifiedResult = WechatUtils.unifiedOrder(wechatConfig, orderNo, remoteAddr, price);
        if (!StringUtils.isEmpty(unifiedResult.getResult_code()) && unifiedResult.getResult_code().equals("SUCCESS")) {
            String mwebUrl = WechatUtils.webPayRedirect(unifiedResult.getMweb_url());
            return new Result<>(mwebUrl);
        } else {
            return new Result<>(unifiedResult.getResult_code(), unifiedResult.getReturn_msg());
        }
    }

    @ApiOperation(value = "获取全部订单", notes = "获取全部订单")
    @RequestMapping(value = "/all/list", method = RequestMethod.POST)
    public Result<?> all(HttpServletRequest request, PageParam<String> orderPage) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderPage.setData(userId);
            return showOrderClient.listAll(orderPage);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "待支付订单列表", notes = "待支付订单列表")
    @RequestMapping(value = "/unpaid/list", method = RequestMethod.POST)
    public Result<?> unpaid(HttpServletRequest request, PageParam<String> orderPage) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderPage.setData(userId);
            return showOrderClient.listUnpaid(orderPage);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取待成团订单列表", notes = "获取待成团订单列表")
    @RequestMapping(value = "/ungroup/list", method = RequestMethod.POST)
    public Result<?> ungroup(HttpServletRequest request, PageParam<String> orderPage) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderPage.setData(userId);
            return showOrderClient.listUngroup(orderPage);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取待发货订单列表", notes = "获取待发货订单列表")
    @RequestMapping(value = "/undelivery/list", method = RequestMethod.POST)
    public Result<?> undelivery(HttpServletRequest request, PageParam<String> orderPage) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderPage.setData(userId);
            return showOrderClient.listUndelivery(orderPage);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取待收货订单列表", notes = "获取待收货订单列表")
    @RequestMapping(value = "/unreceive/list", method = RequestMethod.POST)
    public Result<?> unreceive(HttpServletRequest request, PageParam<String> orderPage) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderPage.setData(userId);
            return showOrderClient.listUnReceive(orderPage);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取待评价订单列表", notes = "获取待评价订单列表")
    @RequestMapping(value = "/unjudge/list", method = RequestMethod.POST)
    public Result<?> unjudge(HttpServletRequest request, PageParam<String> orderPage) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            orderPage.setData(userId);
            return showOrderClient.listUnjudge(orderPage);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取订单详情", notes = "获取订单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Result<?> getOrderDetail(@RequestParam("orderNo") String orderNo) {
        if (StringUtils.isEmpty(orderNo))
            return new Result<>(StatusCode._4004);
        return showOrderClient.getOrderDetail(orderNo);
    }

    @ApiOperation(value = "获取需要评价的商品列表", notes = "获取需要评价的商品列表")
    @RequestMapping(value = "/item/judge/{orderId}", method = RequestMethod.GET)
    public Result<List<Judge>> listItemJudge(@PathVariable("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId))
            return new Result<>(StatusCode._4004);
        return showOrderClient.listOrderItemJudge(orderId);
    }
}
