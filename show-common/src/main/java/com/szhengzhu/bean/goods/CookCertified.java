package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

@Data
public class CookCertified implements Serializable {

    private static final long serialVersionUID = -3601061132102147160L;

    private String markId;

    private String userId;

    private String shortName;

    private String cookStyle;
    
    private String cookLevel;//关联等级

    private String phone;

    private String province;

    private String city;

    private String address;

    private Boolean certified;
    
    private String userName;  
    
    private String cookStyleDesc;
    
    private String imagePath;
    
    private String description;
    
    private String personalSignature;
    
    private String cookLevelName;
}