package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderBase implements Serializable {

    private static final long serialVersionUID = -735725660581605956L;
    
    private String orderId;
    
    private String orderNo;
    
    private String orderStatus;
    
    private Date orderTime;
    
    private Integer count;
    
    private String payAmount;
    
    private List<String> imagePath;
    
    private Integer type;  // 1:普通订单 2：团购订单  3：秒杀订单

}
