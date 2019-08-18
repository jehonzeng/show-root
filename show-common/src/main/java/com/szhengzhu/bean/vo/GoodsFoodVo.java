package com.szhengzhu.bean.vo;

import java.math.BigDecimal;

import com.szhengzhu.bean.goods.FoodsItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsFoodVo extends FoodsItem{

    private static final long serialVersionUID = -8885104901232307829L;
    
    private String goodsName;
    
    private String foodName;

    private BigDecimal purchaseRate;

    private String unit;
    
    private String specList;
}
