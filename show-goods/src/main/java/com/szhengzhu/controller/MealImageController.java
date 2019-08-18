package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.MealImage;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.MealImageService;

@RestController
@RequestMapping(value = "mImages")
public class MealImageController {
    
    @Resource
    private MealImageService mealImageService;
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Result<?> addMealImage(@RequestBody MealImage base) {
        return mealImageService.addMealImage(base);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyMealImage(@RequestBody MealImage base) {
        return mealImageService.midifyMealImage(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.DELETE)
    public Result<?> deleteMealImage(@PathVariable("markId") String markId) {
        return mealImageService.deleteMealImage(markId);
    }

    @RequestMapping(value = "/list/{mealId}", method = RequestMethod.GET)
    public Result<?> getMealImages(@PathVariable("mealId") String mealId,
            @RequestParam("serverType") Integer serverType) {
        return mealImageService.getMealImageList(mealId, serverType);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public MealImage getMealImageInfo(@PathVariable("markId")String markId) {
        return mealImageService.getImageInfo(markId);
    }

}
