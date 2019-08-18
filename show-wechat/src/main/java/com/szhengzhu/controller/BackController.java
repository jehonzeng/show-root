package com.szhengzhu.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.application.WechatConfig;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.Result;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.ExpireSet;
import com.szhengzhu.util.WechatUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.paymch.MchBaseResult;
import weixin.popular.util.XMLConverUtil;

@Api(tags = { "微信回调：BackController" })
@RestController
@RequestMapping("/back")
public class BackController {

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private Sender sender;

    // 重复通知过滤
    private static ExpireSet<String> expireSet = new ExpireSet<String>(60);

    @ApiOperation(value = "微信校验处理接口", notes = "微信校验处理接口")
    @RequestMapping(value = "/signature", method = RequestMethod.GET)
    public void signature(@RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr, HttpServletResponse response)
            throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(echostr);
    }

    @ApiOperation(value = "微信支付后回滚", notes = "微信支付之后回滚")
    @RequestMapping(value = "/wechatPay", method = { RequestMethod.POST, RequestMethod.GET })
    public void wechatPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));// 获取请求数据
        StringBuilder xml = new StringBuilder();
        String line = null;
        while ((line = in.readLine()) != null) {
            xml.append(line + "\n");
        }
        Map<String, String> payNotify = XMLConverUtil.convertToMap(xml.toString());
        String outTradeNo = payNotify.get("out_trade_no");
        String transactionId = payNotify.get("transaction_id");
        if (expireSet.contains(transactionId))// 已处理 去重4
            return;
        int type = Character.getNumericValue(outTradeNo.charAt(0));
        if (type == 1)
            orderBack(response, outTradeNo, transactionId, payNotify);
        else if (type == 2)
            teambuyOrderBack(response, outTradeNo, transactionId, payNotify);
        else if (type == 3)
            seckillOrderBack(response, outTradeNo, transactionId, payNotify);
    }

    public void orderBack(HttpServletResponse response, String orderNo, String transactionId,
            Map<String, String> payNotify) throws Exception {
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        if (orderResult.isSuccess()) {
            OrderInfo order = orderResult.getData();
            String userId = order.getUserId();
            validateSignature(response, payNotify, transactionId, orderNo, userId);
            // 记录回调信息
            System.out.println("订单已回滚");
        }
    }

    public void teambuyOrderBack(HttpServletResponse response, String orderNo, String transactionId,
            Map<String, String> payNotify) throws Exception {
        Result<TeambuyOrder> orderResult = showOrderClient.getTeambuyOrderByNo(orderNo);
        if (orderResult.isSuccess()) {
            TeambuyOrder order = orderResult.getData();
            String userId =  order.getUserId();
            validateSignature(response, payNotify, transactionId, orderNo, userId);
            // 记录回调信息
            System.out.println("订单已回滚");
        }
    }

    public void seckillOrderBack(HttpServletResponse response, String orderNo, String transactionId,
            Map<String, String> payNotify) throws Exception {
        Result<SeckillOrder> orderResult = showOrderClient.getSeckillOrderByNo(orderNo);
        if (orderResult.isSuccess()) {
            SeckillOrder order = orderResult.getData();
            String userId =  order.getUserId();
            validateSignature(response, payNotify, transactionId, orderNo, userId);
            // 记录回调信息
            System.out.println("订单已回滚");
        } 
    }

    public void validateSignature(HttpServletResponse response, Map<String, String> payNotify, String transactionId,
            String orderNo, String userId) throws Exception { 
        if (WechatUtils.validateSignature(payNotify, wechatConfig.getKey())) {// 签名验证
            // 回调成功 删除回调错误提示
            MchBaseResult baseResult = new MchBaseResult();
            expireSet.add(transactionId);
            baseResult.setReturn_code("SUCCESS");
            baseResult.setReturn_msg("OK");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } else {
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("FAIL");
            baseResult.setReturn_msg("ERROR");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            showOrderClient.orderErrorBack(orderNo, userId);
            System.out.println(("验证失败：" + orderNo));
        }
    }

    @ApiOperation(value = "微信事件处理接口", notes = "微信事件处理接口")
    @RequestMapping(value = "/signature", method = RequestMethod.POST)
    public void signature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getInputStream() == null) {
            response.getOutputStream().flush();
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        EventMessage eventMsg = XMLConverUtil.convertToObject(EventMessage.class, reader);
        response.getOutputStream().flush();
        String openId = eventMsg.getFromUserName();
        Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
        UserInfo user = new UserInfo();
        if (userResult.isSuccess())
            user = userResult.getData();
        else
            user.setWopenId(openId);
        if (eventMsg.getMsgType().equals("event")) {// 微信推送事件
            if (eventMsg.getEvent().equals("CLICK")) {
                baseAction(user, eventMsg.getEventKey());
            } else if (eventMsg.getEvent().equals("subscribe")) { // 微信订阅
                subscribeAction(user, eventMsg.getEventKey());
            } else if (eventMsg.getEvent().equals("unsubscribe")) { // 微信退订
//                sender.valid(user.getWopenId(), 0);
                System.out.println();
            } else if (eventMsg.getEvent().equals("SCAN")) {
                subscribeAction(user, Commons.QR_PRE + eventMsg.getEventKey());
            }
        } else if (eventMsg.getMsgType().equals("text")) {
            replyAction(user, eventMsg.getContent());
        }
    }

    private void baseAction(UserInfo user, String code) {

    }

    private void subscribeAction(UserInfo user, String qr_code) {

    }

    private void replyAction(UserInfo user, String content) {

    }
}
