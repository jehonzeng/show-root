package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class SeckillInfo implements Serializable {

    private static final long serialVersionUID = -1536861336341411683L;

    private String markId;

    private String productId;

    private Integer productType;
    
    private String productName;

    private String specificationIds;

    private String theme;

    private BigDecimal price;

    private Date startTime;

    private Date stopTime;

    private Boolean free;

    private Boolean serverStatus;
    
    private String specifications;
}