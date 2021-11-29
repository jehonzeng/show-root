package com.szhengzhu.bean.wechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Judge implements Serializable {

    private static final long serialVersionUID = 4683032093581750994L;

    private String productId;
    
    private String specificationIds;
    
    private Integer productType;
    
    private String productName;
    
    private String orderNo;
    
    private Integer star;
    
    private String description;
}
