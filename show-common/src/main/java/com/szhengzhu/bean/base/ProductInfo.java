package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductInfo implements Serializable{
 
    private static final long serialVersionUID = 2033963186740201755L;

    private String markId;

    @NotBlank
    private String name;

    private Boolean serverStatus;

    private Integer serverType;

    private Date addTime;

    private String material;

    private String imgPath;

    private String description;

    private Integer sort;

}