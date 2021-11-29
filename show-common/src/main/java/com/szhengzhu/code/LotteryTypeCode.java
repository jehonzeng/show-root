package com.szhengzhu.code;

public enum LotteryTypeCode {

    INTEGRAL_LOTTERY(1, "积分抽奖"),
    FRESH_LOTTERY(2, "新品尝鲜抽奖");

    public Integer code;
    public String msg;

    LotteryTypeCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        for (LotteryTypeCode lotteryTypeCode : values()) {
            if (lotteryTypeCode.code.equals(code)) {
                return lotteryTypeCode.msg;
            }
        }
        return "";
    }
}
