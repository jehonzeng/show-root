package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class UserCoupon implements Serializable{
    
    private static final long serialVersionUID = -8781180480933079396L;

    private String markId;

    private String userId;
    
    private String templateId;

    private Integer serverStatus;

    private Date startTime;

    private Date stopTime;

    private Date useTime;

    private BigDecimal limitPrice;

    private BigDecimal couponPrice;

    private BigDecimal couponDiscount;

    private String couponName;

    private String linkId;

    private String linkName;

    private Integer couponType;
    
    private String userName;
    
    private String limitRegion;
    
    public UserCoupon() {}
    
    public UserCoupon(UserCoupon coupon) {
        this.markId = coupon.getMarkId();
        this.userId = coupon.getUserId();
        this.templateId = coupon.getTemplateId();
        this.serverStatus = coupon.getServerStatus();
        this.startTime = coupon.getStartTime();
        this.stopTime = coupon.getStopTime();
        this.useTime = coupon.getUseTime();
        this.limitPrice = coupon.getLimitPrice();
        this.couponPrice = coupon.getCouponPrice();
        this.couponDiscount = coupon.getCouponDiscount();
        this.couponName = coupon.getCouponName();
        this.linkId = coupon.getLinkId();
        this.linkName = coupon.getLinkName();
        this.couponType = coupon.getCouponType();
        this.userName = coupon.getUserName();
        this.limitRegion = coupon.getLimitRegion();
    }
}