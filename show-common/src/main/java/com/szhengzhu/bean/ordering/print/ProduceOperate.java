package com.szhengzhu.bean.ordering.print;

public enum ProduceOperate {

    ORDER_DISH(1, "点菜"),
    RETURN_DISH(2, "退菜"),
    CHANGE_TABLE(3, "换桌");

    public Integer code;
    public String msg;

    ProduceOperate(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(Integer code) {
        for (ProduceOperate produceOperate : values()) {
            if (produceOperate.code.equals(code)) {
                return produceOperate.msg;
            }
        }
        return "";
    }
}
