package com.szhengzhu.bean.activity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityRule implements Serializable {
   
    private static final long serialVersionUID = -7330864768052126716L;

    private String markId;

    private Integer follow;

    private String initiatorLimit;

    private String limited;

    private String helperLimit;
}