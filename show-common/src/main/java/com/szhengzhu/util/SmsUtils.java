package com.szhengzhu.util;

import com.szhengzhu.core.StatusCode;
import com.szhengzhu.core.Result;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.gson.Gson;

/**
 * 容联云短信推送消息
 * 
 * @author Administrator
 * @date 2019年2月21日
 */
public class SmsUtils {

    private static final String SID = "aaf98f8953cadc690153e9a3370354a6";
    private static final String TOKEN = "6cb565f65a9e4f2db76ca5c338842b5f";
    private static final String URL = "app.cloopen.com";
    private static final String PORT = "8883";
    private static final String APPID = "aaf98f8953cadc690153e9c102745519";
    private static final String[] MODEL = { "176683", "183732", "337764", "343211", "343199" };
    private static final String TIME = "10分钟";

    public static Result<String> send(String phone, String smsMsg) {
        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        String[] msg = new String[] { smsMsg, TIME };
        result = restAPI.sendTemplateSMS(phone, MODEL[0], msg);
        if ("000000".equals(result.get("statusCode"))) {
            return new Result<String>();
        }
        if (!"160040".equals(result.get("statusCode"))) {
            return new Result<String>(StatusCode._5001);
        }
        result = restAPI.sendTemplateSMS(phone, MODEL[1], msg);
        if ("000000".equals(result.get("statusCode"))) {
            return new Result<String>();
        }
        return new Result<String>(StatusCode._5001);
    }

    public static CCPRestSmsSDK createCCPRestSmsSDK() {
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(URL, PORT);
        restAPI.setAccount(SID, APPID);
        restAPI.setAppId(TOKEN);
        return restAPI;
    }

    /**
     * 通知短信 节日祝福一模板
     * 
     * @param phone
     * @param smsMsg
     * @return
     */
    public static Result<?> holidayFirstTemplateMsg(String phone, String smsMsg) {
        HashMap<String, Object> map = null;
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        String[] msg = smsMsg.split("&");
        map = restAPI.sendTemplateSMS(phone, MODEL[2], msg);
        System.out.println(new Gson().toJson(map));
        return resultMsg(map);
    }

    /**
     * 通知短信 节日祝福二模板
     * 
     * @param phone
     * @param smsMsg
     * @return
     */
    public static Result<?> holidaySecondTemplateMsg(String phone, String smsMsg) {
        HashMap<String, Object> map = null;
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        String[] msg = new String[] { smsMsg };
        map = restAPI.sendTemplateSMS(phone, MODEL[3], msg);
        System.out.println(new Gson().toJson(map));
        return resultMsg(map);
    }

    /**
     * 通知短信 优惠券过期模板
     * 
     * @param phone
     * @param smsMsg
     * @return
     */
    public static Result<?> noticeOverdueMsg(String phone, String smsMsg) {
        HashMap<String, Object> map = null;
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        map = restAPI.sendTemplateSMS(phone, MODEL[4], new String[] { smsMsg });
        System.out.println(new Gson().toJson(map));
        return resultMsg(map);
    }

    /**
     * 获取短信回调的结果集，封装到自定义result中
     * 
     * @date 2019年2月21日 下午4:16:10
     * @param result
     * @return
     */
    private static Result<?> resultMsg(HashMap<String, Object> map) {
        String statusCode = (String) map.get("statusCode");
        String statusMsg = (String) map.get("statusMsg");
        Result<String> result = new Result<>(statusCode, statusMsg);
        if ("000000".equals(statusCode)) {
            return result;
        } else {
            return result;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(send("15215730485", "123456")));
    }
}
