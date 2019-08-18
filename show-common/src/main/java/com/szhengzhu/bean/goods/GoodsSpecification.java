package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class GoodsSpecification implements Serializable {
    
    private static final long serialVersionUID = 3144188292649419371L;

    private String markId;

    private String goodsId;
    
    private String specificationIds;

    private Boolean serverStatus;

    private BigDecimal basePrice;

    private BigDecimal salePrice;
    
    private String specs;
    
    private String goodsNo;
}