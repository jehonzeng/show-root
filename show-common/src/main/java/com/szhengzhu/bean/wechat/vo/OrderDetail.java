package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    
    private String statusDesc;
    
    private String contact;
    
    private String phone;
    
    private String address;
    
    private BigDecimal discount;
    
    private String remark;
    
    private String type; // 1:普通订单 2：团购订单 3：秒杀订单
    
    private List<OrderItemDetail> items;
}
