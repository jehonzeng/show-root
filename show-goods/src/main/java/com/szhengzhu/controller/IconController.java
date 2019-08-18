package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.IconService;

@RestController
@RequestMapping(value = "icons")
public class IconController {
    
    @Resource
    private IconService iconService;
    
    @RequestMapping(value = "/add" ,method =RequestMethod.POST)
    public Result<?> add(@RequestBody IconInfo base){
        return iconService.addIcon(base);
    }
    
    @RequestMapping(value = "/update" ,method =RequestMethod.PATCH)
    public Result<?> modify(@RequestBody IconInfo base){
        return iconService.modifyIcon(base);
    }
    
    @RequestMapping(value = "/page" ,method =RequestMethod.POST)
    public Result<PageGrid<IconInfo>> getPage(@RequestBody PageParam<IconInfo> base){
        return iconService.getPage(base);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getIconInfo(@PathVariable("markId") String markId){
        return iconService.getInconById(markId);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<?> listIconByGoods(@RequestParam("goodsId") String goodsId){
        return iconService.getIconByGoods(goodsId);
    }
    
    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<IconGoodsVo>> iconItemPage(@RequestBody PageParam<IconItem> base){
        return iconService.getItemPage(base);
    }

    @RequestMapping(value = "/item/delete", method = RequestMethod.POST)
    public Result<?> deleteIconItem(@RequestBody IconItem base){
        return iconService.deleteItem(base);
    }
    
    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public Result<?> addIconItem(@RequestBody IconItem base){
        return iconService.addItem(base);
    }

    @RequestMapping(value = "/goods/addBatch", method = RequestMethod.POST)
    public Result<?> addBatchIconGoods(@RequestBody BatchVo base){
        return iconService.addBatchGoods(base);
    }
    
}
