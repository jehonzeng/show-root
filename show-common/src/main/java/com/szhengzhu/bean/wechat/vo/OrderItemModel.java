package com.szhengzhu.bean.wechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemModel implements Serializable {

    private static final long serialVersionUID = 1903333121514619570L;

    private String productId;
    
    private Integer productType;
    
    private String productName;
    
    private String specificationIds;
    
    /** 总数量  包括使用了菜品券商品的数量和失效数量 */
    private Integer quantity = 1; 
    
    /** 失效数量 */
    private Integer invalidCount = 0;
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    /** 使用菜品券数量  */
    private Integer useVoucherCount = 0; 
    
    /** 0:正常  1：不可配送  2：库存不足  3：该商品已下架 */
    private Integer status = 0; 
    
    private String storehouseId;

    /* 过渡字段 */
    private List<String> voucherIds;
}
