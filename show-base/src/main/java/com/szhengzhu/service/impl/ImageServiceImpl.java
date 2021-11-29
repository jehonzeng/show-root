package com.szhengzhu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.mapper.ImageInfoMapper;
import com.szhengzhu.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageInfoMapper imageInfoMapper;

    @Override
    public ImageInfo addImgInfo(ImageInfo imageInfo) {
        imageInfoMapper.insert(imageInfo);
        return imageInfo;
    }

    @Override
    public void deleteImage(String markId) {
        imageInfoMapper.deleteByPrimaryKey(markId);
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
        if (ObjectUtil.isNull(image) || StrUtil.isEmpty(image.getImagePath())) {
            image = ImageInfo.builder().fileType("png").imagePath("/" + Contacts.DEFAULT_IMG).build();
        } else {
            image.setImagePath(image.getImagePath());
        }
        return image;
    }

    @Override
    public ImageInfo getAccessorylImage(String markId) {
        ImageInfo image = imageInfoMapper.selectAccessoryImage(markId);
        return getImage(image);
    }
}
