package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.service.GoodsContentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "contents")
public class ContentController {

    @Resource
    private GoodsContentService goodsContentService;

    @GetMapping(value = "/show/{goodsId}")
    public GoodsContent shwContent(@PathVariable("goodsId") @NotBlank String goodsId) {
        return goodsContentService.showContentByGoodsId(goodsId);
    }

    @PatchMapping(value = "/edit")
    public GoodsContent modifyContent(@RequestBody @Validated GoodsContent goodsContent) {
        return goodsContentService.editGoodsContent(goodsContent);
    }
}
