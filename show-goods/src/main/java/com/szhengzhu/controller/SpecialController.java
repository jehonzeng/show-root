package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.SpecialService;

@RestController
@RequestMapping(value = "specials")
public class SpecialController {

    @Resource
    private SpecialService specialService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> save(@RequestBody SpecialInfo base) {
        return specialService.addSpecial(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SpecialInfo>> page(@RequestBody PageParam<SpecialInfo> base) {
        return specialService.getSpecialPage(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> edit(@RequestBody SpecialInfo base) {
        return specialService.editSpecial(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> specialInfo(@PathVariable("markId") String markId) {
        return specialService.sepcialInfoById(markId);
    }

    @RequestMapping(value = "/addBatchByColumn", method = RequestMethod.POST)
    public Result<?> addItemBatchByGroup(@RequestBody SpecialBatchVo base) {
        return specialService.addItemBatchByColumn(base);
    }

    @RequestMapping(value = "/addBatchByLabel", method = RequestMethod.POST)
    public Result<?> addItemBatchByLabel(@RequestBody SpecialBatchVo base) {
        return specialService.addItemBatchByLabel(base);
    }

    @RequestMapping(value = "/item/delete", method = RequestMethod.POST)
    public Result<?> deleteItem(@RequestBody SpecialItem base) {
        return specialService.deleteItem(base);
    }

    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<SpecialGoodsVo>> getItemPage(@RequestBody PageParam<SpecialItem> base) {
        return specialService.getItemPage(base);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<?> listSpecialById(@RequestParam("goodsId") String goodsId){
        return specialService.listSpecialByGoods(goodsId);
    }
    
    @RequestMapping(value ="/item/add",method=RequestMethod.POST)
    public Result<?> addSpecialItem(@RequestBody SpecialItem base){
        return specialService.addSpecialItem(base);
    }
    
    @RequestMapping(value = "goods/addBatch", method = RequestMethod.POST)
    public Result<?> addBatchSpecialGoods(@RequestBody BatchVo base){
        return specialService.addBatchGoods(base);
    }

}
