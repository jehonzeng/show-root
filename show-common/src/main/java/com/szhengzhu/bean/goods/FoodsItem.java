package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class FoodsItem implements Serializable{
  
    private static final long serialVersionUID = 580560748681408897L;

    private String markId;

    private String foodId;

    private String goodsId;
    
    private String specificationIds;

    private BigDecimal useSize;
    
    private Boolean serverStatus;

}