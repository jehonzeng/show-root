package com.szhengzhu.code;

public enum TableStatus {

    FREEING("TS01", "空桌"),
    BOOKING("TS02", "已预定"),
    ORDERING("TS03", "点餐中"),
    EATING("TS04", "就餐中"),
    PAID("TS05", "结账中"),
    BILL("TS06", "已结账");

    public String code;
    public String msg;

    TableStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        for (TableStatus tableStatus : values()) {
            if (tableStatus.code.equals(code)) {
                return tableStatus.msg;
            }
        }
        return "";
    }
}
