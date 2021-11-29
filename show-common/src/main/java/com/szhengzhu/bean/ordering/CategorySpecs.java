package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategorySpecs implements Serializable {
    
    private static final long serialVersionUID = -6388511582229956626L;

    @NotBlank
    private String categoryId;

    @NotBlank
    private String specsId;

    private Integer sort;
}