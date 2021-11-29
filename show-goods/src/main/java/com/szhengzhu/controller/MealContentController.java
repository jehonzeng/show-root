package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.MealContent;
import com.szhengzhu.service.MealContentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "mContents")
public class MealContentController {

    @Resource
    private MealContentService mealContentService;

    @PatchMapping(value = "/edit")
    public MealContent modifyContent(@RequestBody @Validated MealContent base) {
        return mealContentService.modifyContent(base);
    }

    @GetMapping(value = "/{mealId}")
    public MealContent getMealContent(@PathVariable("mealId") @NotBlank String mealId) {
        return mealContentService.getMealContent(mealId);
    }

}
