package com.szhengzhu.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.gson.Gson;
import com.szhengzhu.bean.vo.DeliveryMsgVo;
import com.szhengzhu.bean.vo.SmsModel;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;

import java.util.HashMap;

/**
 * 容联云短信推送消息
 *
 * @author Administrator
 * @date 2019年2月21日
 */
public class SmsUtils {

    // private static final String SID = "aaf98f8953cadc690153e9a3370354a6"; // 露几手
    // private static final String TOKEN = "6cb565f65a9e4f2db76ca5c338842b5f"; //
    // 露几手
    // private static final String APPID = "aaf98f8953cadc690153e9c102745519"; //
    // 露几手
    // private static final String[] MODEL = { "176683", "183732", "337764",
    // "343211", "343199" };

    private static final String SID = "8a216da861f5a2570161ff740650043b";
    private static final String TOKEN = "91aa1dee0e1547489909f822ac75d0f1";
    private static final String APPID = "8a216da86c282c6a016c51b7ecbb1aec";
    private static final String[] MODEL = {"460713", "460713", "337764", "343211", "343199", "473762"};

    private static final String URL = "app.cloopen.com";
    private static final String PORT = "8883";
    private static final String TIME = "3分钟";

    public static void send(String phone, String smsMsg) {
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        String[] msg = new String[]{smsMsg, TIME};
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phone, MODEL[0], msg);
        if ("000000".equals(result.get("statusCode"))) {
            return;
        }
        ShowAssert.checkTrue(!"160040".equals(result.get("statusCode")), StatusCode._5001);
        result = restAPI.sendTemplateSMS(phone, MODEL[1], msg);
        if ("000000".equals(result.get("statusCode"))) {
            return;
        }
        ShowAssert.checkTrue(!"000000".equals(result.get("statusCode")), StatusCode._5001);
    }

    public static CCPRestSmsSDK createCCPRestSmsSDK() {
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(URL, PORT);
        restAPI.setAccount(SID, TOKEN);
        restAPI.setAppId(APPID);
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
        String[] msg = new String[]{smsMsg};
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
        map = restAPI.sendTemplateSMS(phone, MODEL[4], new String[]{smsMsg});
        System.out.println(new Gson().toJson(map));
        return resultMsg(map);
    }

    /**
     * 通用模板信息通知(批量)
     *
     * @param base
     * @param contents
     * @return
     * @date 2019年9月2日
     */
    public static Result<?> sendDynamicMsg(SmsModel base, String[] contents) {
        HashMap<String, Object> map = null;
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        map = restAPI.sendTemplateSMS(base.getPhone(), base.getTemplateId(), contents);
        return resultMsg(map);
    }

    /**
     * 配送模板信息通知(单个)
     *
     * @param base
     * @return
     * @date 2019年9月2日
     */
    public static Result<?> sendDeliveryMsg(DeliveryMsgVo base) {
        HashMap<String, Object> map = null;
        CCPRestSmsSDK restAPI = createCCPRestSmsSDK();
        map = restAPI.sendTemplateSMS(base.getPhone(), MODEL[5],
                new String[]{base.getName(), base.getTrackNo(), base.getCustomerService()});
        return resultMsg(map);
    }

    /**
     * 获取短信回调的结果集，封装到自定义result中
     *
     * @param map
     * @return
     * @date 2019年2月21日 下午4:16:10
     */
    private static Result<?> resultMsg(HashMap<String, Object> map) {
        String statusCode = (String) map.get("statusCode");
        String statusMsg = (String) map.get("statusMsg");
        Result<String> result = new Result<>(statusCode, statusMsg);
        System.out.println(new Gson().toJson(result));
        if ("000000".equals(statusCode)) {
            return result;
        } else {
            return result;
        }
    }
}
