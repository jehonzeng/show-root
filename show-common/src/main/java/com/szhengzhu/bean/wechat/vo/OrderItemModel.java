package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemModel implements Serializable {

    private static final long serialVersionUID = 1903333121514619570L;

    private String productId;
    
    private Integer productType;
    
    private String productName;
    
    private String specificationIds;
    
    private Integer quantity = 1; // 总数量  包括使用了菜品券商品的数量
    
    private Integer invalidCount = 0; // 失效数量
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    private Integer useVoucherCount = 0; // 使用菜品券数量
    
    private Integer status = 0; // 0:正常  1：不可配送  2：库存不足  3：该商品已下架
}
