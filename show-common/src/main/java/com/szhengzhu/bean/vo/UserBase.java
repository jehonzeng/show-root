package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserBase implements Serializable{
    
    private static final long serialVersionUID = 2378226003967438967L;

    private String markId;
    
    private String nickName;
    
    private String phone;
    
    private String roleName;
    
    private String userLevel;
    
    private String wopenId;
    
    private String xopenId;
    
    private String unionId;
    
    private String wechatStatus;

}
