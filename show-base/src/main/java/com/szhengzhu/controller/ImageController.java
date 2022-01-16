package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.ImageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping(value = "/images")
public class ImageController {

    @Resource
    private ImageService imageService;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @PostMapping(value = "/add")
    public ImageInfo addImgInfo(@RequestBody ImageInfo imageInfo) {
        return imageService.addImgInfo(imageInfo);
    }

    @GetMapping(value = "/goods/{goodsId}")
    public ImageInfo showGoodsImage(
            @PathVariable("goodsId") @NotBlank String goodsId, @RequestParam("type") @NotNull Integer type,
            @RequestParam(value = "specIds", required = false) String specIds) {
        ImageInfo image = imageService.getGoodsImage(goodsId, type, specIds);
        return image;
    }

    @GetMapping(value = "/{markId}")
    public ImageInfo showImage(@PathVariable("markId") @NotBlank String markId) {
        ImageInfo image = imageService.getImage(markId);
        return image;
    }

    @DeleteMapping(value = "/{markId}")
    public void deleteImage(@PathVariable("markId") @NotBlank String markId) {
        imageService.deleteImage(markId);
    }

    @GetMapping(value = "/goodSpec/{goodsId}")
    public ImageInfo showGoodsSpecImage(@PathVariable("goodsId") @NotBlank String goodsId,
                                        @RequestParam(value = "specIds", required = false) String specIds) {
        return imageService.goodsSpecImage(goodsId, specIds);
    }

    @GetMapping(value = "/voucherSpec/{voucherId}")
    public ImageInfo showVoucherSpecImage(@PathVariable("voucherId") @NotBlank String voucherId) {
        Result<GoodsVoucher> result = showGoodsClient.getGoodsVoucherInfo(voucherId);
        if (!result.isSuccess()) {
            ImageInfo image = ImageInfo.builder().fileType("png").imagePath("/" + Contacts.DEFAULT_IMG).build();
            return image;
        }
        GoodsVoucher voucher = result.getData();
        return imageService.voucherSpecImage(voucher.getProductId(), voucher.getSpecificationIds());
    }

    @GetMapping(value = "/meal/{mealId}")
    public ImageInfo showMealSmallImage(@PathVariable("mealId") @NotBlank String mealId) {
        return imageService.mealSmallImage(mealId);
    }

    @GetMapping(value = "/accessory/{markId}")
    public ImageInfo showAccessoryImage(@RequestParam("markId") @NotBlank String markId) {
        return imageService.getAccessorylImage(markId);
    }
}
