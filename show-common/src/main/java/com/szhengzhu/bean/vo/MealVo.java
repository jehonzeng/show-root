package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.MealItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealVo extends MealItem {

    private static final long serialVersionUID = 2057880776037280010L;
    
    private String mealName;

    private String goodsName;

    private String specList;

}
