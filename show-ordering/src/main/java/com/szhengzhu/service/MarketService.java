package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.MarketInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface MarketService {

    /**
     * 获取买减活动分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<MarketInfo> page(PageParam<MarketInfo> pageParam);

    /**
     * 新增买减活动
     *
     * @param marketInfo
     * @return
     */
    String add(MarketInfo marketInfo);

    /**
     * 修改买减活动
     *
     * @param marketInfo
     * @return
     */
    void modify(MarketInfo marketInfo);

    /**
     * 删除活动
     *
     * @param marketId
     * @return
     */
    void delete(String marketId);
}
