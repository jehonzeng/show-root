package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.ordering.PayBack;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.redis.RedisKey;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.bean.paymch.MchBaseResult;
import weixin.popular.util.XMLConverUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/back")
public class BackController {

    @Resource
    private Redis redis;

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "微信支付后回滚")
    @RequestMapping(value = "/xpay", method = {RequestMethod.POST, RequestMethod.GET})
    public void wechatPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateSignature(request, response, true);
    }

    @ApiOperation(value = "会员微信充值后回滚")
    @RequestMapping(value = "/member/pay", method = {RequestMethod.POST, RequestMethod.GET})
    public void recharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateSignature(request, response, false);
    }

    /**
     * 校验数据
     *
     * @param request
     * @param response
     * @param flag true: 微信支付  false：会员充值
     * @throws IOException
     */
    private void validateSignature(HttpServletRequest request, HttpServletResponse response, boolean flag) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder xml = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            xml.append(line + "\n");
        }
        Map<String, String> payNotify = XMLConverUtil.convertToMap(xml.toString());
        String transactionId = payNotify.get("transaction_id");
        String outTradeNo = payNotify.get("out_trade_no");
        log.info("{}回滚：{}", Boolean.TRUE.equals(flag) ? "微信支付" : "会员充值", outTradeNo);
        if (StrUtil.isEmpty(outTradeNo)) {
            return;
        }
        // 已处理 去重
        String payBackKey = "xwechat:pay:back:" + transactionId;
        Object obi = redis.get(payBackKey);
        if (!ObjectUtil.isNull(obi)) {
            return;
        }
        if (WechatUtils.validateSignature(payNotify, wechatConfig.getKey())) {
            successResponse(response, outTradeNo, payBackKey, flag);
        } else {
            failResponse(response, outTradeNo, flag);
        }
    }

    /**
     * 回调成功
     * 微信支付、会员充值成功回调记录
     *
     * @param response
     * @param outTradeNo
     * @param payBackKey
     * @param flag
     * @throws IOException
     */
    private void successResponse(HttpServletResponse response, String outTradeNo, String payBackKey, boolean flag) throws IOException {
        redis.set(payBackKey, 1, 60);
        if (Boolean.TRUE.equals(flag)) {
            callBack(outTradeNo, 1);
        } else {
            showMemberClient.modifyPayBack(outTradeNo);
        }
        MchBaseResult baseResult = new MchBaseResult();
        baseResult.setReturn_code("SUCCESS");
        baseResult.setReturn_msg("OK");
        response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        log.info("{}回调成功并通知微信：{}", Boolean.TRUE.equals(flag) ? "微信支付" : "会员充值", outTradeNo);
    }

    /**
     * 回调失败
     * 微信支付回调失败并记录
     *
     * @param response
     * @param outTradeNo
     * @param flag
     * @throws IOException
     */
    private void failResponse(HttpServletResponse response, String outTradeNo, boolean flag) throws IOException {
        if (Boolean.TRUE.equals(flag)) {
            callBack(outTradeNo, -1);
        }
        MchBaseResult baseResult = new MchBaseResult();
        baseResult.setReturn_code("FAIL");
        baseResult.setReturn_msg("ERROR");
        response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        log.info("{}回调失败并通知微信：{}", Boolean.TRUE.equals(flag) ? "微信支付" : "会员充值", outTradeNo);
    }

    /**
     * 记录微信支付回调
     *
     * @param indentId
     * @param backType
     */
    private void callBack(String indentId, Integer backType) {
        String payKey = RedisKey.X_INDENT_PAY + indentId;
        Object data = redis.get(payKey);
        PayBack back = new PayBack();
        back.setIndentId(indentId);
        back.setBackType(backType);
        back.setBackInfo(backType == 1 ? "SUCCESS" : "ERROR");
        back.setUserId((String) data);
        showOrderingClient.addPayBack(back);
        showOrderingClient.wechatBack(indentId);
    }
}
