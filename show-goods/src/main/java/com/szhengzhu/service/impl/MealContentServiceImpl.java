package com.szhengzhu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szhengzhu.bean.goods.MealContent;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.MealContentMapper;
import com.szhengzhu.service.MealContentService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("mealContentService")
public class MealContentServiceImpl implements MealContentService {

    @Autowired
    private MealContentMapper mealContentMapper;

    @Override
    public Result<?> editContent(MealContent base) {
        if(base == null || StringUtils.isEmpty(base.getMealId()))
            return new Result<>(StatusCode._4004);
        MealContent beforeInfo = mealContentMapper.selectByMealId(base.getMealId());
        base.setMarkId(beforeInfo.getMarkId());
        mealContentMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> getMealContent(String mealId) {
        if(StringUtils.isEmpty(mealId))
            return new Result<>(StatusCode._4008);
        MealContent content = mealContentMapper.selectByMealId(mealId);
        if (content == null) {
            content = new MealContent();
            content.setMarkId(IdGenerator.getInstance().nexId());
            content.setContent("");
            content.setMealId(mealId);
            mealContentMapper.insertSelective(content);
        }
        return new Result<>(content);
    }

}
