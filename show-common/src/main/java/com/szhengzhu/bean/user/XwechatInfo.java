package com.szhengzhu.bean.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户微信小程序信息
 * 
 * @author Administrator
 * @date 2019年2月19日
 */
@Data
public class XwechatInfo implements Serializable {

    private static final long serialVersionUID = 8215779938451156407L;

    private String markId;
    
    private String openId;

    private String nickName;

    private String headerImg;

    private Date createTime;
}