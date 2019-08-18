package com.szhengzhu.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * 自定义字符串工具类
 * 
 * @author Administrator
 * @date 2019年2月20日
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0 || s.equals("null");
    }

    public static String format(double p, String s) {
        DecimalFormat a = new DecimalFormat(s);
        return a.format(p);
    }

    public static String format(double p) {
        return format(p, "#.##");
    }

    public static String exception(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getRandomId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
