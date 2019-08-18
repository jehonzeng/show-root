package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface GoodsService {

    /**
     * 添加商品基础信息
     * 
     * @date 2019年2月27日 下午4:08:59
     * @param goods
     * @return
     */
    Result<?> addGoods(GoodsInfo base);

    /**
     * 编辑商品基本信息
     * 
     * @date 2019年3月1日 下午4:59:28
     * @param base
     * @return
     */
    Result<?> editGoods(GoodsInfo base);

    /**
     * 获取商品基本信息
     * 
     * @date 2019年3月1日 下午5:02:32
     * @param markId
     * @return
     */
    Result<?> getGoodsInfo(String markId);

    /**
     * 获取商品基础信息分页
     * 
     * @date 2019年3月7日 上午11:43:25
     * @param base
     * @return
     */
    Result<PageGrid<GoodsVo>> getPage(PageParam<GoodsInfo> base);

    /**
     * 获取商品下拉列表
     * 
     * @date 2019年3月28日 下午6:16:53
     * @return
     */
    Result<List<Combobox>> listCombobox();

    /**
     * 栏目选择商品列表
     * 
     * @date 2019年4月10日 下午12:40:13
     * @return
     */
    Result<List<Combobox>> getListNotColumn();

    /**
     * 标签选择商品列表
     * 
     * @date 2019年4月22日 下午5:59:19
     * @return
     */
    Result<List<Combobox>> getListNotLabel(String labelId);

    /**
     * 批量添加服务
     * 
     * @date 2019年4月22日 下午5:59:21
     * @param base
     * @return
     */
    Result<?> addBatchServer(BatchVo base);

    /**
     * 批量删除服务
     * 
     * @date 2019年4月22日 下午5:59:23
     * @param base
     * @return
     */
    Result<?> moveBatchServer(BatchVo base);

    /**
     * 已选择的服务列表
     * 
     * @date 2019年4月22日 下午5:59:25
     * @param goodsId
     * @return
     */
    Result<?> listInnerServer(String goodsId);

    /**
     * 获取没有设置特价的商品列表
     * 
     * @date 2019年5月6日 下午6:20:20
     * @return
     */
    Result<List<Combobox>> getListNotSpecial();

    /**
     * 获取没有设置图标的商品列表
     * 
     * @date 2019年5月6日 下午6:20:22
     * @return
     */
    Result<List<Combobox>> getListNotIcon();

    /**
     * 获取没有栏目的商品分页信息列表
     * 
     * @date 2019年5月28日 下午1:30:27
     * @param base
     * @return
     */
    Result<PageGrid<GoodsVo>> getPageByColumn(PageParam<GoodsInfo> base);

    /**
     * 获取没有设置标签分类的商品分页列表
     * 
     * @date 2019年5月28日 下午2:10:41
     * @param base
     * @return
     */
    Result<PageGrid<GoodsVo>> getPageByLabel(PageParam<GoodsInfo> base);

    /**
     * 获取没有设置图标的商品列表
     * 
     * @date 2019年5月28日 下午2:40:07
     * @param base
     * @return
     */
    Result<PageGrid<GoodsVo>> getPageByIcon(PageParam<GoodsInfo> base);

    /**
     * 获取没有设置特价的图标
     * 
     * @date 2019年5月28日 下午2:40:09
     * @param base
     * @return
     */
    Result<PageGrid<GoodsVo>> getPageBySpecial(PageParam<GoodsInfo> base);
    
    /**
     * 商品列表推荐
     * 
     * @date 2019年6月10日 下午2:29:22
     * @return
     */
    Result<List<GoodsBase>> listRecommend(String userId);
    
    /**
     * 获取商品详情信息及商品相关信息
     * 
     * @date 2019年6月18日 下午2:47:46
     * @param goodsId
     * @param userId
     * @return
     */
    Result<GoodsInfoVo> getGoodsDetail(String goodsId, String userId);

    /**
     * @date 2019年6月14日 下午12:36:05
     * @param base
     * @return
     */
    Result<?> editGoodsStatus(GoodsInfo base);

}
