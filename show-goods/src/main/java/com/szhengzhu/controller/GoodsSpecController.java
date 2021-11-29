package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.GoodsSpecService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/goodsSpec")
public class GoodsSpecController {

    @Resource
    private GoodsSpecService goodsSpecService;

    @PostMapping(value = "/page")
    public PageGrid<GoodsSpecification> page(@RequestBody PageParam<GoodsSpecification> base) {
        ShowAssert.checkTrue(base.getData() == null || StrUtil.isEmpty(base.getData().getGoodsId()), StatusCode._4004);
        return goodsSpecService.pageGoodsSpec(base);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated GoodsSpecification base) {
        goodsSpecService.add(base);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated GoodsSpecification base) {
        goodsSpecService.modify(base);
    }
}
