package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class StockBase implements Serializable {

    private static final long serialVersionUID = -556738521274182393L;

    private String goodsId;
    
    private String specificationIds;
    
    private String specs;
    
    private String goodsNo;
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
//    private String goodsImage;
    
    private Boolean isDelivery = false;
    
    private Integer currentStock = 100;
}
