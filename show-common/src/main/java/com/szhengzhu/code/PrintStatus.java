package com.szhengzhu.code;

public enum PrintStatus {

    POS_ES_EXCEPTION(500, "打印机异常"),
    POS_ES_PAPERENDING(6, "纸将尽"),
    POS_ES_DRAWERHIGH(5, "钱箱高电平"),
    POS_ES_CUT(4, "切刀未复位"),
    POS_ES_DOOROPEN(3, "纸仓门开"),
    POS_ES_HEAT(2, "机头过热"),
    POS_ES_PAPEROUT(1, "打印机缺纸"),
    POS_ES_SUCCESS(0, "成功"),
    POS_ES_INVALIDPARA(-1, "参数错误"),
    POS_ES_WRITEFAIL(-2, "写失败"),
    POS_ES_READFAIL(-3, "读失败"),
    POS_ES_NONMONOCHROMEBITMAP(-4, "非单色位图"),
    POS_ES_OVERTIME(-5, "超时"),
    POS_ES_FILEOPENERROR(-6, "文件打开失败"),
    POS_ES_OPENFAIL(-7, "打印机打开失败"),
    POS_ES_OTHERERRORS(-100, "其他原因导致的错误");

    public Integer code;
    public String msg;

    PrintStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(Integer code) {
        for (PrintStatus printStatus : values()) {
            if (printStatus.code.equals(code)) {
                return printStatus.msg;
            }
        }
        return "其他异常错误";
    }
}
