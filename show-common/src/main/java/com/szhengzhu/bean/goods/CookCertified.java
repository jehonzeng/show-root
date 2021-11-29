package com.szhengzhu.bean.goods;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class CookCertified implements Serializable {

    private static final long serialVersionUID = -3601061132102147160L;

    private String markId;

    private String userId;

    @NotBlank
    private String shortName;

    private String cookStyle;

    /** 关联等级 */
    private String cookLevel;

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