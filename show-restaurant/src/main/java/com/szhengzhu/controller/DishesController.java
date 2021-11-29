package com.szhengzhu.controller;

import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.bean.member.DishesImage;
import com.szhengzhu.bean.member.DishesInfo;
import com.szhengzhu.bean.member.DishesStage;
import com.szhengzhu.bean.member.vo.DishesImageVo;
import com.szhengzhu.bean.member.vo.DishesStageVo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/v1/dishes")
@Api(tags = "菜品活动信息：DishesController")
public class DishesController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "查询菜品信息")
    @PostMapping(value = "/queryDishes")
    public Result<PageGrid<DishesInfo>> queryDishes(@RequestBody PageParam<DishesInfo> param) {
        return showMemberClient.queryDishes(param);
    }

    @ApiOperation(value = "添加菜品信息")
    @PostMapping(value = "/info")
    public Result addDishes(HttpSession session, @RequestBody @Validated DishesInfo dishesInfo) {
        dishesInfo.setCreator((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.addDishes(dishesInfo);
    }

    @ApiOperation(value = "修改菜品信息")
    @PatchMapping(value = "/info")
    public Result modifyDishes(HttpSession session, @RequestBody DishesInfo dishesInfo) {
        dishesInfo.setModifier((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.modifyDishes(dishesInfo);
    }

    @ApiOperation(value = "修改菜品信息状态")
    @PatchMapping(value = "/info/{markId}")
    public Result modifyDishesStatus(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.modifyDishesStatus(markId);
    }

    @ApiOperation(value = "查询菜品图片")
    @PostMapping(value = "/imageInfo")
    public Result<List<DishesImage>> queryImage(@RequestBody @Validated DishesImageVo image) {
        return showMemberClient.queryImage(image);
    }

    @ApiOperation(value = "添加菜品图片信息")
    @PostMapping(value = "/image")
    public Result addImage(@RequestBody DishesImageVo image) {
        return showMemberClient.addImage(image);
    }

    @ApiOperation(value = "修改菜品图片信息")
    @PatchMapping(value = "/image")
    public Result modifyImage(@RequestBody DishesImageVo image) {
        return showMemberClient.modifyImage(image);
    }

    @ApiOperation(value = "查询菜品阶段信息")
    @PostMapping(value = "/stageInfo")
    public Result<List<DishesStage>> queryStage(@RequestBody DishesStageVo stage) {
        return showMemberClient.queryStage(stage);
    }

    @ApiOperation(value = "添加菜品阶段信息")
    @PostMapping(value = "/stage")
    public Result addStage(@RequestBody @Validated DishesStageVo stage) {
        return showMemberClient.addStage(stage);
    }

    @ApiOperation(value = "修改菜品阶段信息")
    @PatchMapping(value = "/stage")
    public Result modifyStage(@RequestBody DishesStageVo stage) {
        return showMemberClient.modifyStage(stage);
    }


}
