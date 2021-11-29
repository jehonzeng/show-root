package com.szhengzhu.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.order.ContactUser;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.ClientProtocolException;
import weixin.popular.api.*;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.message.message.Message;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.TemplateMessageMiniProgram;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.bean.paymch.SecapiPayRefund;
import weixin.popular.bean.paymch.SecapiPayRefundResult;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.JsUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Log4j2
public class WechatUtils {
    /**
     * 正式 ：0：支付成功 1：通知管理员 2：订单状态 3：订单配送  4:退款  5：会员注册  6:领取菜品活动  7:订单完成提醒  8:通知用户异常
     * 9：优惠券过期提醒 10:会员生日推送券
     */
//    private final static String[] TEMPLATE_IDS = {"UnJzxPKTqMgLbKkqxMe-iQGoiH2INqd5cZJ9bYHkriU",
//            "5CzqTgUjcxM9rz2FqByP9AqenaYgvmSbJEuZ6lRwKqQ", "K1fzJQtcSnWAVBCj6Qn_MPjJ8jMggSAKO6PzEzMa67A",
//            "PwSGxzs9QUYg3V9DIzl3c2MkpPXENcd9VGkNpy5EmcE", "gzYIUd7_I6hAPvPXow7EIUm9t82dOz7O_7FI9uw2Fos",
//            "-I1MccXfyOgkOCvdggrBuiP5_f8n7FhB-TY0qxTnrHg", "snkPhxp_3pQRdotWvlOmsEIY-IWpJMAgTDErxiiA7Sg",
//            "qWaXdtA20o27u9L-MpPBOElH4uSSc7rTTqKSyUN0IHQ", "2HgoDXpnT2wnBd6RbAov6llLx0ahspVWqBW4Js5I2t8",
//            "EUyq1uDmSEkl7EFp0DwlR0JBSePVRLieNdU3MAOpyEw", "Ep3XEb8pFgwy7i30pTcKqRcSl9F1HDfBNzkrsQCDjLY",
//            "jWqKobUdvGm_RgMCiUKYnwxFvGlmv43xtFguUhORYE4"};

    interface Template {
        /* 支付成功 */
        String PAY_SUCCESS = "UnJzxPKTqMgLbKkqxMe-iQGoiH2INqd5cZJ9bYHkriU";
        /* 通知管理员 */
        String NOTIFY_ADMIN = "5CzqTgUjcxM9rz2FqByP9AqenaYgvmSbJEuZ6lRwKqQ";
        /* 订单状态 */
        String ORDER_STATUS = "K1fzJQtcSnWAVBCj6Qn_MPjJ8jMggSAKO6PzEzMa67A";
        /* 订单配送 */
        String ORDER_DELIVERY = "PwSGxzs9QUYg3V9DIzl3c2MkpPXENcd9VGkNpy5EmcE";
        /* 退款 */
        String ORDER_REFUND = "gzYIUd7_I6hAPvPXow7EIUm9t82dOz7O_7FI9uw2Fos";
        /* 会员注册 */
        String MEMBER_REGISTER = "-I1MccXfyOgkOCvdggrBuiP5_f8n7FhB-TY0qxTnrHg";
        /* 领取菜品活动 */
        String RECEIVE_COMMODITY = "snkPhxp_3pQRdotWvlOmsEIY-IWpJMAgTDErxiiA7Sg";
        /* 订单完成提醒 */
        String ORDER_FINISH = "qWaXdtA20o27u9L-MpPBOElH4uSSc7rTTqKSyUN0IHQ";
        /* 通知用户异常 */
        String NOTIFY_EXCEPTION = "2HgoDXpnT2wnBd6RbAov6llLx0ahspVWqBW4Js5I2t8";
        /* 优惠券过期提醒 */
        String COUPON_EXPIRED = "EUyq1uDmSEkl7EFp0DwlR0JBSePVRLieNdU3MAOpyEw";
        /* 会员生日推送券 */
        String MEMBER_BIRTHDAY = "Ep3XEb8pFgwy7i30pTcKqRcSl9F1HDfBNzkrsQCDjLY";
        /* 竞赛活动队伍状态提醒 */
        String MATCH_TEAM_STATUS = "jWqKobUdvGm_RgMCiUKYnwxFvGlmv43xtFguUhORYE4";
        /* 通知用户积分将过期提醒 */
        String INTEGRAL_EXPIRE = "94fiaZ0o5bIgyaTCamLlocMyOYAapje0TrezgXUtk78";
        /* 抽奖结果通知 */
        String LOTTERY_RESULT_NOTIFY = "UVkfBLXJYbT73EcjBMF_zHq3vgBLZ-U86gat6FfSG8w";
    }

    /**
     * 抽奖结果通知
     */
    public static TemplateMessage lotteryResult(String prizeName) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.LOTTERY_RESULT_NOTIFY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create("感谢您对露几手的支持~恭喜您获得新品福利一份，快来兑奖并庆祝一下吧！！", "#173177"));
        data.put("keyword1", create(prizeName, "#173177"));
        data.put("keyword2", create(sdf.format(DateUtil.date()), "#173177"));
        data.put("remark", create("露几手欢迎您的到来~预祝您用餐愉快！！详情 >>", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 通知用户积分过期提醒
     */
    public static TemplateMessage integralExpire(String integral, String days, String integralTotal) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.INTEGRAL_EXPIRE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create(String.format("亲~您的%s积分将在%s后过期，赶快去积分商城使用吧！", integral, days), "#173177"));
        data.put("keyword1", create(sdf.format(DateUtil.date()), "#173177"));
        data.put("keyword2", create("0", "#173177"));
        data.put("keyword3", create("过期", "#173177"));
        data.put("keyword4", create(integralTotal, "#173177"));
        data.put("remark", create("如有疑问，请拨打4008-920-557咨询,微信客服：LJSSTYZJ", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 竞赛活动通知
     */
    public static TemplateMessage matchStatusSend(String teamName, Integer teamStatus, String phone) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.MATCH_TEAM_STATUS);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        if (teamStatus == 1) {
            data.put("first", create(String.format("恭喜，您支持的%s技压群雄勇夺桂冠，快来吃椰子鸡庆祝一下吧！！欢迎参与前台立减现金游戏~", teamName), "#173177"));
        } else {
            data.put("first", create(String.format("真遗憾，您支持的%s只能止步至此。吃椰子鸡可参与前台立减现金游戏，快来和小鹿一起玩耍吧~！", teamName), "#173177"));
        }
        data.put("keyword1", create(phone.substring(7), "#173177"));
        data.put("keyword2", create(sdf.format(DateUtil.date()), "#173177"));
        if (teamStatus == 1) {
            data.put("remark", create("备注：11月3日至5日，欢迎光临露几手门店观看LJS x LOL手游友谊赛>>", "#173177"));
        } else {
            data.put("remark", create("备注：请您多多关注露几手公众号，了解最新活动！点击进入>>", "#173177"));
        }
        msg.setData(data);
        return msg;
    }

    /**
     * 竞赛活动通知
     */
    public static TemplateMessage matchWinSend(String teamName, Integer teamStatus, String phone) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.MATCH_TEAM_STATUS);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        if (teamStatus == 1) {
            data.put("first", create(String.format("恭喜，您支持的%s技压群雄勇夺桂冠，快来吃椰子鸡庆祝一下吧！！欢迎参与前台立减现金游戏~", teamName), "#173177"));
        } else {
            data.put("first", create(String.format("真遗憾，您支持的%s只能止步至此。吃椰子鸡可参与前台立减现金游戏，快来和小鹿一起玩耍吧~！", teamName), "#173177"));
        }
        data.put("keyword1", create(phone.substring(7), "#173177"));
        data.put("keyword2", create(sdf.format(DateUtil.date()), "#173177"));
        if (teamStatus == 1) {
            data.put("remark", create("备注：11月3日至5日，欢迎光临露几手门店观看LJS x LOL手游友谊赛>>", "#173177"));
        } else {
            data.put("remark", create("备注：请您多多关注露几手公众号，了解最新活动！点击进入>>", "#173177"));
        }
        msg.setData(data);
        return msg;
    }


    /**
     * 会员生日推送券
     */
    public static TemplateMessage birthdayTicket(String name, String accountNo) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.MEMBER_BIRTHDAY);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create(String.format("亲爱的%s生日快乐！\n露几手·生态椰子鸡提前赠送您一份祝福礼包", name), "#173177"));
        data.put("keyword1", create(accountNo, "#173177"));
        data.put("keyword2", create("优惠券", "#173177"));
        data.put("keyword3", create("88", "#173177"));
        data.put("remark", create("点击「详情」查看福利，小鹿欢迎您来吃鸡~", "#173177"));
        msg.setUrl("");
        msg.setData(data);
        return msg;
    }

    /**
     * 优惠券过期提醒
     */
    public static TemplateMessage ticketExpire(String date, String total, String num, String name) throws ParseException {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.COUPON_EXPIRED);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date time = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(date);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create(String.format("亲~您有%s张%s优惠券即将到期，总面值%s元，赶快去使用吧。", num, name, total), "#173177"));
        data.put("keyword1", create("露几手·生态椰子鸡", "#173177"));
        data.put("keyword2", create(sdf.format(time), "#173177"));
        data.put("keyword3", create("买单时使用", "#173177"));
        data.put("remark", create("如有疑问，请拨打4008-920-557咨询,微信客服：LJSSTYZJ", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 通知用户异常
     */
    public static TemplateMessage contactUser(ContactUser user) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.NOTIFY_EXCEPTION);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create("订单配送异常", "#173177"));
        if (StrUtil.isNotEmpty(user.getContent())) {
            data.put("first", create(user.getContent(), "#173177"));
        }
        data.put("keyword1", create(user.getOrderNo(), "#173177"));
        data.put("keyword2", create(user.getName(), "#173177"));
        data.put("keyword3", create(user.getPhone(), "#173177"));
        data.put("keyword4", create("联系电话号码为空号", "#173177"));
        if (StrUtil.isNotEmpty(user.getReason())) {
            data.put("keyword4", create(user.getReason(), "#173177"));
        }
        data.put("remark", create("请您及时联系客服进行处理！微信号：szlujishou 电话号码：4008-920-557", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 提醒用户订单完成
     */
    public static TemplateMessage remarkInfo(String name, IndentInfo indentInfo, BigDecimal indentTotal, Integer integralTotal, BigDecimal memberTotal, Integer amount) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.ORDER_FINISH);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("{}，{}，{}", integralTotal, memberTotal, indentInfo.getTotal());
        data.put("first", create(String.format("%s感谢光临%s，期待您的下次光临！\n本次消费：%s", name, indentInfo.getStoreName(),
                indentTotal), "#173177"));
        if (memberTotal != null && indentInfo.getTotal() != null) {
            data.put("first", create(String.format("%s感谢光临%s，期待您的下次光临！\n本次消费：%s，卡内余额：%s \n本次积分：%s，卡内积分：%s",
                    name, indentInfo.getStoreName(), indentTotal, memberTotal, amount, integralTotal), "#173177"));
        }
        data.put("keyword1", create("用餐消费", "#173177"));
        data.put("keyword2", create(sdf.format(ObjectUtil.isNull(indentInfo.getTime()) ? new Date() : indentInfo.getTime()), "#173177"));
        data.put("remark", create("点击进行评论，可以领取积分哦！", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 领取菜品活动
     */
    public static TemplateMessage dishesActivity(Object beginDate, Object endDate) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.RECEIVE_COMMODITY);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        data.put("first", create("领取菜苗活动", "#173177"));
        data.put("keyword1", create(sdf.format(beginDate) + "-" + sdf.format(endDate), "#173177"));
        data.put("keyword2", create("正在进行中", "#173177"));
        data.put("remark", create("您有一个领取菜苗的机会，请尽快领取,点击进行查看", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 菜品状态阶段
     */
    public static TemplateMessage dishesStage(String name, String stage, String beginDays, String endDays) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.RECEIVE_COMMODITY);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create("您的" + name + "已经进入" + stage + "阶段", "#173177"));
        data.put("keyword1", create("成熟期", "#173177"));
        data.put("keyword2", create(stage, "#173177"));
        data.put("remark", create("您的" + name + "已经成熟，请尽快去领取对应菜品券", "#173177"));
        if (beginDays != null && endDays != null) {
            data.put("keyword1", create(beginDays + "-" + endDays + "天！", "#173177"));
            data.put("remark", create("点击进行查看！（成熟阶段即可领取菜品券）", "#173177"));
        }
        msg.setData(data);
        return msg;
    }

    /**
     * 菜品状态（将过期）
     */
    public static TemplateMessage expire(String name) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.RECEIVE_COMMODITY);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create("您的领取菜苗机会即将过期", "#173177"));
        if (name != null) {
            data.put("first", create("您的领取" + name + "券机会即将过期", "#173177"));
        }
        data.put("keyword1", create("距离过期时间还有三天", "#173177"));
        data.put("keyword2", create("等待领取中", "#173177"));
        data.put("remark", create("请尽快领取，不然机会将过期！！！", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 获取微信用户信息
     *
     * @param openId
     * @param accessToken
     * @param refreshToken
     * @return
     * @date 2019年4月24日 下午4:54:15
     */
    public static User getWxUser(String appId, String openId, String accessToken, String refreshToken) {
        User wxUser = SnsAPI.userinfo(accessToken, openId, "zh_CN");
        if ((!wxUser.isSuccess()) && wxUser.getErrcode().equals("40001")) {
            SnsToken snsToken = SnsAPI.oauth2RefreshToken(appId, refreshToken);
            wxUser = UserAPI.userInfo(snsToken.getAccess_token(), openId);
        }
        return wxUser;
    }

    public static ImageInfo downLoadImage(FtpServer ftpServer, String url) {
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
     * 微信内统一下单
     *
     * @param wechatConfig
     * @param orderNo
     * @param ip
     * @param wopenId
     * @param price
     * @return
     * @date 2019年5月10日 下午4:31:05
     */
    public static UnifiedorderResult unifiedOrder(WechatConfig wechatConfig, String orderNo, String ip, String
            wopenId,
                                                  String price) {
        Unifiedorder unifiedorder = createUnifiedOrder(wechatConfig, orderNo, ip, price);
        unifiedorder.setTrade_type("JSAPI");
        unifiedorder.setOpenid(wopenId);
        unifiedorder.setNotify_url(wechatConfig.getWechatServer() + wechatConfig.getWechatPayBack());
        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
        return un;
    }

    private static Unifiedorder createUnifiedOrder(WechatConfig wechatConfig, String orderNo, String ip, String price) {
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(wechatConfig.getAppId());
        unifiedorder.setMch_id(wechatConfig.getMachId());
        unifiedorder.setNonce_str(wechatConfig.getNonceStr());
        unifiedorder.setBody("露几手-订单");
        unifiedorder.setOut_trade_no(orderNo);
        unifiedorder.setTotal_fee(price);
        unifiedorder.setSpbill_create_ip(ip);
        return unifiedorder;
    }

    /**
     * web统一下单
     *
     * @param wechatConfig
     * @param orderNo
     * @param ip
     * @param price
     * @return
     * @date 2019年7月24日 上午11:56:52
     */
    public static UnifiedorderResult unifiedOrder(WechatConfig wechatConfig, String orderNo, String ip, String
            price) {
        Unifiedorder unifiedorder = createUnifiedOrder(wechatConfig, orderNo, ip, price);
        unifiedorder.setNotify_url(wechatConfig.getWechatServer() + wechatConfig.getWebPayBack());
        unifiedorder.setTrade_type("MWEB");
        UnifiedorderResult un = PayMchAPI.payUnifiedorder(unifiedorder, wechatConfig.getKey());
        return un;
    }

    /**
     * h5支付后重定向url
     *
     * @param redirectUrl
     * @return
     * @date 2019年7月24日 下午12:01:08
     */
    public static String webPayRedirect(String redirectUrl) {
        StringBuilder url = null;
        try {
            url = new StringBuilder();
            url.append(redirectUrl);
            url.append("&redirect_url=");
            url.append(URLEncoder.encode("https://m.lujishou.com/orderInfo.html", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url.toString();
    }

    /**
     * 构建下单结果信息，返回给前端
     *
     * @param wechatConfig
     * @param prepayId
     * @return
     * @date 2019年5月10日 下午5:04:31
     */
    public static SortedMap<String, String> buildValidationInfo(WechatConfig wechatConfig, String prepayId) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<String, String> finalPackage = new TreeMap<String, String>();
        finalPackage.put("appId", wechatConfig.getAppId());
        finalPackage.put("timeStamp", timestamp);
        finalPackage.put("nonceStr", wechatConfig.getNonceStr());
        finalPackage.put("package", "prepay_id=" + prepayId);
        finalPackage.put("signType", "MD5");
        String paySign = WechatSign.createSign(finalPackage, wechatConfig.getKey());
        SortedMap<String, String> auth = new TreeMap<>();
        auth.put("paySign", paySign);
        auth.put("nonceStr", wechatConfig.getNonceStr());
        auth.put("appId", wechatConfig.getAppId());
        auth.put("timeStamp", timestamp);
        auth.put("prepay_id", prepayId);
        return auth;
    }

    /**
     * 退款申请
     *
     * @param config
     * @param orderNo   订单号
     * @param refundNo  退款单号
     * @param totalFee  总金额
     * @param refundFee 退款金额
     * @return
     * @date 2019年7月29日 上午10:12:56
     */
    public static SecapiPayRefundResult refundOrder(WechatConfig config, String orderNo, String refundNo,
                                                    int totalFee,
                                                    int refundFee) {
        // 綁定api安全证书
        LocalHttpClient.initMchKeyStore(config.getMachId(), Contacts.WECHAT_REFUND_CERT);
        SecapiPayRefund payRefund = new SecapiPayRefund();
        payRefund.setAppid(config.getAppId());
        payRefund.setMch_id(config.getMachId());
        payRefund.setOp_user_id(config.getMachId());
        payRefund.setNonce_str(config.getNonceStr());
        payRefund.setOut_trade_no(orderNo);
        payRefund.setOut_refund_no(refundNo);
        payRefund.setTotal_fee(totalFee);
        payRefund.setRefund_fee(refundFee);
        payRefund.setSign_type("MD5");
        payRefund.setRefund_fee_type("CNY");
        SecapiPayRefundResult refundResult = PayMchAPI.secapiPayRefund(payRefund, config.getKey());
        return refundResult;
    }

    public static boolean validateSignature(Map<String, String> map, String appKey) {
        String paySign = WechatSign.createSign(map, appKey);
        return paySign.equals(map.get("sign"));
    }

    /**
     * 生成配置信息
     *
     * @param url
     * @return
     * @date 2019年5月17日 下午2:48:23
     */
    public static SortedMap<String, String> buildConfigInfo(WechatConfig wechatConfig, String url) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<String, String> configMap = new TreeMap<>();
        String signature = JsUtil.generateConfigSignature(wechatConfig.getNonceStr(), wechatConfig.getTicket(),
                timestamp, url);
        configMap.put("signature", signature);
        configMap.put("appId", wechatConfig.getAppId());
        configMap.put("timestamp", timestamp);
        configMap.put("nonceStr", wechatConfig.getNonceStr());
        return configMap;
    }

    /**
     * 生成支付成功推送信息组装
     *
     * @param orderNo
     * @param payAmount
     * @return
     * @date 2019年8月7日 下午6:01:09
     */
    public static TemplateMessage noticePaySucess(String orderNo, BigDecimal payAmount) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.PAY_SUCCESS);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
        data.put("first", create("您好，您的订单已支付成功。", "#173177"));
        data.put("keyword1", create(orderNo, "#173177"));
        data.put("keyword2", create(sdf.format(new Date()), "#173177"));
        data.put("keyword3", create(toFix(payAmount), "#173177"));
        data.put("remark", create("如有问题请致电露几手客服热线400-8920557或直接在微信留言，客服人员将第一时间为您服务。", "#173177"));
        msg.setData(data);
        return msg;
    }

    private static String toFix(BigDecimal price) {
        DecimalFormat df = new DecimalFormat("#.00");
        return "￥" + df.format(price);
    }

    /**
     * 通知管理员及时处理订单
     *
     * @param orderNo
     * @param orderTime
     * @param delivery
     * @return
     * @date 2018年12月5日
     */
    public static TemplateMessage noticeOrderAction(String orderNo, Date orderTime, OrderDelivery delivery) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.NOTIFY_ADMIN);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(delivery.getRemark());
        data.put("first", create("备注：" + delivery.getRemark(), "#FF0000"));
        data.put("Day", create(sdf.format(orderTime), "#173177"));
        data.put("orderId", create(orderNo, "#173177"));
        data.put("orderType", create("已付款", "#173177"));
        data.put("customerName", create(delivery.getContact(), "#173177"));
        data.put("customerPhone", create(delivery.getPhone(), "#173177"));
        data.put("remark", create("配送地址：" + delivery.getDeliveryArea() + delivery.getDeliveryAddress(),
                "#173177"));
        msg.setData(data);
        msg.setUrl("http://p.lujishou.com/orderInfo/index.html?orderNo=" + orderNo);
        return msg;
    }

    /**
     * 订单状态提醒
     *
     * @param orderNo
     * @return
     */
    public static TemplateMessage noticeOrderConfirm(String orderNo) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.ORDER_STATUS);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm");
        data.put("first", create("您好，您有一个订单状态已更新。", "#173177"));
        data.put("keyword1", create(orderNo, "#173177"));
        data.put("keyword2", create("商家已确认并开始安排生产", "#173177"));
        data.put("keyword3", create(sdf.format(new Date()), "#173177"));
        data.put("remark", create("请等待商家配送。", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 订单配送通知
     *
     * @param orderNo
     * @return
     */
    public static TemplateMessage noticeOrderFreight(String orderNo) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.ORDER_DELIVERY);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create("您好，您有一个订单状态已更新。", "#173177"));
        data.put("keyword1", create(orderNo, "#173177"));
        data.put("keyword2", create("订单正在配送中。", "#173177"));
        data.put("remark", create("您的订单正在配送中，请保持手机通畅。露几手客服电话：400-8920557。", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 退款成功推送消息
     *
     * @param orderNo
     * @param refundTime
     * @param totalPrice
     * @return
     * @date 2019年8月28日 下午6:11:38
     */
    public static TemplateMessage noticeRefund(String orderNo, Date refundTime, BigDecimal totalPrice) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.ORDER_REFUND);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm");
        data.put("first", create("您好，您的订单退款申请已通过。", "#173177"));
        data.put("keyword1", create(orderNo, "#173177"));
        data.put("keyword2", create("退款中", "#173177"));
        data.put("keyword3", create(sdf.format(refundTime), "#173177"));
        data.put("keyword4", create(toFix(totalPrice) + "元", "#173177"));
        data.put("remark", create("您的钱款将在1-7工作日内退还至您的支付账户，请耐心等待！", "#173177"));
        msg.setData(data);
        return msg;
    }

    /**
     * 扫码关注成为会员通知模板
     *
     * @param memberNo
     * @param xappId
     * @param xpagePath
     * @return
     */
    public static TemplateMessage noticeMember(String memberNo, String xappId, String xpagePath) {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplate_id(Template.MEMBER_REGISTER);
        LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<>();
        data.put("first", create("您好，您已成为露几手会员", "#173177"));
        data.put("keyword1", create(memberNo, "#173177"));
        data.put("keyword2", create(DateUtil.today(), "#173177"));
        data.put("remark", create("如有疑问，请咨询客服：400-8920557。", "#173177"));
        msg.setData(data);
        TemplateMessageMiniProgram min = new TemplateMessageMiniProgram();
        min.setAppid(xappId);
        min.setPagepath(xpagePath);
        return msg;
    }

    private static TemplateMessageItem create(String msg, String color) {
        return new TemplateMessageItem(msg, color);
    }

    /**
     * 推送微信信息
     *
     * @param config
     * @param msg
     * @date 2019年8月7日 下午6:12:26
     */
    public static void messageSend(WechatConfig config, TemplateMessage msg) {
        TemplateMessageResult result = MessageAPI.messageTemplateSend(config.getToken(), msg);
        if (!result.isSuccess() && result.getErrcode().equals("40001")) {
            config.refreshToken();
            result = MessageAPI.messageTemplateSend(config.getToken(), msg);
        }
        System.out.println("推送微信信息结果：" + result.isSuccess() + " " + result.getErrcode() + ":" + result.getErrmsg());
    }

    /**
     * 推送信息
     *
     * @param config
     * @param message
     */
    public static void messageSend(WechatConfig config, Message message) {
        BaseResult result = MessageAPI.messageCustomSend(config.getToken(), message);
        if (result.getErrcode().equals("40001")) {
            MessageAPI.messageCustomSend(config.refreshToken(), message);
        }
    }

    /**
     * 根据url上传图片素材
     *
     * @param config
     * @param url
     * @param mediaType
     * @return
     */
    public static Media uploadMedia(WechatConfig config, String url, MediaType mediaType) {
        Media media = null;
        try {
            media = MediaAPI.mediaUpload(config.getToken(), mediaType, new URI(url));
            if (!media.isSuccess() && media.getErrcode().equals("40001")) {
                media = MediaAPI.mediaUpload(config.refreshToken(), mediaType, new URI(url));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return media;
    }

    public static void main(String[] args) {
//        String signature = JsUtil.generateConfigSignature("Wm3WZYTPz0wzccnW",
//                "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg", "1414587457",
//                "http://mp.weixin.qq.com?params=value");
//        System.out.println(signature);
//        BigDecimal bigDecimal = new BigDecimal(28.01);
//        DecimalFormat df = new DecimalFormat("#.00");
//        System.out.println("￥" + df.format(bigDecimal));
//        System.out.println(Template.NOTIFY_ADMIN);
//
//        noticeRefund("123", DateUtil.date(), new BigDecimal(1));
    }
}
