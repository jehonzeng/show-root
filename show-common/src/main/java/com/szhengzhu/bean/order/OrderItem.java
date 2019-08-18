package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrderItem implements Serializable, Cloneable {

    private static final long serialVersionUID = -5785616632655884883L;

    private String markId;

    private String orderId;

    private String productId;

    private Integer productType;

    private String increaseId;

    private String productName;

    private String specificationIds;

    private Integer quantity;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private BigDecimal payAmount;

    private String voucherIds;
    
    private String storehouseId;

    private String specs;

    private String orderNo;

    private String statusDesc;

    private String orderStatus;

    private Date orderTime;

    private Date deliveryDate;

    private Date sendTime;

    @Override
    public OrderItem clone() throws CloneNotSupportedException {
        return (OrderItem) super.clone();
    }
}