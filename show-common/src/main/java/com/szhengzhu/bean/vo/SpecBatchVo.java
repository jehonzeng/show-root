package com.szhengzhu.bean.vo;

import java.util.Set;

import lombok.Data;
@Data
public class SpecBatchVo {
    
    private String typeId;
    
    private String attrName;
    
    private Boolean serverStatus;
    
    private Set<String> attrValues;

}
