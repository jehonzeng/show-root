package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.MealImage;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.mapper.MealImageMapper;
import com.szhengzhu.service.MealImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("mealImageService")
public class MealImageServiceImpl implements MealImageService {

    @Resource
    private MealImageMapper mealImageMapper;

    @CheckGoodsChange
    @Override
    public MealImage addMealImage(MealImage base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        mealImageMapper.insertSelective(base);
        return base;
    }

    @CheckGoodsChange
    @Override
    public MealImage modifyMealImage(MealImage base) {
        mealImageMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public void deleteMealImage(String markId) {
        mealImageMapper.deleteByPrimaryKey(markId);
    }

    @Override
    public Map<String,Object> getMealImageList(String mealId, Integer type) {
        Map<String,Object> map = new HashMap<>(4);
        List<MealImage> list = mealImageMapper.selectListByTypeAndId(mealId,type);
        map.put("infos", list);
        map.put("imageServer",Contacts.IMAGE_SERVER);
        return map;
    }

    @Override
    public MealImage getImageInfo(String markId) {
        return mealImageMapper.selectByPrimaryKey(markId);
    }
}
