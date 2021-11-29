package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.MealImage;
import com.szhengzhu.service.MealImageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "mImages")
public class MealImageController {
    
    @Resource
    private MealImageService mealImageService;
    
    @PostMapping(value="/add")
    public MealImage addMealImage(@RequestBody MealImage base) {
        return mealImageService.addMealImage(base);
    }

    @PatchMapping(value = "/edit")
    public MealImage modifyMealImage(@RequestBody MealImage base) {
        return mealImageService.modifyMealImage(base);
    }

    @DeleteMapping(value = "/{markId}")
    public void deleteMealImage(@PathVariable("markId") @NotBlank String markId) {
        mealImageService.deleteMealImage(markId);
    }

    @GetMapping(value = "/list/{mealId}")
    public Map<String, Object> getMealImages(@PathVariable("mealId") @NotBlank String mealId,
                                             @RequestParam("serverType") @NotNull Integer serverType) {
        return mealImageService.getMealImageList(mealId, serverType);
    }
    
    @GetMapping(value = "/{markId}")
    public MealImage getMealImageInfo(@PathVariable("markId") @NotBlank String markId) {
        return mealImageService.getImageInfo(markId);
    }
}
