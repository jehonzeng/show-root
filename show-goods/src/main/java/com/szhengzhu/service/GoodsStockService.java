package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface GoodsStockService {

    /**
     * 添加商品库存信息
     *
     * @date 2019年3月6日 下午4:26:04
     * @param base
     * @return
     */
    GoodsStock addGoodsStock(GoodsStock base);

    /**
     * 修改商品库存信息
     *
     * @date 2019年3月6日 下午4:26:48
     * @param base
     * @return
     */
    GoodsStock modifyGoodsStock(GoodsStock base);

    /**
     * 获取库存商品基本信息
     *
     * @date 2019年3月7日 上午11:44:40
     * @param base
     * @return
     */
    PageGrid<StockVo> getPage(PageParam<GoodsStock> base);

    /**
     * 根据id获取库存商品的详细信息
     *
     * @date 2019年3月13日 下午3:31:54
     * @param markId
     * @return
     */
    GoodsBaseVo getDetailInfos(String markId);

    /**
     * 根据id获取库存商品信息
     *
     * @date 2019年3月25日 下午6:22:14
     * @param markId
     * @return
     */
    GoodsStock getGoodsStockInfo(String markId);

    /**
     * 获取订单库存商品信息
     *
     * @date 2019年4月3日 下午3:19:31
     * @param markIds
     * @return
     */
    List<StockVo> listGoodsStock(List<String> markIds);

    /**
     * 获取规格商品信息及存库
     *
     * @date 2019年6月27日 下午2:40:18
     * @param goodsId
     * @param specIds
     * @param addressId
     * @return
     */
    StockBase getStockInfo(String goodsId, String specIds, String addressId);

    /**
     * 减少商品虚拟库存
     *
     * @date 2019年8月6日 上午11:29:14
     * @param productInfo
     */
    void subCurrentStock(ProductInfo productInfo);

    /**
     * 减少商品真实库存
     *
     * @date 2019年8月6日 上午11:38:32
     * @param productInfo
     */
    void subTotalStock(ProductInfo productInfo);

    /**
     * 添加商品虚拟库存
     *
     * @date 2019年8月6日 下午3:55:42
     * @param productInfo
     */
    void addCurrentStock(ProductInfo productInfo);

    /**
     * 添加商品真实库存
     * @date 2019年8月8日 上午10:32:46
     * @param productInfo
     */
    void addTotalStock(ProductInfo productInfo);
}
