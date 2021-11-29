package com.szhengzhu.bean.activity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 活动（或发起者）礼物表
 * 
 * @author Administrator
 * @date 2019年9月25日
 */
@Data
public class ActivityGift implements Serializable{

    private static final long serialVersionUID = 5652644521755120220L;

    private String markId;

    @NotBlank
    private String giftId;

    private String activityId;

    private Integer point;

    private Integer giftTotal;

    private Integer exchangeTotal;

    private Integer grantType;

    private BigDecimal payPrice;

    private String limitType;

    private Integer sort;
    
    private Integer partType;
    
    private String giftName;
    
    private Boolean serverStatus;
    
    private String limitTypeDesc;
    
    private String actName;

}