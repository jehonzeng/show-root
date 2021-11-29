package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class NavItemBase implements Serializable {

    private static final long serialVersionUID = -6038002154890621622L;

    private String theme;
    
    private String serverType;
    
    private String linkUrl;
    
    private String imagePath;
    
    private String description;
    
    private Integer showType;
}
