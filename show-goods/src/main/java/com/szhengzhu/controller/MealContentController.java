package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.MealContent;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.MealContentService;

@RestController
@RequestMapping(value = "mContents")
public class MealContentController {

    @Resource
    private MealContentService mealContentService;

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> editMealContent(@RequestBody MealContent base) {
        return mealContentService.editContent(base);
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.GET)
    public Result<?> getMealContent(@PathVariable("mealId") String mealId) {
        return mealContentService.getMealContent(mealId);
    }

}
