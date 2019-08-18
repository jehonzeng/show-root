package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.MealInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LabelMealVo extends LabelGoods{

    private static final long serialVersionUID = -7039333370749538329L;
    
    private MealInfo meal;

}
