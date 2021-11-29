package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.service.GoodsImageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "images")
public class GoodsImageController {

    @Resource
    private GoodsImageService goodsImageService;

    @PostMapping(value = "/add")
    public GoodsImage addGoodsImage(@RequestBody GoodsImage base) {
        return goodsImageService.addGoodsImage(base);
    }

    @PatchMapping(value = "/edit")
    public GoodsImage modifyGoodsImage(@RequestBody GoodsImage base) {
        return goodsImageService.modifyGoodsImage(base);
    }

    @DeleteMapping(value = "/delete/{markId}")
    public void deleteGoodsImage(@PathVariable("markId") @NotBlank String markId) {
        goodsImageService.deleteGoodsImage(markId);
    }

    @GetMapping(value = "/list/{goodsId}")
    public Map<String, Object> showGoodsImage(@PathVariable("goodsId") @NotBlank String goodsId,
                                              @RequestParam("serverType") @NotNull Integer serverType) {
        return goodsImageService.getGoodsImage(goodsId, serverType);
    }

    @GetMapping(value = "/info")
    public GoodsImage getImageInfo(@RequestParam("markId") @NotBlank String markId) {
        return goodsImageService.getImageInfo(markId);
    }

}
