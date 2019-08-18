package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface FoodsService {

    /**
     * @date 2019年3月25日 下午5:13:42
     * @param base
     * @return
     */
    Result<?> addFoodsInfo(FoodsInfo base);

    /**
     * 修改食材
     * 
     * @date 2019年3月25日 下午5:13:44
     * @param base
     * @return
     */
    Result<?> modifyFoodsInfo(FoodsInfo base);

    /**
     * 添加食材
     * 
     * @date 2019年3月25日 下午5:13:48
     * @param base
     * @return
     */
    Result<PageGrid<FoodsInfo>> getPage(PageParam<FoodsInfo> base);

    /**
     * 根据id获取食材信息
     * 
     * @date 2019年3月25日 下午5:13:53
     * @param markId
     * @return
     */
    Result<?> getFoodsInfo(String markId);

    /**
     * 下拉列表
     * 
     * @date 2019年4月24日 下午12:26:03
     * @return
     */
    Result<List<Combobox>> listFoodWithoutGoods(String goodsId);

    /**
     * 批量添加
     * 
     * @date 2019年4月24日 下午12:26:18
     * @param base
     * @return
     */
    Result<?> addBatchItem(FoodsItem base);

    /**
     * 获取商品食材列表
     * 
     * @date 2019年4月24日 下午12:29:25
     * @param base
     * @return
     */
    Result<PageGrid<GoodsFoodVo>> getItemPage(PageParam<FoodsItem> base);

    /**
     * 删除食材
     * 
     * @date 2019年4月24日 下午12:29:44
     * @param markId
     * @return
     */
    Result<?> deleteItem(String markId);

    /**
     * 修改商品食材信息
     * 
     * @date 2019年5月10日 下午1:55:44
     * @param base
     * @return
     */
    Result<?> updateFoodsItem(FoodsItem base);

    /**
     * 获取启用的食材列表
     * 
     * @date 2019年5月13日 下午2:02:15
     * @return
     */
    Result<List<Combobox>> getFoodCombobox();
}
