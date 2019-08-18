package com.szhengzhu.bean.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 微信公众号用户信息
 * 
 * @author Administrator
 * @date 2019年2月19日
 */
@Data
public class WechatInfo implements Serializable {

    private static final long serialVersionUID = -1856070994450899929L;

    private String markId;

    private String openId;

    private String nickName;

    private String headerImg;

    private String source;

    private Integer wechatStatus;
}