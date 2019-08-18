package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsSpecService;

@RestController
@RequestMapping("/goodsSpec")
public class GoodsSpecController {

    @Resource
    private GoodsSpecService goodsSpecService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsSpecification>> page(@RequestBody PageParam<GoodsSpecification> base) {
        return goodsSpecService.pageGoodsSpec(base);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody GoodsSpecification base) {
        return goodsSpecService.add(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modify(@RequestBody GoodsSpecification base) {
        return goodsSpecService.modify(base);
    }
}
