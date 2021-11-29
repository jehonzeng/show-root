package com.szhengzhu.code;

public enum PushStatus {

    MEMBER("1428184921079418881", "会员"),
    NON_MEMBER("1428184948057182209", "非会员"),
    DEFAULT("1428259319455027201", "默认"),
    CUSTOM_INPUT("1428259944817364993", "自定义输入"),
    PROMOTION("1429715163740770304", "晋级"),
    WEED_OUT("1429715236646162433", "淘汰");

    public String code;

    public String msg;

    PushStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        for (PushStatus pushStatus : values()) {
            if (pushStatus.code.equals(code)) {
                return pushStatus.msg;
            }
        }
        return "";
    }
}
