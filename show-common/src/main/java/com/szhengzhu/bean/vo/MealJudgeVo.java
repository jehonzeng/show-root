package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.order.OrderDelivery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MealJudgeVo extends MealJudge{

    private static final long serialVersionUID = 2177493397285325954L;

    private String mealName;//套餐名称
    
    private OrderDelivery delivery;


}
