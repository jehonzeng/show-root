package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.szhengzhu.util.StringUtils;

import lombok.Data;

@Data
public class LogInfo implements Serializable {

    private static final long serialVersionUID = -4603934853827158621L;

    private String markId; // 日志主键

    private String type; // 日志类型

    private String title; // 日志标题

    private String remoteAddr; // 请求地址

    private String requestUri; // URI

    private String method; // 请求方式

    private String params; // 提交参数

    private String exception; // 异常
    
    private Date addTime; // 开始时间

    private String timeout; // 结束用时

    private String userId; // 用户ID
    
    private String operator;
    
    private String resultParams;//返回结果

    /**
     * 设置参数
     *
     * @param paramMap
     * @date 2019年10月31日
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            if (params.length() > 0) {
                params.append("&");
            }
            params.append(param.getKey());
            params.append("=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0)
                    ? param.getValue()[0]
                    : "";
            params.append(StringUtils.abbr(paramValue, 100));
        }
        this.params = params.toString();
    }

}
