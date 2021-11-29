package com.szhengzhu.service;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface ColumnService {

    /**
     * 添加
     *
     * @date 2019年3月27日 下午6:16:54
     * @param base
     * @return
     */
    ColumnInfo addColumn(ColumnInfo base);

    /**
     * 修改
     *
     * @date 2019年3月27日 下午6:16:56
     * @param base
     * @return
     */
    ColumnInfo modifyColumn(ColumnInfo base);

    /**
     * 获取分页列表
     *
     * @date 2019年3月27日 下午6:16:59
     * @param base
     * @return
     */
    PageGrid<ColumnInfo> getPage(PageParam<ColumnInfo> base);

    /**
     * 修改栏目商品信息
     *
     * @date 2019年3月28日 上午10:42:52
     * @param base
     * @return
     */
    ColumnGoods modifyColumnGoods(ColumnGoods base);

    /**
     * 获取栏目商品分页列表
     *
     * @date 2019年3月28日 上午11:01:17
     * @param base
     * @return
     */
    PageGrid<ColumnGoodsVo> getColumnGoodsPage(PageParam<ColumnGoods> base);

    /**
     * 批量添加栏目商品
     *
     * @date 2019年3月28日 上午11:07:04
     * @param base
     * @return
     */
    void addBatchColumnGoods(BatchVo base);

    /**
     * 删除栏目商品
     *
     * @date 2019年3月28日 上午11:11:53
     * @param base
     * @return
     */
    void deleteColumnGoods(ColumnGoods base);

    /**
     * 根据id获取栏目信息
     *
     * @date 2019年4月8日 下午3:40:56
     * @param markId
     * @return
     */
    ColumnInfo getColumnInfo(String markId);

    /**
     * 获取栏目套餐分页信息
     *
     * @date 2019年5月28日 下午4:17:36
     * @param base
     * @return
     */
    PageGrid<ColumnMealVo> getColumnMealPage(PageParam<ColumnGoods> base);
}
