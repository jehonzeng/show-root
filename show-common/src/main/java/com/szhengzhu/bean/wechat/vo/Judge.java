package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Judge implements Serializable {

    private static final long serialVersionUID = 4683032093581750994L;

    private String productId;
    
    private String specificationIds;
    
    private Integer productType;
    
    private String productName;
    
    private String orderId;
    
    private Integer star;
    
    private String description;
}
