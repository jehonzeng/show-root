package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface StoreHouseService {

    /**
     * 添加仓库信息
     * 
     * @date 2019年3月4日 下午5:56:10
     * @param base
     * @return
     */
    Result<?> addStoreHouse(StoreHouseInfo info);

    /**
     * 编辑仓库信息
     * 
     * @date 2019年3月4日 下午5:56:26
     * @param base
     * @return
     */
    Result<?> modifyStoreHouse(StoreHouseInfo info);

    /**
     * 获取仓库分页信息
     * 
     * @date 2019年3月4日 下午5:57:22
     * @param base
     * @return
     */
    Result<PageGrid<StoreHouseInfo>> getPage(PageParam<StoreHouseInfo> base);
    
    /**
     * 获取仓库下拉列表
     * 
     * @date 2019年3月20日 上午10:42:30
     * @return
     */
    Result<List<Combobox>> listCombobox();

    /**获取
     * @date 2019年3月25日 下午6:14:28
     * @param markId
     * @return
     */
    Result<?> getHouseInfo(String markId);
}
