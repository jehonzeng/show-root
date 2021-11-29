package com.szhengzhu.core;

import java.io.File;

/**
 * 公共常量
 *
 * @author Administrator
 * @date 2019年2月28日
 */
public interface Contacts {

    String TIMEZONE = "GMT+08:00";

    String TIME_MILLSECOND = "yyyy-MM-dd HH:mm:ss SSS";

    String TIME_SECOND = "yyyy-MM-dd HH:mm:ss";

    String TIME_MIN = "yyyy-MM-dd HH:mm";

    String TIME_DATE = "yyyy-MM-dd";

    String IMAGE_SERVER = "https://image.lujishou.com";

    Integer GOODS_DEF_IMG_SIZE = 1000;

    Integer OTHER_DEF_IMG_SIZE = 2000;

    String BASE_IMG_PATH = File.separator + "backend" + File.separator + "pictures"
            + File.separator;

    String CONSUMES = "application/json";

    String DEFAULT_IMG = "default_hands.png";

    String CUSTOMER_SERVICE = "客服热线：400-8920557";

    String NAME = "菜品";

    String SUCCESS_CODE = "200";

    /**
     * 商品标识
     */
    String TYPE_OF_GOODS = "0";

    /**
     * 菜品券标识
     */
    String TYPE_OF_VOUCHER = "1";

    /**
     * 套餐标识
     */
    String TYPE_OF_MEAL = "2";

    /**
     * 附属品标识
     */
    String TYPE_OF_ACCESSORY = "3";

    /**
     * 普通订单标识
     */
    String TYPE_OF_ORDER = "1";

    /**
     * 团购订单标识
     */
    String TYPE_OF_TEAMBUY_ORDER = "2";

    /**
     * 秒杀订单标识
     */
    String TYPE_OF_SECKILL_ORDER = "3";

    /**
     * 线下现场订单标识
     */
    String TYPE_OF_SCENE_ORDER = "4";

    /**
     * 订单未支付过期时间
     */
    Long ORDER_EXPIRED_TIME = 15 * 60 * 1000L;

    /**
     * 现场订单未支付过期时间
     */
    Long SCENE_ORDER_EXPIRED_TIME = 60 * 1000L;

    /**
     * 文本
     */
    Integer ACTION_TEXT_TYPE = 0;

    /**
     * 图片
     */
    Integer ACTION_IMAGE_TYPE = 1;

    /**
     * 图文
     */
    Integer ACTION_ARTICLE_TYPE = 2;

    /**
     * 模板
     */
    Integer ACTION_TEMPLATE_TYPE = 3;

    /**
     * 现场抽奖
     */
    Integer ACTION_SCAN_WIN_TYPE = 4;

    /**
     * 小程序卡片
     */
    Integer ACTION_MIN_TYPE = 5;

    String WECHAT_FOLLOW = "微信关注";

    String OPEN_THE_DOOR = "芝麻开门";

    String WECHAT_EVENT_CLICK = "CLICK";

    String WECHAT_EVENT_SUBSCRIBE = "subscribe";

    String WECHAT_EVENT_UNSUBSCRIBE = "unsubscribe";

    String WECHAT_EVENT_SCAN = "SCAN";

    String WECHAT_EVENT_MSG_TYPE_EVNET = "event";

    String WECHAT_EVENT_MSG_TYPE_TEXT = "text";

    /**
     * 并发消费者的个数
     */
    int RABBITMQ_DEF_CONCURRENT = 2;

    /**
     * 一次性从broker里面取的待消费的消息的个数
     */
    int RABBITMQ_DEF_PREFETCH = 50;

    /**
     * 露几手session用户存储名称
     */
    String LJS_SESSION = "LJS_USER";

    /**
     * 点餐session用户存储名称
     */
    String RESTAURANT_USER = "RESTAURANT_USER";

    /**
     * 点餐session存储点餐系统当前门店
     */
    String RESTAURANT_STORE = "RESTAURANT_STORE";

//    String RESTAURANT_MEMBER_RECHARGE = "充值";
//
//    String WECHAT_MEMBER_RECHARGE = "自助充值";
//
//    String WECHAT_MEMBER_RECHARGE_FAIL = "充值失败";
//
//    String RESTAURANT_MEMBER_BONUS = "赠送";
//
//    String RESTAURANT_MEMBER_CONSUMPTION = "消费";
//
//    String RESTAURANT_MEMBER_REFUND = "退款";

    String DEFAULT_MANAGER_CODE = "露几手";

    /**
     * 微信退款证书
     */
    String WECHAT_REFUND_CERT = "/etc/show/show_cert.p12";

    /**
     * 小程序退款证书
     */
    String X_WECHAT_REFUND_CERT = "/etc/show/apiclient_cert.p12";

    String TABLE_CODE_PREFIX = "8888";

    String MEMBER_CONSUME = "会员消费";

    String MEMBER_CONSUME_REFUND = "会员消费退款";

    /* 签到 */
    //已签到
    String SIGN_IN = "1";
    //未签到
    String NO_SIGN_IN = "0";

    /**
     *
     */
    long SCHEDULE_EXPIRE = 60 * 60 * 2;

    /**
     * 奖品概率倍数
     */
    Integer PROBABILITY = 100;
}
