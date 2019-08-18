package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TeambuyInfo implements Serializable {

    private static final long serialVersionUID = 6431968880394900894L;

    private String markId;

    private String productId;

    private Integer productType;
    
    private String productName;

    private String theme;

    private Integer type;

    private Date startTime;

    private Date stopTime;

    private Integer reqCount;

    private Integer vaildTime;

    private BigDecimal price;

    private String shareUrl;

    private String limited;

    private Boolean free;

    private Boolean serverStatus;
    
    private String limitDesc;
    
    private List<LinkItem> items;
}