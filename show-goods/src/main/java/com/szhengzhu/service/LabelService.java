package com.szhengzhu.service;

import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface LabelService {

    /**
     * 添加分类标签信息
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    LabelInfo addLabel(LabelInfo base);

    /**
     * 编辑分类标签信息
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    LabelInfo modifyLabel(LabelInfo base);

    /**
     * 分类标签分页列表
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    PageGrid<LabelInfo> getPage(PageParam<LabelInfo> base);

    /**
     * 分类标签中普通商品分页信息
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    PageGrid<LabelGoodsVo> getLabelGoodsPage(PageParam<LabelGoods> base);

    /**
     * 删除分类标签中的商品
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    void deleteLabelGoods(LabelGoods base);

    /**
     * 修改标签商品信息
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    LabelGoods modifyLabelGoods(LabelGoods base);

    /**
     * 批量添加商品信息
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    void addBatchLabelGoods(BatchVo base);

    /**
     * 根据id获取标签商品信息
     *
     * @date 2019年3月27日 下午6:16:54
     * @param markId
     * @return
     */
    LabelInfo getLabelInfo(String markId);

    /**
     * 获取套餐商品分页信息
     *
     * @date 2019年5月29日 上午11:39:21
     * @param base
     * @return
     */
    PageGrid<LabelMealVo> getLabelMealPage(PageParam<LabelGoods> base);

    /**
     * 获取标签分类商品列表
     *
     * @date 2019年6月5日 下午4:53:22
     * @return
     */
    List<Label> listLabelGoods();

    /**
     * 获取某一标签分类的商品列表
     *
     * @date 2019年9月2日 上午10:20:12
     * @param labelId
     * @return
     */
    List<GoodsBase> listLabelGoods(String labelId);
}
