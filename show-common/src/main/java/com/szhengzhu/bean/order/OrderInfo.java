package com.szhengzhu.bean.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    
    private String managerName;
    
    private String managerId;

    private String nickName;

    private String statusDesc;
    
    private String reason;

    @NotNull
    private OrderDelivery orderDelivery;

    @NotEmpty
    private List<OrderItem> items;
}