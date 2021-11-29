package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.IconService;
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
@RequestMapping(value = "icons")
public class IconController {
    
    @Resource
    private IconService iconService;
    
    @PostMapping(value = "/add")
    public IconInfo add(@RequestBody @Validated IconInfo base) {
        return iconService.addIcon(base);
    }
    
    @PatchMapping(value = "/update")
    public IconInfo modify(@RequestBody @Validated IconInfo base) {
        return iconService.modifyIcon(base);
    }
    
    @PostMapping(value = "/page")
    public PageGrid<IconInfo> getPage(@RequestBody PageParam<IconInfo> base){
        return iconService.getPage(base);
    }
    
    @GetMapping(value = "/{markId}")
    public IconInfo getIconInfo(@PathVariable("markId") @NotBlank String markId){
        return iconService.getIconById(markId);
    }
    
    @GetMapping(value = "/combobox")
    public List<Combobox> listIconByGoods(@RequestParam("goodsId") @NotBlank String goodsId){
        return iconService.getIconByGoods(goodsId);
    }
    
    @PostMapping(value = "/item/page")
    public PageGrid<IconGoodsVo> iconItemPage(@RequestBody PageParam<IconItem> base){
        return iconService.getItemPage(base);
    }

    @PostMapping(value = "/item/delete")
    public void deleteIconItem(@RequestBody IconItem base){
        iconService.deleteItem(base);
    }
    
    @PostMapping(value = "/item/add")
    public void addIconItem(@RequestBody IconItem base){
        iconService.addItem(base);
    }

    @PostMapping(value = "/goods/addBatch")
    public void addBatchIconGoods(@RequestBody BatchVo base){
        iconService.addBatchGoods(base);
    }
    
}
