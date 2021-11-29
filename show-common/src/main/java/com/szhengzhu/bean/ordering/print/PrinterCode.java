package com.szhengzhu.bean.ordering.print;

public enum PrinterCode {

    // 凉菜部
    PT00("P200", 1, "制作单"),
    // 热菜部
    PT01("P201", 1, "制作单"),
    // 煲仔部
    PT02("P202", 1, "制作单"),
    // 水吧台
    PT03("P203", 1, "制作单"),
    // 砍鸡砍椰区
    PT04("P204", 1, "制作单"),
    // 配菜部
    PT05("P205", 1, "制作单"),
    PT06("P206", 0, "收入总账单"),
    PT07("P207", 4, "结账单"),
    PT08("P208", 2, "预览单"),
    PT09("P209", 3, "预结账单");

    public String code;
    public Integer type;
    public String typeName;

    PrinterCode(String code, Integer type, String typeName) {
        this.code = code;
        this.type = type;
        this.typeName = typeName;
    }

    public static Integer getType(String code) {
        for (PrinterCode printerCode : values()) {
            if (printerCode.code.equals(code)) {
                return printerCode.type;
            }
        }
        return null;
    }

    public static String getTypeName(String code) {
        for (PrinterCode printerCode : values()) {
            if (printerCode.code.equals(code)) {
                return printerCode.typeName;
            }
        }
        return "";
    }
}
