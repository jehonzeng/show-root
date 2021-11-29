package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.bean.member.DishesImage;
import com.szhengzhu.bean.member.DishesInfo;
import com.szhengzhu.bean.member.DishesStage;
import com.szhengzhu.bean.member.vo.DishesImageVo;
import com.szhengzhu.bean.member.vo.DishesStageVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.DishesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/dishes")
public class DishesController {

    @Resource
    private DishesService dishesService;

    @PostMapping(value = "/info")
    public PageGrid<DishesInfo> queryDishes(@RequestBody PageParam<DishesInfo> param) {
        return dishesService.queryDishes(param);
    }

    @PostMapping(value = "/info/add")
    public void addDishes(@RequestBody @Validated DishesInfo dishesInfo) {
        dishesService.addDishes(dishesInfo);
    }

    @PatchMapping(value = "/info/modify")
    public void modifyDishes(@RequestBody DishesInfo dishesInfo) {
        dishesService.modifyDishes(dishesInfo);
    }

    @PatchMapping(value = "/info/{markId}")
    public void modifyDishesStatus(@PathVariable("markId") @NotBlank String markId) {
        dishesService.modifyDishes(DishesInfo.builder().markId(markId).status(0).modifyTime(DateUtil.date()).build());
    }

    @PostMapping(value = "/image")
    public List<DishesImage> queryImage(@RequestBody DishesImageVo image) {
        return dishesService.queryImage(image);
    }

    @PostMapping(value = "/image/add")
    public void addImage(@RequestBody @Validated DishesImageVo image) {
        dishesService.addImage(image);
    }

    @PatchMapping(value = "/image/modify")
    public void modifyImage(@RequestBody @Validated DishesImageVo image) {
        dishesService.modifyImage(image);
    }

    @PostMapping(value = "/stage")
    public List<DishesStage> queryStage(@RequestBody DishesStageVo stage) {
        return dishesService.queryStage(stage);
    }

    @PostMapping(value = "/stage/add")
    public void addStage(@RequestBody @Validated DishesStageVo stage) {
        dishesService.addStage(stage);
    }

    @PatchMapping(value = "/stage/modify")
    public void modifyStage(@RequestBody DishesStageVo stage) {
        dishesService.modifyStage(stage);
    }
}
