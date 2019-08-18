package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ShoppingCart implements Serializable {
    
    private static final long serialVersionUID = -7613138170019915911L;

    private String markId;

    private String productId;

    private String userId;

    private Integer productType;

    private String productName;

    private String specificationIds;

    private Integer quantity;

    private BigDecimal basePrice;

    private BigDecimal addPrice;
    
    private BigDecimal salePrice;

    private Date addTime;

    private Date updateTime;

    private Boolean checked;
    
    private String productNo;
    
    private String specs;
    
    private Integer currentStock;
    
    private Integer status = 0;// 0:正常  1：不可配送  2：库存不足  3：该商品已下架
}