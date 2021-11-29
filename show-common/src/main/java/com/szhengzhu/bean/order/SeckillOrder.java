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
public class SeckillOrder implements Serializable {

    private static final long serialVersionUID = 827419998487981155L;

    private String markId;

    private String orderNo;

    private String theme;

    private String goodsId;

    private String seckillId;

    private String userId;
    
    private String goodsName;
    
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

    private String orderStatus;
    
    private String storehouseId;
    
    private String nickName;
    
    private String statusDesc;
    
    private String specs;
    
    private String reason;
    
    private OrderDelivery orderDelivery;
}