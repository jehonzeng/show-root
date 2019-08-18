package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.MealInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ColumnMealVo extends ColumnGoods{

    private static final long serialVersionUID = -2381005514754284391L;
    
    private MealInfo meal;

}
