package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class RefundBack implements Serializable {
    
    private static final long serialVersionUID = 1253989707225228476L;

    private String markId;

    private String orderNo;

    private String refundNo;
    
    private BigDecimal totalFee;

    private Integer refundStatus;

    private Date addTime;
    
    private Integer orderType;

    private String remark;
    
    private Integer orderRefund;
}