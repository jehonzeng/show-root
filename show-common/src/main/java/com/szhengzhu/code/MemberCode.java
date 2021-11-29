package com.szhengzhu.code;

/**
 * @author jehon
 *
 * 会员消费类型
 *
 */
public enum MemberCode {

    CONSUME(-1, "消费"),
    GIVE(0, "赠送"),
    RECHARGE(1, "充值"),
    CONSUME_REFUND(2, "退款");

    public Integer code;
    public String msg;

    MemberCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(Integer code) {
        for (MemberCode memberCode : values()) {
            if (memberCode.code.equals(code)) {
                return memberCode.msg;
            }
        }
        return "";
    }
}
