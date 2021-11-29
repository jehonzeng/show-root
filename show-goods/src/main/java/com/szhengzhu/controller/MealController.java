package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MealService;
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
@RequestMapping(value = "/meals")
public class MealController {

    @Resource
    private MealService mealService;

    @PostMapping(value = "/add")
    public MealInfo save(@RequestBody @Validated MealInfo base) {
        return mealService.addMeal(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<MealInfo> page(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPage(base);
    }

    @PatchMapping(value = "/modify")
    public MealInfo edit(@RequestBody @Validated MealInfo base) {
        return mealService.editMeal(base);
    }

    @PostMapping(value = "/item/add")
    public MealItem saveItem(@RequestBody @Validated MealItem base) {
        return mealService.addItem(base);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<MealVo> itemPage(@RequestBody PageParam<MealItem> base) {
        return mealService.getItemPage(base);
    }

    @PatchMapping(value = "/item/modify")
    public MealItem edit(@RequestBody @Validated MealItem base) {
        return mealService.editMealItem(base);
    }

    @GetMapping(value = "/{markId}")
    public MealInfo getMealById(@PathVariable("markId") @NotBlank String markId) {
        return mealService.getMealById(markId);
    }

    @GetMapping(value = "/item/{markId}")
    public MealItem getMealItemById(@PathVariable("markId") @NotBlank String markId) {
        return mealService.getMealItemById(markId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> getMealList() {
        return mealService.getMealList();
    }

    @PostMapping(value = "/column/page")
    public PageGrid<MealInfo> getPageByColumn(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPageByColumn(base);
    }

    @PostMapping(value = "/label/page")
    public PageGrid<MealInfo> getPageByLabel(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPageByLabel(base);
    }

    @PostMapping(value = "/special/page")
    public PageGrid<MealInfo> getMealPageBySpecial(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPageBySpecial(base);
    }

    @PostMapping(value = "/icon/page")
    public PageGrid<MealInfo> getMealPageByIcon(PageParam<MealInfo> base) {
        return mealService.getPageByIcon(base);
    }

    @GetMapping(value = "/fore/detail")
    public GoodsDetail getMealDetail(@RequestParam("mealId") @NotBlank String mealId,
                                     @RequestParam(value = "userId", required = false) String userId) {
        return mealService.getMealDetail(mealId, userId);
    }

    @GetMapping(value = "/servesIn")
    public List<String> serverListInMeal(@RequestParam("mealId") @NotBlank String mealId) {
        return mealService.getServerListInMeal(mealId);
    }

    @PostMapping(value = "/addBatchServe")
    public void addBatchMealServe(@RequestBody BatchVo base) {
        mealService.addBatchMealServe(base);
    }

    @PostMapping(value = "/deleteBatchServe")
    public void deleteBatchMealServe(@RequestBody BatchVo base) {
        mealService.deleteBatchMealServe(base);
    }
}
