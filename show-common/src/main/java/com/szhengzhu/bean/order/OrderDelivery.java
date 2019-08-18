package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

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

    private String deliveryType;

    private Integer orderType;
    
    private String trackNo;
    
    private String orderNo;
    
    private Date orderTime;
    
    private Date sendTime;
    
    private Date arriveTime;
    
    private String orderStatus;
    
    private String statusDesc;
    
    private String deliveryTypeDesc;
}