package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

/**
 * 套餐单品信息
 * 
 * @author Administrator
 * @date 2019年4月17日
 */
@Data
public class MealItem implements Serializable {
    
    private static final long serialVersionUID = -2839488330135879702L;

    private String markId;

    private String mealId;

    private String goodsId;
    
    private String specificationIds;

    private Integer quantity;

    private Integer sort;

}