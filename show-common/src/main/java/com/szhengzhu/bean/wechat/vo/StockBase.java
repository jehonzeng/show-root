package com.szhengzhu.bean.wechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockBase implements Serializable {

    private static final long serialVersionUID = -556738521274182393L;

    private String goodsId;
    
    private String specificationIds;
    
    private String specs;
    
    private String goodsNo;
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    private Boolean isDelivery = false;
    
    private Integer currentStock = 100;
    
    private String storehouseId;
}
