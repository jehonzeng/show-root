package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsImageService;

@RestController
@RequestMapping(value = "images")
public class GoodsImageController {

    @Resource
    private GoodsImageService goodsImageService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addGoodsImage(@RequestBody GoodsImage base) {
        return goodsImageService.addGoodsImage(base);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyGoodsImage(@RequestBody GoodsImage base) {
        return goodsImageService.midifyGoodsImage(base);
    }

    @RequestMapping(value = "/delete/{markId}", method = RequestMethod.DELETE)
    public Result<?> deleteGoodsImage(@PathVariable("markId") String markId) {
        return goodsImageService.deleteGoodsImage(markId);
    }

    @RequestMapping(value = "/list/{goodsId}", method = RequestMethod.GET)
    public Result<?> showGoodsImage(@PathVariable("goodsId") String goodsId,
            @RequestParam("serverType") Integer serverType) {
        return goodsImageService.getGoodsImage(goodsId, serverType);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public GoodsImage getImageInfo(@RequestParam("markId") String markId) {
        return goodsImageService.getImageInfo(markId);
    }

}
