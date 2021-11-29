package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.CommodityPrice;

/**
 * @author jehon
 */
public interface CommodityPriceService {

    /**
     * 添加商品价格
     *
     * @param commodityPrice
     * @return
     */
    String addPrice(CommodityPrice commodityPrice);

    /**
     * 修改商品价格
     *
     * @param commodityPrice
     * @return
     */
    void modifyPrice(CommodityPrice commodityPrice);

    /**
     * 删除商品价格
     *
     * @param priceId
     * @return
     */
    void deletePrice(String priceId);
}
