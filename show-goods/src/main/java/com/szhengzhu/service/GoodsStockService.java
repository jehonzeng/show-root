package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface GoodsStockService {

    /**
     * 添加商品库存信息
     * 
     * @date 2019年3月6日 下午4:26:04
     * @param base
     * @return
     */
    Result<?> addGoodsStock(GoodsStock base);

    /**
     * 修改商品库存信息
     * 
     * @date 2019年3月6日 下午4:26:48
     * @param base
     * @return
     */
    Result<?> editGoodsStock(GoodsStock base);

    /**
     * 获取库存商品基本信息
     * 
     * @date 2019年3月7日 上午11:44:40
     * @param base
     * @return
     */
    Result<PageGrid<StockVo>> getPage(PageParam<GoodsStock> base);

    /**
     * 根据id获取库存商品的详细信息
     * 
     * @date 2019年3月13日 下午3:31:54
     * @param mark_id
     * @return
     */
    Result<?> getDetailedInfos(String markId);

    /**
     * 根据id获取库存商品信息
     * 
     * @date 2019年3月25日 下午6:22:14
     * @param markId
     * @return
     */
    Result<?> getGoodsStockInfo(String markId);
    
    /**
     * 获取订单库存商品信息
     * 
     * @date 2019年4月3日 下午3:19:31
     * @param markIds
     * @return
     */
    Result<List<StockVo>> listGoodsStock(List<String> markIds);
    
    /**
     * 下单后
     * @date 2019年7月9日 下午5:12:58
     * @param item
     * @param addressId
     */
    void reduceStock(OrderItem item, String addressId);
    
    /**
     * 获取规格商品信息及存库
     * 
     * @date 2019年6月27日 下午2:40:18
     * @param goodsId
     * @param specIds
     * @param addressId
     * @return
     */
    Result<StockBase> getStockInfo(String goodsId, String specIds, String addressId);
}
