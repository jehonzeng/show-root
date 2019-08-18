package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDetail implements Serializable {

    private static final long serialVersionUID = 2158226756644665895L;
    
    private String productId;

    private String productName;
    
    private Integer productType;
    
    private String increaseId;
    
    private String specificationIds;
    
    private String specs;
    
    private BigDecimal salePrice;
    
    private BigDecimal payAmount;
    
    private Integer quantity;
    
//    private String goodsImage;
    
    private Boolean isVoucher;
}
