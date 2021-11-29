package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SpecsItem implements Serializable {
    
    private static final long serialVersionUID = 4651308674616571277L;

    private String markId;

    @NotBlank
    private String specsId;

    @NotBlank
    private String name;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}