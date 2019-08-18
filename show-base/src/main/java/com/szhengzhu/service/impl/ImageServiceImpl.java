package com.szhengzhu.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.ImageInfoMapper;
import com.szhengzhu.service.ImageService;
import com.szhengzhu.util.StringUtils;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
    
    private final String DEFAULT_IMAGE = "default_hands.png";

    @Resource
    private ImageInfoMapper imageInfoMapper;

    @Override
    public Result<ImageInfo> addImgInfo(ImageInfo imageInfo) {
        if (imageInfo == null || StringUtils.isEmpty(imageInfo.getMarkId())) {
            return new Result<>(StatusCode._4004);
        }
        imageInfoMapper.insert(imageInfo);
        return new Result<>();
    }

    @Override
    public Result<?> deleteImage(String markId) {
        imageInfoMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }

    @Override
    public ImageInfo getImage(String markId) {
        ImageInfo image = imageInfoMapper.selectByPrimaryKey(markId);
        return getImage(image);
    }

    @Override
    public ImageInfo getGoodsImage(String goodsId, Integer type, String specIds) {
        ImageInfo image = imageInfoMapper.goodsSpecImage(goodsId, type, specIds);
        return getImage(image);
    }

    @Override
    public ImageInfo goodsSpecImage(String goodsId, String specIds) {
        ImageInfo image = imageInfoMapper.selectGoodsSpecImage(goodsId, specIds);
        return getImage(image);
    }

    @Override
    public ImageInfo voucherSpecImage(String goodsId, String specIds) {
        ImageInfo image = imageInfoMapper.selectVoucharSpecImage(goodsId, specIds);
        return getImage(image);
    }

    @Override
    public ImageInfo mealSmallImage(String mealId) {
        ImageInfo image = imageInfoMapper.selectMealSmallImage(mealId);
        return getImage(image);
    }

    private ImageInfo getImage(ImageInfo image) {
        if (image == null || StringUtils.isEmpty(image.getImagePath())) {
            image = new ImageInfo();
            image.setFileType("png");
            image.setImagePath(Contacts.BASE_IMG_PATH + File.separator + DEFAULT_IMAGE);
        } else {
            image.setImagePath(Contacts.BASE_IMG_PATH + image.getImagePath());
        }
        return image;
    }

    @Override
    public ImageInfo getAccessorylImage(String markId) {
        ImageInfo image = imageInfoMapper.selectAccessoryImage(markId);
        return  getImage(image);
    }
}
