package com.szhengzhu.bean.ordering.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class TableParam implements Serializable {

    private static final long serialVersionUID = -934118473843649714L;

    private String code;
    
    private String name;
    
    private String clsId;
    
    private String areaId;
    
    private String storeId;
}
