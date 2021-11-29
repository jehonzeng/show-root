package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TypeSpec implements Serializable {
    
    private static final long serialVersionUID = 1394110464523258379L;

    @NotEmpty
    private String typeId;

    @NotEmpty
    private String specificationId;
    
    private Integer sort;
    
    private Boolean defaultOr;
}