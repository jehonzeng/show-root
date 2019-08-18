package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.MealService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping(value = "/meals")
public class MealController {

    @Resource
    private MealService mealService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> save(@RequestBody MealInfo base) {
        return mealService.addMeal(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> page(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPage(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> edit(@RequestBody MealInfo base) {
        return mealService.editMeal(base);
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public Result<?> saveItem(@RequestBody MealItem base) {
        return mealService.addItem(base);
    }

    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<MealVo>> itemPage(@RequestBody PageParam<MealItem> base) {
        return mealService.getItemPage(base);
    }

    @RequestMapping(value = "/item/modify", method = RequestMethod.PATCH)
    public Result<?> edit(@RequestBody MealItem base) {
        return mealService.editMealItem(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getMealById(@PathVariable("markId") String markId) {
        return mealService.getMealById(markId);
    }

    @RequestMapping(value = "/item/{markId}", method = RequestMethod.GET)
    public Result<?> getMealItemById(@PathVariable("markId") String markId) {
        return mealService.getMealItemById(markId);
    }

    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> getMealList() {
        return mealService.getMealList();
    }

    @RequestMapping(value = "/column/page", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getPageByColumn(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPageByColumn(base);
    }

    @RequestMapping(value = "/label/page", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getPageByLabel(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPageByLabel(base);
    }

    @RequestMapping(value = "/special/page", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getMealPageBySpecial(@RequestBody PageParam<MealInfo> base) {
        return mealService.getPageBySpecial(base);
    }

    @RequestMapping(value = "/icon/page", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> getMealPageByIcon(PageParam<MealInfo> base) {
        return mealService.getPageByIcon(base);
    }

    @RequestMapping(value = "/fore/detail", method = RequestMethod.GET)
    public Result<GoodsInfoVo> getMealDetail(@RequestParam("mealId") String mealId,
            @RequestParam(value = "userId", required = false) String userId) {
        return mealService.getMealDetail(mealId, userId);
    }
    
    @RequestMapping(value = "/servesIn", method = RequestMethod.GET)
    public Result<List<String>> serverListInMeal(@RequestParam("mealId") String mealId){
        return mealService.getServerListInMeal(mealId);
    }
    
    @RequestMapping(value = "/addBatchServe", method = RequestMethod.POST)
    public Result<?> addBatchMealServe(@RequestBody BatchVo base){
        return mealService.addBatchMealServe(base);
    }

    @RequestMapping(value = "/deleteBatchServe", method = RequestMethod.POST)
    public Result<?> deleteBatchMealServe(@RequestBody BatchVo base){
        return mealService.deleteBatchMealServe(base);
    }
    
    @RequestMapping(value = "/stock/info", method = RequestMethod.GET)
    public Result<?> getStockInfo(@RequestParam("mealId") String mealId, @RequestParam(value = "addressId", required = false) String addressId) {
        if (StringUtils.isEmpty(mealId))
            return new Result<>(StatusCode._4004);
        return mealService.getStockInfo(mealId, addressId);
    }
}
