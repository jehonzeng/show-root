package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCoupon implements Serializable,Cloneable{
    
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
    
    private String limitTime;
    
    @Override
    public  UserCoupon clone(){
        UserCoupon userCoupon = null;
        try {
            userCoupon = (UserCoupon) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return userCoupon;
    }
}