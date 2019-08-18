package com.szhengzhu.service;

import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface IconService {

    /**
     * 添加图标信息
     * 
     * @date 2019年4月23日 下午12:02:40
     * @param base
     * @return
     */
    Result<?> addIcon(IconInfo base);

    /**
     * 修改图标信息
     * 
     * @date 2019年4月23日 下午12:02:43
     * @param base
     * @return
     */
    Result<?> modifyIcon(IconInfo base);

    /**
     * 获取图标分页信息
     * 
     * @date 2019年4月23日 下午12:02:46
     * @param base
     * @return
     */
    Result<PageGrid<IconInfo>> getPage(PageParam<IconInfo> base);

    /**
     * 根据商品id获取该商品未设置的图标列表
     * 
     * @date 2019年4月23日 下午12:02:48
     * @return
     */
    Result<?> getIconByGoods(String goodsId);

    /**
     * 根据id获取图标信息
     * 
     * @date 2019年4月23日 下午12:32:07
     * @param markId
     * @return
     */
    Result<?> getInconById(String markId);
    
    /**
     * 获取带有图标的商品分页信息
     * 
     * @date 2019年4月23日 下午12:32:07
     * @param markId
     * @return
     */

    Result<PageGrid<IconGoodsVo>> getItemPage(PageParam<IconItem> base);
    
    /**
     * 上删除图标信息
     * 
     * @date 2019年4月23日 下午12:32:07
     * @param markId
     * @return
     */

    Result<?> deleteItem(IconItem base);
    
    /**
     * 单个商品添加图标
     * 
     * @date 2019年4月23日 下午12:32:07
     * @param markId
     * @return
     */

    Result<?> addItem(IconItem base);
    
    /**
     * 批量添加图标商品
     * 
     * @date 2019年4月23日 下午12:32:07
     * @param markId
     * @return
     */

    Result<?> addBatchGoods(BatchVo base);

}
