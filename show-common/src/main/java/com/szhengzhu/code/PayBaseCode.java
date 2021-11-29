package com.szhengzhu.code;

public enum PayBaseCode {

    WECHAT_PAY("210455967804985344", "微信支付"),
    MEMBER_PAY("175578759235096576", "会员支付"),
    INTEGRAL_PAY("175659301309698048", "会员积分"),
    // 会员券支付时，记录优惠券名称
    TICKET_PAY("211491478162149376", ""),
    MEMBER_DISCOUNT("1437672656857075712","会员折扣");

    public String code;
    public String msg;

    PayBaseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        for (PayBaseCode payBaseCode : values()) {
            if (payBaseCode.code.equals(code)) {
                return payBaseCode.msg;
            }
        }
        return "";
    }
}
