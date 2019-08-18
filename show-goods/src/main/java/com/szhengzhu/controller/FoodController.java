package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.FoodsService;

@RestController
@RequestMapping(value = "foods")
public class FoodController {

    @Resource
    private FoodsService foodsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addFood(@RequestBody FoodsInfo base) {
        return foodsService.addFoodsInfo(base);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyFood(@RequestBody FoodsInfo base) {
        return foodsService.modifyFoodsInfo(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<FoodsInfo>> page(@RequestBody PageParam<FoodsInfo> base) {
        return foodsService.getPage(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> foodsInfo(@PathVariable("markId") String markId) {
        return foodsService.getFoodsInfo(markId);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> comboboxList(@RequestParam("goodsId") String goodsId) {
        return foodsService.listFoodWithoutGoods(goodsId);
    }

    @RequestMapping(value = "/item/batch", method = RequestMethod.POST)
    public Result<?> addBatchItem(@RequestBody FoodsItem base) {
        return foodsService.addBatchItem(base);
    }

    @RequestMapping(value = "/item/page",method = RequestMethod.POST)
    public Result<PageGrid<GoodsFoodVo>> itemPage(@RequestBody PageParam<FoodsItem> base) {
        return foodsService.getItemPage(base);
    }
    
    @RequestMapping(value = "/item/{markId}",method = RequestMethod.DELETE)
    public Result<?> deleteItem(@PathVariable("markId")String markId) {
        return foodsService.deleteItem(markId);
    }
    
    @RequestMapping(value ="/item/modify",method = RequestMethod.PATCH)
    public Result<?> updateFoodsItem(@RequestBody FoodsItem base){
       return foodsService.updateFoodsItem(base);
    }
    
    @RequestMapping(value ="/foodCombobox",method = RequestMethod.GET)
    public Result<List<Combobox>> listFood(){
        return foodsService.getFoodCombobox();
    }
    
}
