package com.szhengzhu.bean.vo;

import java.math.BigDecimal;

import com.szhengzhu.bean.goods.GoodsStock;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StockVo extends GoodsStock{

    private static final long serialVersionUID = 1703832936054891527L;
    
    private String attrList;//属性集
    
    private String goodsName;
    
    private String depotName;//所属仓库名称
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    private String specificationIds;
}
