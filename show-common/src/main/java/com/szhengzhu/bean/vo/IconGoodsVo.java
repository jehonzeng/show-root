package com.szhengzhu.bean.vo;

import java.math.BigDecimal;

import com.szhengzhu.bean.goods.IconItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class IconGoodsVo extends IconItem{

    private static final long serialVersionUID = -8187813041983038540L;

    private String goodsName;
    
    private BigDecimal basePrice;
    
    private BigDecimal salePrice;
    
    private String description;

}
