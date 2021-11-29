package com.szhengzhu.code;

public enum MemberActivityCode {

    RECEIVE_DISH("1339477120249892864", "领取菜品"),
    BIRTHDAY_TICKET("1384043488114511872", "会员生日礼包"),
    LOTTERY("","会员积分抽奖"),
    SIGN_IN("","会员签到");

    public String code;
    public String msg;

    MemberActivityCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        for (MemberActivityCode memberActivityCode : values()) {
            if (memberActivityCode.code.equals(code)) {
                return memberActivityCode.msg;
            }
        }
        return "";
    }
}
