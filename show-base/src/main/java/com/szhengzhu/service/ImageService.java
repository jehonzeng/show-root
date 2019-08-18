package com.szhengzhu.service;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.core.Result;

public interface ImageService {

    /**
     * 添加图片信息
     * 
     * @date 2019年5月5日 下午4:04:15
     * @param imageInfo
     * @return
     */
    Result<ImageInfo> addImgInfo(ImageInfo imageInfo);

    /**
     * 删除图片路径信息
     * 
     * @date 2019年3月8日 下午2:29:37
     * @param markId
     * @return
     */
    Result<?> deleteImage(String markId);

    /**
     * 根据图片id获取图片缓冲流
     * 
     * @date 2019年3月14日 下午2:24:41
     * @param markId
     * @return
     */
    ImageInfo getImage(String markId);
    
    
    /**
     * 根据商品id、商品图片类型和规格获取商品图片
     * @date 2019年3月14日 下午6:28:13
     * @param goodsId
     * @param type
     * @param specList
     * @return
     */
    ImageInfo getGoodsImage(String goodsId, Integer type,String specIds);

    /**
     * 获取普通商品的规格图片
     *
     * @param goodsId
     * @param specIds
     * @return
     * @date 2019年7月1日
     */
    ImageInfo goodsSpecImage(String goodsId, String specIds);

    /**
     * 获取商品券规格图片
     *
     * @param goodsId
     * @return
     * @date 2019年7月1日
     */
    ImageInfo voucherSpecImage(String goodsId,String specIds);

    /**
     * 获取套餐小展示图
     *
     * @param mealId
     * @return
     * @date 2019年7月1日
     */
    ImageInfo mealSmallImage(String mealId);

    /**
     * 获取附属品小展示图
     *
     * @param markId
     * @return
     * @date 2019年7月17日
     */
    ImageInfo getAccessorylImage(String markId);
}
