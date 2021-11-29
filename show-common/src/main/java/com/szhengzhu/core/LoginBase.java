package com.szhengzhu.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class LoginBase implements Serializable {

    private static final long serialVersionUID = -5759222392746110829L;

    /** 过期时间 */
    private int overTime = 1 * 60 * 1000;

    private String phone;
    
    private String markId;
    
    private String code;

    /** 验证码位数*/
    private int digit = 6;

    /** 用来计数的，防止用户胡乱请求短信验证码 */
    private int count = 0;

    private Date createTime = DateUtil.date();

    public LoginBase() {}

    public LoginBase(String phone) {
        this.phone = phone;
        this.code = RandomUtil.randomNumbers(digit);
    }

    /**
     * 是否超时
     * 
     * @date 2019年2月21日 上午11:48:24
     * @return
     */
    public boolean isOver() {
        return System.currentTimeMillis() - createTime.getTime() >= overTime;
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
        return !StrUtil.isBlank(phone) && !StrUtil.isBlank(code) && phone.equals(this.phone) && code.equals(this.code);
    }

    /**
     * 判断是否频繁请求
     * 
     * @date 2019年2月21日 上午11:51:00
     * @param times
     * @return
     */
    public boolean isOften(int times) {
        return count > times;
    }

    /**
     * 刷新
     * 
     * @date 2019年2月21日 上午11:50:12
     * @param phone
     */
    public void refresh(String phone) {
        // 手机号码换了或者过期了短信验证码刷新
        if (!this.phone.equals(phone) || isOver()) {
            this.phone = phone;
            this.code = RandomUtil.randomNumbers(this.digit);
        }
        createTime = DateUtil.date();
        count++;
    }

}
