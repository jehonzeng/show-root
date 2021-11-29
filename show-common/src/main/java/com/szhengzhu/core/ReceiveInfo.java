package com.szhengzhu.core;

/**
 * 动态的领取信息
 * @author Administrator
 */
public class ReceiveInfo {

    public static String receive() {
        return "获得一次领取菜苗的机会。";
    }

    public static String receiveExpire() {
        return "领取菜苗的机会已失效。";
    }

    public static String receiveDish(String dishesName) {
        return "领取了一株" + dishesName + "菜苗。";
    }

    public static String dishExpire(String dishesName) {
        return "未及时兑换，一颗" + dishesName + "凋零了。";
    }

    public static String template(String templateName) {
        return "领取了一张" + templateName + "兑换券。";
    }
}
