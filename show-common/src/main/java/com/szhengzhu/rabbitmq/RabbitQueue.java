package com.szhengzhu.rabbitmq;

import java.util.Date;

/**
 * @author jehon
 * <p>
 * 按字母排序
 */
public class RabbitQueue {

    /*  */
    public static final String ACTIVITY = "activity";

    /* 取消订单恢复库存 */
    public static final String ADD_CURRENT_STOCK = "add-current-stock";

    /* 现场销售，取消订单恢复库存 */
    public static final String ADD_SCENE_GOODS_STOCK = "add-scene-stock";

    /* 支付成功的订单退款后加库存 */
    public static final String ADD_TOTAL_STOCK = "add-total-stock";

    /* 会员生日推送券提醒 */
    public static final String BIRTHDAY_TICKET = "birthday-ticket";

    /* 换桌推送信息 */
    public static final String CHANGE_TABLE_PUSH = "change-table-push";

    /* 清理过期券 */
    public static final String CLEAR_COUPON = "coupon-clear";

    /* 通知用户异常 */
    public static final String CONTACT_USER = "contact-user";

    /* 计算订单成本总额 */
    public static final String CALC_INDENT_BASE_TOTAL = "calc-indent-base-total";

    /* 推送（参加活动领取菜苗） */
    public static final String DISHES_ACTIVITY = "dishes-activity";

    /* 推送（菜品阶段） */
    public static final String DISHES_STAGE = "dishes-stage";

    /* 推送（领取机会快过期） */
    public static final String EXPIRE = "expire";

    /* 赠送积分 */
    public static final String GIFT_INTEGRAL = "gift-integral";

    /* 赠送优惠券 */
    public static final String GIVE_COUPON = "give-coupon";

    /* 会员支付后退款 */
    public static final String INDENT_MEMBER_REFUND = "indent-member-refund";

    /* 微信支付后退款 */
    public static final String INDENT_REFUND = "indent-refund";

    /* 会员支付后评价 */
    public static final String INDENT_REMARK = "indent-remark";

    /* 积分用户初始化 */
    public static final String INTEGRAL_ACCOUNT_CHECK = "integral-account-check";

    /* 积分兑换 */
    public static final String INTEGRAL_EXCHANGE = "integral-exchange";

    /* 积分过期推送 */
    public static final String INTEGRAL_EXPIRE = "integral-expire";

    /* 抽奖结果通知 */
    public static final String LOTTERY_RESULT = "lottery-result";

    /* 推送（成熟阶段） */
    public static final String MATURE = "mature";

    /* 会员折扣 */
    public static final String MEMBER_DISCOUNT = "member-discount";

    /* 用户成为会员 */
    public static final String MEMBER_GRADE = "member-grade";

    /* 会员消费 等级 */
    public static final String MEMBER_CONSUME = "member-consume";

    /* 会员套餐购买通知 */
    public static final String MEMBER_COMBO = "member-combo";

    /* 会员订单消费 等级 */
    public static final String MEMBER_INDENT_CONSUME = "member-indent-consume";

    /* 会员消费 等级退回 */
    public static final String MEMBER_CONSUME_REFUND = "member-consume-refund";

    /* 会员订单消费 等级退回 */
    public static final String MEMBER_INDENT_CONSUME_REFUND = "member-indent-consume-refund";

    /* 抽奖 */
    public static final String MEMBER_LOTTERY = "member-lottery";

    /* 发送优惠券给会员 */
    public static final String MEMBER_RECEIVE_TICKET = "member-receive-ticket";

    /* 撤回发送给会员的优惠券 */
    public static final String MEMBER_RECEIVE_TICKET_REFUND = "member-receive-ticket-refund";

    /* 会员签到 */
    public static final String MEMBER_SIGN = "member-sign";

    /* 修改微信关注状态 */
    public static final String MODIFY_WECHAT_STATUS = "modify-wechat-status";

    /* 商城退款 */
    public static final String ORDER_REFUND = "order-refund";

    /* 记录打印日志 */
    public static final String PRINT_LOG_BACK = "print-log-back";

    /* 会员消费赠送投票次数 */
    public static final String MATCH_CHANCE_COUNT = "match-chance-count";

    /* 现场中奖推送 */
    public static final String SCAN_WINNER = "scan-winner";

    /* 现场订单退款 */
    public static final String SCENE_ORDER_REFUND = "scene-order-refund";

    /* 发送优惠券 */
    public static final String SEND_COUPON = "coupon-send";

    /* 商城支付成功后推送信息给管理员 */
    public static final String SEND_MANAGE_MESSAGE = "send-manage-message";

    /* 商城推送订单确认信息 */
    public static final String SEND_ORDER_CONFIRM_MSG = "send-confirm-msg";

    /* 商城推送已配送信息 */
    public static final String SEND_ORDER_DELIVERY_MSG = "send-delivery-msg";

    /* 发送菜品券 */
    public static final String SEND_VOUCHER = "voucher-send";

    /*  */
    public static final String SHOW_ROBOT = "show-robot";

    /* 商城用户下单减库存 */
    public static final String SUB_CURRENT_STOCK = "sub-current-stock";

    /* 现场下单修改库存 */
    public static final String SUB_SCENE_GOOD_STOCK = "sub-scene-stock";

    /* 用户下单成功修改商品真实库存 */
    public static final String SUB_TOTAL_STOCK = "sub-total-stock";

    /* 优惠券过期 */
    public static final String TICKET_EXPIRE = "ticket_expire";

    /* 领取礼物 */
    public static final String RECEIVE_GIFT = "gift-receive";

    /* 预约提醒 */
    public static final String RESERVATION_NOTIFY = "reservation-notify";

    /* 领取商品 */
    public static final String RECEIVE_GOODS = "receive-goods";

    /* 刷新token */
    public static final String REFRESH_TOKEN = "refresh-token";

    /* 给用户发送队伍状态信息 */
    public static final String SEND_TEAM_STATUS = "send-team-status";
}
