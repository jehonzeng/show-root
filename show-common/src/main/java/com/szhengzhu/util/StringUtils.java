package com.szhengzhu.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;

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
    
    /**
     * StringUtils工具类方法 获取一定长度的随机字符串，范围0-9，a-z
     * 
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static boolean isNumeric(String str){ 
        if (isEmpty(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*"); 
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false; 
        } 
        return true; 
     }
    
    /**
     * 缩略字符串（不区分中英文字符）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("UTF-8").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isEmpty(html)){
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }
    
    
    public static void main(String[] args) {
//        System.out.println(getRandomStringByLength(5));
        
//        System.out.println(isNumeric(""));
//        String str = StringEscapeUtils.unescapeHtml4("&lt;p&gt;【产品名称】艾酷维多种维生素锌软糖&lt;/p&gt;");
//        System.out.println(str);
    }
}
