package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

@Data
public class TypeSpec implements Serializable {
    
    private static final long serialVersionUID = 1394110464523258379L;

    private String typeId;

    private String specificationId;
    
    private Integer sort;
    
    private Boolean defaultOr;
}