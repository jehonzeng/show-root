package com.szhengzhu.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import com.alibaba.fastjson.JSONObject;

/**
 * 极客工具类
 * 
 * @author Administrator
 * @date 2019年7月15日
 */
public class GklifeUtils {

    private static final String GK_URL = "http://test.openapi.gklife.com.cn";// 请求的地址

    private static final String PARTNER_NO = "24011800026";

    private static final String APP_KEY = "EMYMPVCP";

    private static final String APP_SECRET = "CLjsE7QRvIbrg8iPOKqhllZxQU0rK71N";

    /**
     * 地址验证
     * 
     * @date 2019年7月16日 上午10:52:30
     * @return
     */
    public static String sendWithIn(String city, String address) {
        String method = "gklife.network.within";
        String str = String.valueOf(System.currentTimeMillis() / 1000);
        String data = null;
        try {
            String param = "partner_no=" + URLEncoder.encode(PARTNER_NO, "UTF-8") + "&app_key="
                    + URLEncoder.encode(APP_KEY, "UTF-8") + "&time=" + URLEncoder.encode(str, "UTF-8") + "&method="
                    + URLEncoder.encode(method, "UTF-8") + "&city=" + URLEncoder.encode(city, "UTF-8") + "&address="
                    + URLEncoder.encode(address, "UTF-8") + "&lng=" + URLEncoder.encode("", "UTF-8")
                    + "&lat=" + URLEncoder.encode("", "UTF-8") + "&position_source=" + URLEncoder.encode("3", "UTF-8");
            String teString = PARTNER_NO + str + method + APP_SECRET;
            String md5Sign = encrypt(teString);
            data = param + "&sign=" + URLEncoder.encode(md5Sign, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendPost(data);
    }

    /**
     * 发送请求到极客配送
     * 
     * @date 2019年7月15日 下午5:26:57
     * @param data
     * @return
     */
    private static String sendPost(String data) {
        try {
            // 根据地址创建URL对象
            URL url = new URL(GK_URL);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(30 * 1000);
            urlConnection.setConnectTimeout(30 * 1000);
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
                                            // setDoInput的默认值就是true
            // 获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            System.out.println(data);
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                System.out.println(result);
                return result;
            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sign 加密方法
     * 
     * @date 2019年7月15日 下午5:23:44
     * @param s
     * @return
     */
    private final static String encrypt(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = null;
            btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String city = "深圳市";
        String address = "广东省深圳市南山区高新南一道6号TCL大厦A座606";
        String result = sendWithIn(city, address);
        JSONObject object = JSONObject.parseObject(result);
        String res = object.getString("result");
        System.out.println(res);
        String info = object.getString("info");
        JSONObject infoJson = JSONObject.parseObject(info);
        int within = infoJson.getIntValue("within");
//        double lng = infoJson.getDoubleValue("lng");
//        double lat = infoJson.getDoubleValue("lat");
        System.out.println(within);
    }
}
