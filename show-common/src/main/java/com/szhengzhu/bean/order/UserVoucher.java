package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class UserVoucher implements Serializable {
    
    private static final long serialVersionUID = 8450938923316760299L;

    private String markId;

    private String userId;

    private String voucherId;
    
    private String voucherName;

    private String productId;

    private Integer productType;

    private String specificationIds;

    private Integer orderType;

    private String orderNo;

    private Date createTime;

    private Integer quantity;

    private Date useTime;
    
    private BigDecimal salePrice;
    
    private String specs;
}