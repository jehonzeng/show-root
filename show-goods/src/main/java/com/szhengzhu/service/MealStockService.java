package com.szhengzhu.service;

import com.szhengzhu.bean.goods.MealStock;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface MealStockService {

    /**
     * 添加套餐库存
     * 
     * @date 2019年8月8日 下午3:43:34
     * @param base
     * @return
     */
    void add(MealStock base);

    /**
     * 修改套餐库存
     * 
     * @date 2019年8月8日 下午3:43:49
     * @param base
     * @return
     */
    void modify(MealStock base);

    /**
     * 获取套餐分页列表
     * @date 2019年8月8日 下午3:44:00
     * @param base
     * @return
     */
    PageGrid<MealStock> pageStock(PageParam<MealStock> base);
    
    /**
     * 获取套餐信息及库存
     * 
     * @date 2019年8月8日 下午3:46:05
     * @param mealId
     * @param addressId
     * @return
     */
    StockBase getStockInfo(String mealId, String addressId);

    /**
     * 减套餐虚拟库存
     * 
     * @date 2019年8月8日 下午3:44:19
     * @param productInfo
     */
    void subCurrentStock(ProductInfo productInfo);
    
    /**
     * 减少套餐真实库存
     * 
     * @date 2019年8月8日 下午3:44:34
     * @param productInfo
     */
    void subTotalStock(ProductInfo productInfo);
    
    /**
     * 加套餐虚拟库存
     * 
     * @date 2019年8月8日 下午3:45:01
     * @param productInfo
     */
    void addCurrentStock(ProductInfo productInfo);
    
    /**
     * 加套餐真实库存
     * 
     * @date 2019年8月8日 下午3:45:12
     * @param productInfo
     */
    void addTotalStock(ProductInfo productInfo);
}
