package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 4370526058899760582L;
    
    private String orderId;

    private String orderNo;
    
    private Date orderTime;
    
    private Date expireTime;
    
    private BigDecimal deliveryAmount;
    
    private BigDecimal payAmount;
    
    private Date deliveryDate;
    
    private String orderStatus;
    
    private String contact;
    
    private String phone;
    
    private String address;
    
    private BigDecimal discount;
    
    private String remark;
    
    private List<OrderItemDetail> items;
}
