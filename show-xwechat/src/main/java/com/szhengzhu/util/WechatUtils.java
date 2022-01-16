package com.szhengzhu.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.qq.weixin.mp.aes.PKCS7Encoder;
import com.szhengzhu.bean.WxaDPhone;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.config.FtpConfig;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.SecapiPayRefund;
import weixin.popular.bean.paymch.SecapiPayRefundResult;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.JsonUtil;
import weixin.popular.util.WxaUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Jehon Zeng
 */
@Slf4j
public class WechatUtils extends WxaUtil {

    /**
     * 下单到微信
     *
     * @param indentId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @param flag true:微信支付  false：会员充值
     * @return
     */
//    public static Result<SortedMap<String, String>> wechatAppletPay(WechatConfig wechatConfig, String indentId, String ip, String xopenId, BigDecimal payPrice, boolean flag) {
//        UnifiedorderResult un;
//        if (Boolean.TRUE.equals(flag)) {
//            un = wechatAppletPayOrder(wechatConfig, indentId, ip, xopenId, NumberUtil.toStr(NumberUtil.mul(payPrice, 100)));
//        } else {
//            un = wechatMemberRecharge(wechatConfig, indentId, ip, xopenId, NumberUtil.toStr(NumberUtil.mul(payPrice, 100)));
//        }
//        if (ObjectUtil.isNull(un)) {
//            return new Result<>();
//        }
//        // 构建验证签名信息
//        SortedMap<String, String> validationInfo = WechatUtils.buildValidationInfo(wechatConfig, un.getPrepay_id());
//        Result<SortedMap<String, String>> result = new Result<>();
//        validationInfo.put("errCode", un.getErr_code());
//        validationInfo.put("errDes", un.getErr_code_des());
//        if (StrUtil.isEmpty(un.getResult_code()) || !"SUCCESS".equals(un.getResult_code())) {
//            result = new Result<>(StatusCode._5030);
//        }
//        result.setData(validationInfo);
//        return result;
//    }

    /**
     * 微信小程序下单支付
     *
     * @param wechatConfig
     * @param indentId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @return
     */
    public static Result<SortedMap<String, String>> wechatPay(WechatConfig wechatConfig, String indentId, String ip, String xopenId, BigDecimal payPrice) {
        UnifiedorderResult un = createPayOrder(wechatConfig, indentId, ip, xopenId, NumberUtil.toStr(NumberUtil.mul(payPrice, 100)), "露几手点餐订单", wechatConfig.getWechatPayBack());
        // 构建验证签名信息
        SortedMap<String, String> validationInfo = WechatUtils.buildValidationInfo(wechatConfig, un.getPrepay_id());
        Result<SortedMap<String, String>> result = new Result<>();
        validationInfo.put("errCode", un.getErr_code());
        validationInfo.put("errDes", un.getErr_code_des());
        if (StrUtil.isEmpty(un.getResult_code()) || !"SUCCESS".equals(un.getResult_code())) {
            result = new Result<>(StatusCode._5030);
        }
        result.setData(validationInfo);
        return result;
    }

    /**
     * 会员充值
     *
     * @param wechatConfig
     * @param detailId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @return
     */
    public static Result<SortedMap<String, String>> memberPay(WechatConfig wechatConfig, String detailId, String ip, String xopenId, BigDecimal payPrice) {
        UnifiedorderResult un = createPayOrder(wechatConfig, detailId, ip, xopenId, NumberUtil.toStr(NumberUtil.mul(payPrice, 100)), "露几手会员充值", wechatConfig.getMemberPayBack());
        // 构建验证签名信息
        SortedMap<String, String> validationInfo = WechatUtils.buildValidationInfo(wechatConfig, un.getPrepay_id());
        Result<SortedMap<String, String>> result = new Result<>();
        validationInfo.put("errCode", un.getErr_code());
        validationInfo.put("errDes", un.getErr_code_des());
        if (StrUtil.isEmpty(un.getResult_code()) || !"SUCCESS".equals(un.getResult_code())) {
            result = new Result<>(StatusCode._5030);
        }
        result.setData(validationInfo);
        return result;
    }

    /**
     * 竞赛支付
     *
     * @param wechatConfig
     * @param payId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @return
     */
    public static Result<SortedMap<String, String>> matchPay(WechatConfig wechatConfig, String payId, String ip, String xopenId, BigDecimal payPrice) {
        UnifiedorderResult un = createPayOrder(wechatConfig, payId, ip, xopenId, NumberUtil.toStr(NumberUtil.mul(payPrice, 100)), "露几手竞赛活动支付订单", wechatConfig.getMemberPayBack());
        // 构建验证签名信息
        SortedMap<String, String> validationInfo = WechatUtils.buildValidationInfo(wechatConfig, un.getPrepay_id());
        Result<SortedMap<String, String>> result = new Result<>();
        validationInfo.put("errCode", un.getErr_code());
        validationInfo.put("errDes", un.getErr_code_des());
        if (StrUtil.isEmpty(un.getResult_code()) || !"SUCCESS".equals(un.getResult_code())) {
            result = new Result<>(StatusCode._5030);
        }
        result.setData(validationInfo);
        return result;
    }

    /**
     * 购买会员套餐支付
     *
     * @param wechatConfig
     * @param templateId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @return
     */
    public static Result<SortedMap<String, String>> comboPay(WechatConfig wechatConfig, String templateId, String ip, String xopenId, BigDecimal payPrice) {
        UnifiedorderResult un = createPayOrder(wechatConfig, templateId, ip, xopenId, NumberUtil.toStr(NumberUtil.mul(payPrice, 100)), "露几手会员套餐支付订单", wechatConfig.getMemberPayBack());
        // 构建验证签名信息
        SortedMap<String, String> validationInfo = WechatUtils.buildValidationInfo(wechatConfig, un.getPrepay_id());
        Result<SortedMap<String, String>> result = new Result<>();
        validationInfo.put("errCode", un.getErr_code());
        validationInfo.put("errDes", un.getErr_code_des());
        if (StrUtil.isEmpty(un.getResult_code()) || !"SUCCESS".equals(un.getResult_code())) {
            result = new Result<>(StatusCode._5030);
        }
        result.setData(validationInfo);
        return result;
    }

    /**
     * 上传微信用户头像到本系统
     *
     * @param url
     * @return
     * @date 2019年5月10日 下午4:24:19
     */
    public static ImageInfo downLoadImage(FtpConfig ftpServer, String url) {
        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.initApacheHttpClient();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        try {
            String id = snowflake.nextIdStr();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String path = "/user/" + sdf.format(new Date()) + "/";
            String fileName = id + ".png";
            imageDownloader.fetchContent(url, path, fileName, ftpServer);
            ImageInfo image = new ImageInfo();
            image.setMarkId(id);
            image.setFileType("png");
            image.setImagePath(path + fileName);
            return image;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            imageDownloader.destroyApacheHttpClient();
        }
        return null;
    }

    /**
     * 微信小程序统一下单
     *
     * @param wechatConfig
     * @param payId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @return
     */
//    public static UnifiedorderResult wechatAppletPayOrder(WechatConfig wechatConfig, String indentId,
//                                                          String ip, String xopenId, String payPrice) {
//        Unifiedorder unifiedorder = new Unifiedorder();
//        unifiedorder.setAppid(wechatConfig.getAppId());
//        unifiedorder.setMch_id(wechatConfig.getMachId());
//        unifiedorder.setNonce_str(wechatConfig.getNonceStr());
//        unifiedorder.setBody("露几手点餐订单");
//        unifiedorder.setOut_trade_no(indentId);
//        unifiedorder.setTotal_fee(payPrice);
//        unifiedorder.setSpbill_create_ip(ip);
//        unifiedorder.setNotify_url(wechatConfig.getWechatServer() + wechatConfig.getWechatPayBack());
//        unifiedorder.setTrade_type("JSAPI");
//        unifiedorder.setOpenid(xopenId);
//        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
//        return un;
//    }

    public static UnifiedorderResult createPayOrder(WechatConfig wechatConfig, String payId,
                                                          String ip, String xopenId, String payPrice, String body, String payBackPath) {
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(wechatConfig.getAppId());
        unifiedorder.setMch_id(wechatConfig.getMachId());
        unifiedorder.setNonce_str(wechatConfig.getNonceStr());
        unifiedorder.setBody(body);
        unifiedorder.setOut_trade_no(payId);
        unifiedorder.setTotal_fee(payPrice);
        unifiedorder.setSpbill_create_ip(ip);
        unifiedorder.setNotify_url(wechatConfig.getWechatServer() + payBackPath);
        unifiedorder.setTrade_type("JSAPI");
        unifiedorder.setOpenid(xopenId);
        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
        return un;
    }

    /**
     * 会员充值
     *
     * @param wechatConfig
     * @param detailId
     * @param ip
     * @param xopenId
     * @param payPrice
     * @return
     */
//    public static UnifiedorderResult wechatMemberRecharge(WechatConfig wechatConfig, String detailId,
//                                                          String ip, String xopenId, String payPrice) {
//        Unifiedorder unifiedorder = new Unifiedorder();
//        unifiedorder.setAppid(wechatConfig.getAppId());
//        unifiedorder.setMch_id(wechatConfig.getMachId());
//        unifiedorder.setNonce_str(wechatConfig.getNonceStr());
//        unifiedorder.setBody("露几手会员充值");
//        unifiedorder.setOut_trade_no(detailId);
//        unifiedorder.setTotal_fee(payPrice);
//        unifiedorder.setSpbill_create_ip(ip);
//        unifiedorder.setNotify_url(wechatConfig.getWechatServer() + wechatConfig.getMemberPayBack());
//        unifiedorder.setTrade_type("JSAPI");
//        unifiedorder.setOpenid(xopenId);
//        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
//        return un;
//    }

    /**
     * 构建小程序验证信息
     *
     * @param wechatConfig
     * @param prepay_id
     * @return
     */
    public static SortedMap<String, String> buildValidationInfo(WechatConfig wechatConfig, String prepay_id) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<String, String> finalpackage = new TreeMap<>();
        finalpackage.put("appId", wechatConfig.getAppId());
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", wechatConfig.getNonceStr());
        finalpackage.put("package", "prepay_id=" + prepay_id);
        finalpackage.put("signType", "MD5");
        String paySign = WechatSign.createSign(finalpackage, wechatConfig.getKey());
        SortedMap<String, String> auth = new TreeMap<>();
        auth.put("paySign", paySign);
        auth.put("nonceStr", wechatConfig.getNonceStr());
        auth.put("appId", wechatConfig.getAppId());
        auth.put("timeStamp", timestamp);
        auth.put("prepay_id", prepay_id);
        return auth;
    }

    /**
     * 退款申请
     *
     * @param config
     * @param indentId  订单编号
     * @param refundNo  退款单号
     * @param totalFee  总金额
     * @param refundFee 退款金额
     * @return
     */
    public static SecapiPayRefundResult indentRefund(WechatConfig config, String indentId, String refundNo, int totalFee,
                                                     int refundFee) {
        // 綁定api安全证书
        LocalHttpClient.initMchKeyStore(config.getMachId(), Contacts.X_WECHAT_REFUND_CERT);
        SecapiPayRefund payRefund = new SecapiPayRefund();
        payRefund.setAppid(config.getAppId());
        payRefund.setMch_id(config.getMachId());
        payRefund.setOp_user_id(config.getMachId());
        payRefund.setNonce_str(config.getNonceStr());
        payRefund.setOut_trade_no(indentId);
        payRefund.setOut_refund_no(refundNo);
        payRefund.setTotal_fee(totalFee);
        payRefund.setRefund_fee(refundFee);
        payRefund.setSign_type("MD5");
        payRefund.setRefund_fee_type("CNY");
        SecapiPayRefundResult refundResult = PayMchAPI.secapiPayRefund(payRefund, config.getKey());
        return refundResult;
    }

    /**
     * 解密用户手机
     *
     * @param session_key
     * @param encryptedData
     * @param iv
     * @return
     */
    public static WxaDPhone decryptPhone(String session_key, String encryptedData, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            Key sKeySpec = new SecretKeySpec(Base64.decodeBase64(session_key), "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, new IvParameterSpec(Base64.decodeBase64(iv)));
            byte[] resultByte = cipher.doFinal(Base64.decodeBase64(encryptedData));
            String data = new String(PKCS7Encoder.decode(resultByte), StandardCharsets.UTF_8);
            return JsonUtil.parseObject(data, WxaDPhone.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean validateSignature(Map<String, String> map, String appKey) {
        String paySign = WechatSign.createSign(map, appKey);
        return paySign.equals(map.get("sign"));
    }
}
