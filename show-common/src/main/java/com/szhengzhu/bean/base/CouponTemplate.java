package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CouponTemplate implements Serializable {

    private static final long serialVersionUID = -263088807836213786L;

    private String markId;

    private String couponName;

    private Integer couponTotal;//生成规则

    private Integer lineType;

    private Integer couponType;

    private BigDecimal couponPrice;

    private BigDecimal couponDiscount;

    private BigDecimal limitPrice;

    private Boolean serverStatus;

    private Integer limitCount;//限领取次数

    private Integer validityType;//生成规则

    private Date startTime;

    private Date stopTime;

    private Integer validityDay;//有效天数

    private Integer rangeType;//使用规则0全品1单品2部分

    private String rangeId;//关联商品或者类别

    private String description;
    
    private String rangeName;//指定商品或者名称
    
    private String limitRegion;
}