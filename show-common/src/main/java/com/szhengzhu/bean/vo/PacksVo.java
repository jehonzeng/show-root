package com.szhengzhu.bean.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.szhengzhu.bean.base.PacksItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacksVo extends PacksItem{

    private static final long serialVersionUID = -5246259393542516921L;

    private String couponName;
    
    private Integer couponType;
    
    private Integer serverType;
    
    private BigDecimal couponPrice;

    private BigDecimal couponDiscount;

    private BigDecimal limitPrice;
    
    private Date startTime;
    
    private Date stopTime;
    
    private int validityDay;
    
    private int limitCount;
}
