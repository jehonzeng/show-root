package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.MealContent;
import com.szhengzhu.mapper.MealContentMapper;
import com.szhengzhu.service.MealContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("mealContentService")
public class MealContentServiceImpl implements MealContentService {

    @Resource
    private MealContentMapper mealContentMapper;

    @CheckGoodsChange
    @Override
    public MealContent modifyContent(MealContent base) {
        MealContent old = mealContentMapper.selectByMealId(base.getMealId());
        base.setMarkId(old.getMarkId());
        base.setContent(base.getContent() == null ? "" : base.getContent());
        mealContentMapper.updateByPrimaryKeyWithBLOBs(base);
        return base;
    }

    @Override
    public MealContent getMealContent(String mealId) {
        MealContent content = mealContentMapper.selectByMealId(mealId);
        if (ObjectUtil.isNull(content)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            content = MealContent.builder().markId(snowflake.nextIdStr()).content("").mealId(mealId).build();
            mealContentMapper.insertSelective(content);
        }
        return content;
    }

}
