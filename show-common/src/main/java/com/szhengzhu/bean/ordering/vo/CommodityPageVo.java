package com.szhengzhu.bean.ordering.vo;

import java.math.BigDecimal;

import com.szhengzhu.bean.ordering.Commodity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommodityPageVo extends Commodity {

    private static final long serialVersionUID = -7700659561902680704L;

    private String imgId;
    
    private String unit;
    
    private String cates;
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    private BigDecimal memberPrice;
}
