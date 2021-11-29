package com.szhengzhu.bean.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信公众号用户信息
 * 
 * @author Administrator
 * @date 2019年2月19日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WechatInfo implements Serializable {

    private static final long serialVersionUID = -1856070994450899929L;

    private String openId;

    private String nickName;

    private String headerImg;

    private String source;

    private Integer wechatStatus;
}