package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface LabelService {

    /**
     * 添加分类标签信息
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<?> addLabel(LabelInfo base);

    /**
     * 编辑分类标签信息
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<?> modifyLabel(LabelInfo base);

    /**
     * 分类标签分页列表
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<PageGrid<LabelInfo>> getPage(PageParam<LabelInfo> base);

    /**
     * 分类标签中普通商品分页信息
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<PageGrid<LabelGoodsVo>> getLabelGoodsPage(PageParam<LabelGoods> base);

    /**
     * 删除分类标签中的商品
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<?> deleteLabelGoods(LabelGoods base);

    /**
     * 修改标签商品信息
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<?> modifyLabelGoods(LabelGoods base);

    /**
     * 批量添加商品信息
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<?> addBatchLabelGoods(BatchVo base);

    /**
     * 根据id获取标签商品信息
     * 
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    Result<LabelInfo> getLabelInfo(String markId);

    /**
     * 获取套餐商品分页信息
     * 
     * @date 2019年5月29日 上午11:39:21
     * @param base
     * @return
     */
    Result<PageGrid<LabelMealVo>> getLabelMealPage(PageParam<LabelGoods> base);
    
    /**
     * 获取标签分类商品列表
     * 
     * @date 2019年6月5日 下午4:53:22
     * @return
     */
    Result<List<Label>> listLabelGoods();

}
