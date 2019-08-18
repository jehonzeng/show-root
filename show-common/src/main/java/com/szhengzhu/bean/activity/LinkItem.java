package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class LinkItem implements Serializable {

    private static final long serialVersionUID = 2456948075678751417L;

    private String markId;

    private BigDecimal price;

    private String superId;

    private String specificationIds;
    
    private String specifications;
    
    private String productName;
}