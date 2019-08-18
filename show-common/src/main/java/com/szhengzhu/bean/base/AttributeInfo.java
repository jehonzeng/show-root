package com.szhengzhu.bean.base;

import java.io.Serializable;

import lombok.Data;

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

    private String code;

    private Boolean serverStatus;

    private String type;

    private String name;

    private String father;

    private Integer sort;
}