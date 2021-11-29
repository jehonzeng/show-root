package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PayType implements Serializable {

    private static final long serialVersionUID = -1845484065626232213L;

    private String markId;
    
    private String code;

    @NotBlank
    private String name;

    private String description;

    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Integer sort;

    private Integer status;
}