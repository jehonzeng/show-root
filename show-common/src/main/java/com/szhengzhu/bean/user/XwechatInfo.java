package com.szhengzhu.bean.user;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户微信小程序信息
 * 
 * @author Administrator
 * @date 2019年2月19日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class XwechatInfo implements Serializable {

    private static final long serialVersionUID = 8215779938451156407L;

    private String openId;

    private String nickName;

    private String headerImg;

    private Date createTime;
    
    private String source;
}