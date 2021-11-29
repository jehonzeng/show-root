package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsImage;

import java.util.Map;

public interface GoodsImageService {

    /**
     * 添加商品图片
     *
     * @date 2019年3月8日 上午10:23:18
     * @param base
     * @return
     */
    GoodsImage addGoodsImage(GoodsImage base);

    /**
     * 修改商品图片信息
     *
     * @date 2019年3月8日 上午10:24:01
     * @param base
     * @return
     */
    GoodsImage modifyGoodsImage(GoodsImage base);

    /**
     * 删除商品图片信息
     *
     * @date 2019年3月8日 上午10:24:45
     * @param markId
     * @return
     */
    void deleteGoodsImage(String markId);

    /**
     * 获取商品图片信息
     * 包括商品信息列表和图片请求路径
     *
     * @date 2019年3月8日 下午1:36:05
     * @param goodsId,serverType
     * @return
     */
    Map<String, Object> getGoodsImage(String goodsId, Integer serverType);

    /**
     * 根据markId获取商品图片信息
     *
     * @date 2019年3月8日 下午2:36:45
     * @param markId
     * @return
     */
    GoodsImage getImageInfo(String markId);
}
