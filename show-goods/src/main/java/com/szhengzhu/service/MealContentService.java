package com.szhengzhu.service;

import com.szhengzhu.bean.goods.MealContent;

public interface MealContentService {

    /**编辑套餐图文信息
     * @param base 
     * @date 2019年4月19日 下午4:04:23
     * @return
     */
    MealContent modifyContent(MealContent base);

    /**
     * 获取套餐图文编辑信息
     * @date 2019年4月19日 下午4:16:54
     * @param mealId
     * @return
     */
    MealContent getMealContent(String mealId);

}
