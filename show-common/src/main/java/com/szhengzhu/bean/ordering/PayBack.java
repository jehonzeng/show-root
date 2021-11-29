package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PayBack implements Serializable {
    
    private static final long serialVersionUID = -2258007579387332721L;

    private String markId;

    private String indentId;

    private String backInfo;

    private Integer backType;

    private Date addTime;

    private String userId;
}