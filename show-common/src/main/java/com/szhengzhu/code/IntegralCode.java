package com.szhengzhu.code;

/**
 * @author jehon
 * <p>
 * 积分消费类型
 */
public enum IntegralCode {

    EXCHANGE(-1, "兑换"),
    LOTTERY_CONSUME(-1, "抽奖消费"),
    CANCEL_GIVE(-1, "撤回赠送"),
    OTHER_GIVE(0, "其他赠送"),
    MEMBER_SIGN(1, "签到赠送"),
    MEMBER_GRADE(1, "等级升级赠送"),
    INDENT_GIVE(1, "用餐赠送"),
    LOTTERY_GIVE(1, "抽奖赠送"),
    JUDGE_GIVE(1, "评价得积分"),
    EXCHANGE_WITHDRAW(2, "撤回兑换");

    public Integer code;
    public String msg;

    IntegralCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

//    public static String getValue(Integer code) {
//        for (IntegralCode integralCode : values()) {
//            if (integralCode.code.equals(code)) {
//                return integralCode.msg;
//            }
//        }
//        return "";
//    }
}
