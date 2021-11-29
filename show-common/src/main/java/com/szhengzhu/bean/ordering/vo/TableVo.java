package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class TableVo implements Serializable {

    private static final long serialVersionUID = -7678561607381950042L;
    
    private String markId;

    private String code;

    private String name;

    private Integer seats;

    private String qrCode;

    private String clsName;
    
    private String areaName;
    
    private String tableStatus;
    
    private String statusName;
    
    private Integer status;
}
