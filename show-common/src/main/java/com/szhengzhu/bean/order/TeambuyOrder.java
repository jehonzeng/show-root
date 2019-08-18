package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TeambuyOrder implements Serializable {

    private static final long serialVersionUID = 6032734323159600243L;

    private String markId;

    private String orderNo;

    private String productId;
    
    private Integer productType;

    private String groupId;

    private String userId;
    
    private String productName;
    
    private String specificationIds;

    private Integer quantity;

    private BigDecimal orderAmount;

    private BigDecimal deliveryAmount;

    private BigDecimal payAmount;

    private Date orderTime;

    private Date cancelTime;

    private Date deliveryDate;
    
    private Date sendTime;

    private Date arriveTime;

    private String orderSource;

    private Integer sort;

    private String orderStatus;
    
    private String storehouseId;
    
    private String nickName;
    
    private String statusDesc;
    
    private String groupNo;
    
    private String specs;

    private String theme;
    
    private Integer teamStatus;
    
    private OrderDelivery orderDelivery;
}