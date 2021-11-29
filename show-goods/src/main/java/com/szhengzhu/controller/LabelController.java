package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.LabelService;
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
@RequestMapping(value = "labels")
public class LabelController {
    
    @Resource
    private LabelService labelService;
    
    @PostMapping(value = "/add")
    public LabelInfo save(@RequestBody @Validated LabelInfo base) {
        return labelService.addLabel(base);
    }

    @PatchMapping(value = "/update")
    public LabelInfo modify(@RequestBody @Validated LabelInfo base) {
        return labelService.modifyLabel(base);
    }
    
    @PostMapping(value = "/page")
    public PageGrid<LabelInfo> page(@RequestBody PageParam<LabelInfo> base){
        return labelService.getPage(base);
    }
    
    @PostMapping(value = "/goods/page")
    public PageGrid<LabelGoodsVo> labelGoodsPage(@RequestBody PageParam<LabelGoods> base){
        return labelService.getLabelGoodsPage(base);
    }
    
    @PatchMapping(value = "/goods/update")
    public LabelGoods updateLabelGoods(@RequestBody LabelGoods base){
        return labelService.modifyLabelGoods(base);
    }
    
    @PostMapping(value = "/goods/addBatch")
    public void addBatchLabelGoods(@RequestBody BatchVo base){
        labelService.addBatchLabelGoods(base);
    }
    
    @PostMapping(value = "/goods/delete")
    public void deleteLabelGoods(@RequestBody LabelGoods base){
        labelService.deleteLabelGoods(base);
    }
    
    @GetMapping(value = "/{markId}")
    public LabelInfo getLabelInfo(@PathVariable("markId") @NotBlank String markId){
        return labelService.getLabelInfo(markId);
    }
    
    @PostMapping(value = "/meal/page")
    public PageGrid<LabelMealVo> labelMealPage(@RequestBody PageParam<LabelGoods> base){
        return labelService.getLabelMealPage(base);
    }
    
    @GetMapping(value = "/goods/list")
    public List<Label> listLabelGoods() {
        return labelService.listLabelGoods();
    }
    
    @GetMapping(value = "/{labelId}/product")
    public List<GoodsBase> listLabelGoods(@PathVariable("labelId") @NotBlank String lableId) {
        return labelService.listLabelGoods(lableId);
    }
    
}
