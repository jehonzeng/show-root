package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.SpecialService;
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
@RequestMapping(value = "specials")
public class SpecialController {

    @Resource
    private SpecialService specialService;

    @PostMapping(value = "/add")
    public SpecialInfo save(@RequestBody @Validated SpecialInfo base) {
        return specialService.addSpecial(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<SpecialInfo> page(@RequestBody PageParam<SpecialInfo> base) {
        return specialService.getSpecialPage(base);
    }

    @PatchMapping(value = "/modify")
    public SpecialInfo edit(@RequestBody @Validated SpecialInfo base) {
        return specialService.editSpecial(base);
    }

    @GetMapping(value = "/{markId}")
    public SpecialInfo specialInfo(@PathVariable("markId") @NotBlank String markId) {
        return specialService.specialInfoById(markId);
    }

    @PostMapping(value = "/addBatchByColumn")
    public List<SpecialItem> addItemBatchByGroup(@RequestBody SpecialBatchVo base) {
        return specialService.addItemBatchByColumn(base);
    }

    @PostMapping(value = "/addBatchByLabel")
    public List<SpecialItem> addItemBatchByLabel(@RequestBody SpecialBatchVo base) {
        return specialService.addItemBatchByLabel(base);
    }

    @PostMapping(value = "/item/delete")
    public void deleteItem(@RequestBody SpecialItem base) {
        specialService.deleteItem(base);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<SpecialGoodsVo> getItemPage(@RequestBody PageParam<SpecialItem> base) {
        return specialService.getItemPage(base);
    }
    
    @GetMapping(value = "/combobox")
    public List<Combobox> listSpecialById(@RequestParam("goodsId") @NotBlank String goodsId){
        return specialService.listSpecialByGoods(goodsId);
    }
    
    @PostMapping(value ="/item/add")
    public void addSpecialItem(@RequestBody SpecialItem base){
        specialService.addSpecialItem(base);
    }
    
    @PostMapping(value = "goods/addBatch")
    public void addBatchSpecialGoods(@RequestBody BatchVo base){
        specialService.addBatchGoods(base);
    }

}
