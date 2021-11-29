package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class StoreMapVo implements Serializable {

    private static final long serialVersionUID = -7244254681149940157L;

    private String storeId;
    
    private String name;
    
    private Boolean defaults;
}
