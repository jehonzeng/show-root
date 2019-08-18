package com.szhengzhu.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.ImageService;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    @Resource
    private ImageService imageService;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<ImageInfo> addImgInfo(@RequestBody ImageInfo imageInfo) {
        return imageService.addImgInfo(imageInfo);
    }

    @RequestMapping(value = "/goods/{goodsId}", method = RequestMethod.GET)
    public ImageInfo showGoodsImage(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId, @RequestParam("type") Integer type,
            @RequestParam(value = "specIds", required = false) String specIds) {
        ImageInfo image = imageService.getGoodsImage(goodsId, type, specIds);
        return image;
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public ImageInfo showImage(@PathVariable("markId") String markId) {
        ImageInfo image = imageService.getImage(markId);
        return image;
    }

    @RequestMapping(value = "/delete/{markId}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("markId") String markId) {
        imageService.deleteImage(markId);
    }

    @RequestMapping(value = "/goodSpec/{goodsId}", method = RequestMethod.GET)
    public ImageInfo showGoodsSpecImage(@PathVariable("goodsId") String goodsId,
            @RequestParam(value = "specIds", required = false) String specIds) {
        return imageService.goodsSpecImage(goodsId, specIds);
    }

    @RequestMapping(value = "/voucherSpec/{voucherId}", method = RequestMethod.GET)
    public ImageInfo showVoucherSpecImage(@PathVariable("voucherId") String voucherId) {
        Result<GoodsVoucher> result = showGoodsClient.getGoodsVoucherInfo(voucherId);
        if (!result.isSuccess()) {
            ImageInfo image = new ImageInfo();
            image.setFileType("png");
            image.setImagePath(Contacts.BASE_IMG_PATH + File.separator+ "default_hands.png");
            return image;
        }
        GoodsVoucher voucher = result.getData();
        return imageService.voucherSpecImage(voucher.getProductId(), voucher.getSpecificationIds());
    }

    @RequestMapping(value = "/meal/{mealId}", method = RequestMethod.GET)
    public ImageInfo showMealSmallImage(@PathVariable("mealId") String mealId) {
        return imageService.mealSmallImage(mealId);
    }
    
    @RequestMapping(value="/accessory/{markId}" ,method=RequestMethod.GET)
    public ImageInfo showAccessoryImage(@RequestParam("markId") String markId) {
        return imageService.getAccessorylImage(markId);
    }
}
