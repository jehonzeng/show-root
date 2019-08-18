package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.LabelService;

@RestController
@RequestMapping(value = "labels")
public class LabelController {
    
    @Resource
    private LabelService labelService;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> save(@RequestBody LabelInfo base) {
        return labelService.addLabel(base);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public Result<?> modify(@RequestBody LabelInfo base) {
        return labelService.modifyLabel(base);
    }
    
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public Result<PageGrid<LabelInfo>> page(@RequestBody PageParam<LabelInfo> base){
        return labelService.getPage(base);
    }
    
    @RequestMapping(value = "/goods/page",method = RequestMethod.POST)
    public Result<PageGrid<LabelGoodsVo>> labelGoodsPage(@RequestBody PageParam<LabelGoods> base){
        return labelService.getLabelGoodsPage(base);
    }
    
    @RequestMapping(value = "/goods/update", method = RequestMethod.PATCH)
    public Result<?> updateLabelGoods(@RequestBody LabelGoods base){
        return labelService.modifyLabelGoods(base);
    }
    
    @RequestMapping(value = "/goods/addBatch",method= RequestMethod.POST)
    public Result<?> addBatchLabelGoods(@RequestBody BatchVo base){
        return labelService.addBatchLabelGoods(base);
    }
    
    @RequestMapping(value = "/goods/delete",method= RequestMethod.POST)
    public Result<?> deleteLabelGoods(@RequestBody LabelGoods base){
        return labelService.deleteLabelGoods(base);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<LabelInfo> getLabelInfo(@PathVariable("markId") String markId){
        return labelService.getLabelInfo(markId);
    }
    
    @RequestMapping(value = "/meal/page",method = RequestMethod.POST)
    public Result<PageGrid<LabelMealVo>> labelMealPage(@RequestBody PageParam<LabelGoods> base){
        return labelService.getLabelMealPage(base);
    }
    
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public Result<List<Label>> listLabelGoods() {
        return labelService.listLabelGoods();
    }
    
}
