package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsContentService;

@RestController
@RequestMapping(value = "contents")
public class ContentController {

    @Resource
    private GoodsContentService goodsContentService;

    @RequestMapping(value = "/show/{goodsId}", method = RequestMethod.GET)
    public Result<?> showContent(@PathVariable("goodsId") String goodsId) {
        return goodsContentService.showContentByGoodsId(goodsId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyContent(@RequestBody GoodsContent goodsContent) {
        return goodsContentService.editGoodsContent(goodsContent);
    }
}
