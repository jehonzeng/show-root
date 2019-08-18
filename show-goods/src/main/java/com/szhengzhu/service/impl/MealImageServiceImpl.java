package com.szhengzhu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szhengzhu.bean.goods.MealImage;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.MealImageMapper;
import com.szhengzhu.service.MealImageService;
import com.szhengzhu.util.IdGenerator;

@Service("mealImageService")
public class MealImageServiceImpl implements MealImageService {
    
    @Autowired
    private MealImageMapper mealImageMapper;

    @Override
    public Result<?> addMealImage(MealImage base) {
        if(base == null)
            return new Result<>(StatusCode._4004);
        base.setMarkId(IdGenerator.getInstance().nexId());
        mealImageMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> midifyMealImage(MealImage base) {
        if(base == null || base.getMarkId()==null)
            return new Result<>(StatusCode._4004);
        mealImageMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> deleteMealImage(String markId) {
        mealImageMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }

    @Override
    public Result<?> getMealImageList(String mealId, Integer type) {
        Map<String,Object> map = new HashMap<>();
        List<MealImage> list = mealImageMapper.selectListByTypeAndId(mealId,type);
        map.put("infos", list);
        map.put("imageServer",Contacts.IMAGE_SERVER);
        return new Result<>(map);
    }

    @Override
    public MealImage getImageInfo(String markId) {
        return mealImageMapper.selectByPrimaryKey(markId);
    }

}
