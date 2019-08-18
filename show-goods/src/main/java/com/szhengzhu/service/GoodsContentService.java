package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.core.Result;

public interface GoodsContentService {

    /**
     * 展示创建详情内容（创建空详情）
     * 
     * @date 2019年3月1日 下午6:52:57
     * @param goodsId
     * @return
     */
    Result<?> showContentByGoodsId(String goodsId);

    /**
     * 编辑详情内容
     * 
     * @date 2019年3月1日 下午6:53:28
     * @param base
     * @return
     */
    Result<?> editGoodsContent(GoodsContent base);
}
