package com.szhengzhu.bean.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDelivery implements Serializable {

    private static final long serialVersionUID = -245890485436851122L;

    private String markId;

    private String orderId;

    private String contact;

    private Date deliveryDate;

    private String phone;

    private String deliveryAddress;

    private String deliveryArea;
    
    private String province;
    
    private String city;
    
    private String area;

    private Double longitude;

    private Double latitude;

    private String remark;

    /** 物流公司 */
    private String deliveryType;

    private String orderType;

    /** 运单号 */
    private String trackNo;
    
    private Date addTime;
    
    private String orderNo;
    
    private Date orderTime;
    
    private Date sendTime;
    
    private Date arriveTime;
    
    private String orderStatus;
    
    private String statusDesc;
    
    private String deliveryTypeDesc;
    
    private String userId;
}