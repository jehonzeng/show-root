package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class TableStatus implements Serializable {

    private static final long serialVersionUID = -6773052975106519607L;

    private String markId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String storeId;

    private String color;

    private Integer sort;

    private Date createTime;

    private Date modifyTime;

    private Integer status;
}