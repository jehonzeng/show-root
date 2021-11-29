package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface GoodsSpecService {

    /**
     * 获取商品规格组合价格分页列表
     * @date 2019年6月24日 下午3:32:42
     * @param base
     * @return
     */
    PageGrid<GoodsSpecification> pageGoodsSpec(PageParam<GoodsSpecification> base);
    
    /**
     * 批量生成商品规格组合价格列表
     * @date 2019年6月24日 下午3:33:42
     * @param base
     * @return
     */
    void add(GoodsSpecification base);
    
    /**
     * 修改商品规格组合信息
     * @date 2019年6月24日 下午4:24:44
     * @param base
     * @return
     */
    void modify(GoodsSpecification base);
}
