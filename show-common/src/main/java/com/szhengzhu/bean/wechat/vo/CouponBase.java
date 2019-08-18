package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class CouponBase implements Serializable {
    
    private static final long serialVersionUID = 1701792913783236163L;
    
    private String couponId;
    
    private String couponName;
    
    private Date startTime;
    
    private Date stopTime;
    
    private BigDecimal limitPrice;
    
    private BigDecimal price;
    
    private BigDecimal discount;
    
    private Integer serverStatus;

}
