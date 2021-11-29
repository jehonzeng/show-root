package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ColumnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "columns")
public class ColumnController {

    @Resource
    private ColumnService columnService;

    @PostMapping(value = "/add")
    public ColumnInfo save(@RequestBody @Validated ColumnInfo base) {
        return columnService.addColumn(base);
    }

    @PatchMapping(value = "/update")
    public ColumnInfo modify(@RequestBody @Validated ColumnInfo base) {
        return columnService.modifyColumn(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<ColumnInfo> page(@RequestBody PageParam<ColumnInfo> base) {
        return columnService.getPage(base);
    }

    @PostMapping(value = "/goods/page")
    public PageGrid<ColumnGoodsVo> columnGoodsPage(@RequestBody PageParam<ColumnGoods> base) {
        return columnService.getColumnGoodsPage(base);
    }

    @PatchMapping(value = "/goods/update")
    public ColumnGoods updateColumnGoods(@RequestBody ColumnGoods base) {
        return columnService.modifyColumnGoods(base);
    }

    @PostMapping(value = "/goods/addBatch")
    public void addBatchColumnGoods(@RequestBody BatchVo base) {
        columnService.addBatchColumnGoods(base);
    }

    @PostMapping(value = "/goods/delete")
    public void deleteColumnGoods(@RequestBody ColumnGoods base) {
        columnService.deleteColumnGoods(base);
    }

    @GetMapping(value = "/{markId}")
    public ColumnInfo getColumnInfo(@PathVariable("markId") @NotBlank String markId) {
        return columnService.getColumnInfo(markId);
    }

    @PostMapping(value = "/meal/page")
    public PageGrid<ColumnMealVo> columnMealPage(@RequestBody PageParam<ColumnGoods> base) {
        return columnService.getColumnMealPage(base);
    }
}
