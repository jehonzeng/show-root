package com.szhengzhu.util;

import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhengzhu.bean.kd.KdResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class KdQueryUtils {

    /**  实时查询请求地址 */
    private static final String SYNQUERY_URL = "http://poll.kuaidi100.com/poll/query.do";

    /** 授权key */
    private static final String key = "cbXVwyrn382";

    /** 查询公司编号 */
    private static final String customer = "7FEFACC406430E532042F906C55984DF";
    
    /**
     * 实时查询快递信息并解析数据返回
     * 
     * @date 2019年9月4日 下午2:23:58
     * @param com 快递公司名称   快递给的编号
     * @param num 快递号
     * @return
     * @throws Exception
     */
    public static KdResult queryData(String com, String num) throws Exception {
        return queryData(com, num, "", "", "");
    }
    
    /**
     * 实时查询快递信息并解析数据返回
     * 
     * @date 2019年9月4日 下午2:26:23
     * @param com
     * @param num
     * @param phone 手机号码后四位
     * @param from 出发地
     * @param to 目的地
     * @return
     * @throws Exception
     */
    public static KdResult queryData(String com, String num, String phone, String from, String to) throws Exception {
      String result = synQueryData(com, num, phone, from, to, 0);
//      String result = "{\"message\":\"ok\",\"nu\":\"776000756349705\",\"ischeck\":\"0\",\"condition\":\"00\",\"com\":\"shentong\",\"status\":\"200\",\"state\":\"0\",\"data\":[{\"time\":\"2019-09-04 09:57:06\",\"ftime\":\"2019-09-04 09:57:06\",\"context\":\"广东深圳福田转运中心-已装袋发往-湖北武昌转运中心\"},{\"time\":\"2019-09-04 09:57:06\",\"ftime\":\"2019-09-04 09:57:06\",\"context\":\"广东深圳福田转运中心-已进行装车扫描\"},{\"time\":\"2019-09-04 08:51:26\",\"ftime\":\"2019-09-04 08:51:26\",\"context\":\"广东深圳福田转运中心-已发往-湖北武昌转运中心\"},{\"time\":\"2019-09-04 08:51:26\",\"ftime\":\"2019-09-04 08:51:26\",\"context\":\"广东深圳福田转运中心-已进行装袋扫描\"},{\"time\":\"2019-09-04 08:33:46\",\"ftime\":\"2019-09-04 08:33:46\",\"context\":\"已到达-广东深圳福田转运中心\"},{\"time\":\"2019-09-03 19:21:17\",\"ftime\":\"2019-09-03 19:21:17\",\"context\":\"深圳福田科技园分点-邹文龙(15012689530,0755-61410360)-已收件\"}]}";
      ObjectMapper mapper = new ObjectMapper();
      mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      KdResult kdResult = mapper.readValue(result, KdResult.class);
      return kdResult;
    }
    
    /**
     * 实时查询快递单号
     * 
     * @date 2019年9月4日 下午2:35:34
     * @param com 快递公司编码
     * @param num 快递单号
     * @return
     */
    public static String synQueryData(String com, String num) {
//        String result = "{\"message\":\"ok\",\"nu\":\"776000756349705\",\"ischeck\":\"0\",\"condition\":\"00\",\"com\":\"shentong\",\"status\":\"200\",\"state\":\"0\",\"data\":[{\"time\":\"2019-09-04 09:57:06\",\"ftime\":\"2019-09-04 09:57:06\",\"context\":\"广东深圳福田转运中心-已装袋发往-湖北武昌转运中心\"},{\"time\":\"2019-09-04 09:57:06\",\"ftime\":\"2019-09-04 09:57:06\",\"context\":\"广东深圳福田转运中心-已进行装车扫描\"},{\"time\":\"2019-09-04 08:51:26\",\"ftime\":\"2019-09-04 08:51:26\",\"context\":\"广东深圳福田转运中心-已发往-湖北武昌转运中心\"},{\"time\":\"2019-09-04 08:51:26\",\"ftime\":\"2019-09-04 08:51:26\",\"context\":\"广东深圳福田转运中心-已进行装袋扫描\"},{\"time\":\"2019-09-04 08:33:46\",\"ftime\":\"2019-09-04 08:33:46\",\"context\":\"已到达-广东深圳福田转运中心\"},{\"time\":\"2019-09-03 19:21:17\",\"ftime\":\"2019-09-03 19:21:17\",\"context\":\"深圳福田科技园分点-邹文龙(15012689530,0755-61410360)-已收件\"}]}";
        String result = synQueryData(com, num, "", "", "", 0);
        return result;
    }

    /**
     * 实时查询快递单号
     * @param com           快递公司编码
     * @param num           快递单号
     * @param phone         手机号
     * @param from          出发地城市
     * @param to            目的地城市
     * @param resultv2      开通区域解析功能：0-关闭；1-开通
     * @return
     */
    public static String synQueryData(String com, String num, String phone, String from, String to, int resultv2) {
        StringBuilder param = new StringBuilder("{");
        param.append("\"com\":\"").append(com).append("\"");
        param.append(",\"num\":\"").append(num).append("\"");
        param.append(",\"phone\":\"").append(phone).append("\"");
        param.append(",\"from\":\"").append(from).append("\"");
        param.append(",\"to\":\"").append(to).append("\"");
        if(1 == resultv2) {
            param.append(",\"resultv2\":1");
        } else {
            param.append(",\"resultv2\":0");
        }
        param.append("}");
        
        Map<String, String> params = new HashMap<>();
        params.put("customer", customer);
        String sign = DigestUtil.md5Hex(param + key + customer);
        params.put("sign", sign);
        params.put("param", param.toString());
        
        return post(params);
    }

    /**
     * 发送post请求
     */
    private static String post(Map<String, String> params) {
        StringBuffer response = new StringBuffer("");
        
        BufferedReader reader = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (builder.length() > 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                builder.append('=');
                builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] bytes = builder.toString().getBytes("UTF-8");

            URL url = new URL(SYNQUERY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(bytes);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return response.toString();
    }
//    public static void main(String[] args) throws Exception {
//        String com = "shentong";            //快递公司编码
//        String num = "776000756349705"; //快递单号
//        KdResult result = queryData(com, num);
//        System.out.println(result.getData());
//        String str = "{\"message\":\"ok\",\"nu\":\"776000756349705\",\"ischeck\":\"0\",\"condition\":\"00\",\"com\":\"shentong\",\"status\":\"200\",\"state\":\"0\",\"data\":[{\"time\":\"2019-09-04 09:57:06\",\"ftime\":\"2019-09-04 09:57:06\",\"context\":\"广东深圳福田转运中心-已装袋发往-湖北武昌转运中心\"},{\"time\":\"2019-09-04 09:57:06\",\"ftime\":\"2019-09-04 09:57:06\",\"context\":\"广东深圳福田转运中心-已进行装车扫描\"},{\"time\":\"2019-09-04 08:51:26\",\"ftime\":\"2019-09-04 08:51:26\",\"context\":\"广东深圳福田转运中心-已发往-湖北武昌转运中心\"},{\"time\":\"2019-09-04 08:51:26\",\"ftime\":\"2019-09-04 08:51:26\",\"context\":\"广东深圳福田转运中心-已进行装袋扫描\"},{\"time\":\"2019-09-04 08:33:46\",\"ftime\":\"2019-09-04 08:33:46\",\"context\":\"已到达-广东深圳福田转运中心\"},{\"time\":\"2019-09-03 19:21:17\",\"ftime\":\"2019-09-03 19:21:17\",\"context\":\"深圳福田科技园分点-邹文龙(15012689530,0755-61410360)-已收件\"}]}";
//        Map mapType = JSON.parseObject(str,Map.class);
//        System.out.println(mapType.containsKey("mess1age"));
//        System.out.print(synQueryData("shentong", "776000745337082"));
//    }
}
