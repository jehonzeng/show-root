package com.szhengzhu.bean.base;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统属性表
 * 
 * @author Administrator
 * @date 2019年2月25日
 */
@Data
public class AttributeInfo implements Serializable {

    private static final long serialVersionUID = 5728667740085466506L;

    private String markId;

    @NotBlank
    private String code;

    private Boolean serverStatus;

    private String type;

    @NotBlank
    private String name;

    private String father;
    
    private String description;

    private Integer sort;
}