package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.ColumnService;

@RestController
@RequestMapping(value = "columns")
public class ColumnController {

    @Resource
    private ColumnService columnService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ColumnInfo base) {
        return columnService.addColumn(base);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public Result<?> modify(@RequestBody ColumnInfo base) {
        return columnService.modifyColumn(base);
    }
    
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public Result<PageGrid<ColumnInfo>> page(@RequestBody PageParam<ColumnInfo> base){
        return columnService.getPage(base);
    }
    
    @RequestMapping(value = "/goods/page",method = RequestMethod.POST)
    public Result<PageGrid<ColumnGoodsVo>> columnGoodsPage(@RequestBody PageParam<ColumnGoods> base){
        return columnService.getColumnGoodsPage(base);
    }
    
    @RequestMapping(value = "/goods/update", method = RequestMethod.PATCH)
    public Result<?> updateColumnGoods(@RequestBody ColumnGoods base){
        return columnService.modifyColumnGoods(base);
    }
    
    @RequestMapping(value = "/goods/addBatch",method= RequestMethod.POST)
    public Result<?> addBatchColumnGoods(@RequestBody BatchVo base){
        return columnService.addBatchColumnGoods(base);
    }
    
    @RequestMapping(value = "/goods/delete",method= RequestMethod.POST)
    public Result<?> deleteColumnGoods(@RequestBody ColumnGoods base){
        return columnService.deleteColumnGoods(base);
    }
    
    @RequestMapping(value = "/{markId}",method =RequestMethod.GET)
    public Result<ColumnInfo> getColumnInfo(@PathVariable(value="markId") String markId){
        return columnService.getColumnInfo(markId);
    }
    
    @RequestMapping(value = "/meal/page",method = RequestMethod.POST)
    public Result<PageGrid<ColumnMealVo>> columnMealPage(@RequestBody PageParam<ColumnGoods> base){
        return columnService.getColumnMealPage(base);
    }
}
