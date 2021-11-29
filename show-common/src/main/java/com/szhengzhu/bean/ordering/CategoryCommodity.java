package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryCommodity implements Serializable {
    
    private static final long serialVersionUID = -6325512395965232764L;

    @NotBlank
    private String categoryId;

    @NotBlank
    private String commodityId;
    
    private Integer sort;
}