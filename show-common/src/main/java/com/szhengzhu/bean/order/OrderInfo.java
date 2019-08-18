package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = -97351129900641548L;

    private String markId;

    private String orderNo;

    private String userId;

    private BigDecimal orderAmount;

    private BigDecimal deliveryAmount;

    private BigDecimal payAmount;

    private Date orderTime;

    private Date payTime;

    private Date cancelTime;

    private Date deliveryDate;

    private Date sendTime;

    private Date arriveTime;

    private String remark;

    private Integer orderType;

    private String orderStatus;

    private String couponId;

    private String orderSource;

    private String nickName;

    private String statusDesc;

    private OrderDelivery orderDelivery;

    private List<OrderItem> items;
}