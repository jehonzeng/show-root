package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommodityParam implements Serializable {

    private static final long serialVersionUID = 2751986572271643736L;
    
    private String code;

    private String name;

    private String storeId;

    private Integer type;

    private String introduction;

    private Integer status;
    
    private String cateId;
    
    private String userId;
}
