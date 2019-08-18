package com.szhengzhu.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;

import com.szhengzhu.application.WechatConfig;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.common.Commons;

import weixin.popular.api.PayMchAPI;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.util.JsUtil;

public class WechatUtils {

    /**
     * 获取微信用户信息
     * 
     * @date 2019年4月24日 下午4:54:15
     * @param openId
     * @param accessToken
     * @param refreshToken
     * @return
     */
    public static User getWxUser(String appId, String openId, String accessToken, String refreshToken) {
        User wxUser = SnsAPI.userinfo(accessToken, openId, "zh_CN");
        if ((!wxUser.isSuccess()) && wxUser.getErrcode().equals("40001")) {
            SnsToken snsToken = SnsAPI.oauth2RefreshToken(appId, refreshToken);
            wxUser = UserAPI.userInfo(snsToken.getAccess_token(), openId);
        }
        return wxUser;
    }
    
    /**
     * 上传微信用户头像到本系统
     * 
     * @date 2019年5月10日 下午4:24:19
     * @param url
     * @return
     */
    public static ImageInfo downLoadImage(String url) {
        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.initApacheHttpClient();
        try {
            String id = IdGenerator.getInstance().nexId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String path = File.separator + "usr" + File.separator + "pictures";
            String source = File.separator + "user" + File.separator + sdf.format(new Date());
            File source_file = new File(path + source);
            if (!source_file.exists())
                source_file.mkdirs();
            imageDownloader.fetchContent(url, path + source + File.separator + id + ".png");
            ImageInfo image = new ImageInfo();
            image.setMarkId(id);
            image.setFileType("png");
            image.setImagePath(source);
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
     * 微信内统一下单
     * 
     * @date 2019年5月10日 下午4:31:05
     * @param trade_no
     * @param ip
     * @param wx_openid
     * @param price
     * @return
     */
    public static UnifiedorderResult unifiedOrder(WechatConfig wechatConfig, String orderNo, String ip,
            String wopenId, String price) {
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(wechatConfig.getAppid());
        unifiedorder.setMch_id(wechatConfig.getMach_id());
        unifiedorder.setNonce_str(wechatConfig.getNoncestr());
        unifiedorder.setBody("露几手-订单");
        unifiedorder.setOut_trade_no(orderNo);
        unifiedorder.setTotal_fee(price);
        unifiedorder.setSpbill_create_ip(ip);
        unifiedorder.setNotify_url(Commons.WECHAT_SERVER + Commons.PAY_BACK);
        unifiedorder.setTrade_type("JSAPI");
        unifiedorder.setOpenid(wopenId);
        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
        return un;
    }
    
    /**
     * H5统一下单
     * @date 2019年7月24日 上午11:56:52
     * @param wechatConfig
     * @param orderNo
     * @param ip
     * @param price
     * @return
     */
    public static UnifiedorderResult unifiedOrder(WechatConfig wechatConfig, String orderNo, String ip, String price) {
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(wechatConfig.getAppid());
        unifiedorder.setMch_id(wechatConfig.getMach_id());
        unifiedorder.setNonce_str(wechatConfig.getNoncestr());
        unifiedorder.setBody("露几手-订单");
        unifiedorder.setOut_trade_no(orderNo);
        unifiedorder.setTotal_fee(price);
        unifiedorder.setSpbill_create_ip(ip);
        unifiedorder.setNotify_url(wechatConfig.getPay_back());
        unifiedorder.setTrade_type("MWEB");
        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
        return un;
    }
    
    /**
     * h5支付后重定向url
     * 
     * @date 2019年7月24日 下午12:01:08
     * @param mweb_url
     * @return
     */
    public static String webPayRedirect(String redirectUrl) {
        StringBuilder url = null;
        try {
            url = new StringBuilder();
            url.append(redirectUrl);
            url.append("&redirect_url=");
            url.append(URLEncoder.encode("https://shop.szhengzhu.com/orderInfo.html","utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url.toString();
    }
    
    /**
     * 构建下单结果信息，返回给前端
     * 
     * @date 2019年5月10日 下午5:04:31
     * @param prepay_id
     * @return
     */
    public static SortedMap<String, String> buildValidationInfo(WechatConfig wechatConfig, String prepay_id) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        finalpackage.put("appId", wechatConfig.getAppid());
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", wechatConfig.getNoncestr());
        finalpackage.put("package", "prepay_id=" + prepay_id);
        finalpackage.put("signType", "MD5");
        String paySign = WechatSign.createSign(finalpackage, wechatConfig.getKey());
        SortedMap<String, String> auth = new TreeMap<String, String>();
        auth.put("paySign", paySign);
        auth.put("nonceStr", wechatConfig.getNoncestr());
        auth.put("appId", wechatConfig.getAppid());
        auth.put("timeStamp", timestamp);
        auth.put("prepay_id", prepay_id);
        return auth;
    }
    
    public static boolean validateSignature(Map<String, String> map, String appKey) {
        String paySign = WechatSign.createSign(map, appKey);
        return paySign.equals(map.get("sign"));
    }
    
    /**
     * 生成配置信息
     * 
     * @date 2019年5月17日 下午2:48:23
     * @param url
     * @return
     */
    public static SortedMap<String, String> buildConfigInfo(WechatConfig wechatConfig, String url) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<String, String> configMap = new TreeMap<String, String>();
        String signature = JsUtil.generateConfigSignature(wechatConfig.getNoncestr(), wechatConfig.getTicket(), timestamp, url);
        configMap.put("signature", signature);
        configMap.put("appId", wechatConfig.getAppid());
        configMap.put("timestamp", timestamp);
        configMap.put("nonceStr", wechatConfig.getNoncestr());
        return configMap;
    }
    
    public static void main(String[] args) {
        String signature = JsUtil.generateConfigSignature("Wm3WZYTPz0wzccnW", "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg", "1414587457", "http://mp.weixin.qq.com?params=value");
        System.out.println(signature);
    }

}
