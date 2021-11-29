package com.szhengzhu.code;

public enum IndentStatus {

    READY("IS01", "待下单"),
    CREATE("IS02", "已下单"),
    PAYING("IS03", "支付中"),
    BILL("IS04", "已结账"),
    FAILURE("IS05", "已失效"),
    SHIFT("IS06", "已评价"),
    EVALUATION("IS07", "已交班");

    public String code;
    public String msg;

    IndentStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(String code) {
        for (IndentStatus status : values()) {
            if (status.code.equals(code)) {
                return status.msg;
            }
        }
        return "";
    }
}
