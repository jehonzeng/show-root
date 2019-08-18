package com.szhengzhu.core;

import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.TimeUtils;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginBase implements Serializable {

    private static final long serialVersionUID = -5759222392746110829L;

    private int over_time = 10 * 60 * 1000;// 过期时间

    private String phone;
    
    private String markId;

    private String code;

    private int count = 0;// 用来计数的，防止用户胡乱请求短信验证码

    private Date create_time = TimeUtils.today();

    public LoginBase(String phone, int digit) {
        this.phone = phone;
        this.code = ShowUtils.random(digit).toString();
    }

    /**
     * 是否超时
     * 
     * @date 2019年2月21日 上午11:48:24
     * @return
     */
    public boolean isOver() {
        return System.currentTimeMillis() - create_time.getTime() >= over_time;
    }

    /**
     * 匹配
     * 
     * @date 2019年2月21日 上午11:48:57
     * @param phone
     * @param code
     * @return
     */
    public boolean equals(String phone, String code) {
        return phone.equals(this.phone) && code.equals(this.code);
    }

    /**
     * 判断是否频繁请求
     * 
     * @date 2019年2月21日 上午11:51:00
     * @param digit
     * @return
     */
    public boolean isOften(int digit) {
        return count > digit;
    }

    /**
     * 刷新
     * 
     * @date 2019年2月21日 上午11:50:12
     * @param phone
     */
    public void refresh(String phone) {
        if (!this.phone.equals(phone) || isOver()) { // 手机号码换了或者过期了短信验证码刷新
            this.phone = phone;
            this.code = ShowUtils.random(code.length()).toString();
        }
        create_time = TimeUtils.today();
        count++;
    }

}
