package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.FoodsService;
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
@RequestMapping(value = "foods")
public class FoodController {

    @Resource
    private FoodsService foodsService;

    @PostMapping(value = "/add")
    public FoodsInfo addFood(@RequestBody @Validated FoodsInfo base) {
        return foodsService.addFoodsInfo(base);
    }

    @PatchMapping(value = "/edit")
    public FoodsInfo modifyFood(@RequestBody @Validated FoodsInfo base) {
        return foodsService.modifyFoodsInfo(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<FoodsInfo> page(@RequestBody PageParam<FoodsInfo> base) {
        return foodsService.getPage(base);
    }

    @GetMapping(value = "/{markId}")
    public FoodsInfo foodsInfo(@PathVariable("markId") @NotBlank String markId) {
        return foodsService.getFoodsInfo(markId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> comboboxList(@RequestParam("goodsId") @NotBlank String goodsId) {
        return foodsService.listFoodWithoutGoods(goodsId);
    }

    @PostMapping(value = "/item/batch")
    public void addBatchItem(@RequestBody @Validated FoodsItem base) {
        foodsService.addBatchItem(base);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<GoodsFoodVo> itemPage(@RequestBody PageParam<FoodsItem> base) {
        return foodsService.getItemPage(base);
    }

    @DeleteMapping(value = "/item/{markId}")
    public void deleteItem(@PathVariable("markId") @NotBlank String markId) {
        foodsService.deleteItem(markId);
    }

    @PatchMapping(value ="/item/modify")
    public FoodsItem updateFoodsItem(@RequestBody @Validated FoodsItem base){
       return foodsService.updateFoodsItem(base);
    }

    @GetMapping(value ="/foodCombobox")
    public List<Combobox> listFood(){
        return foodsService.getFoodCombobox();
    }

}
