package com.szhengzhu.service;

import com.szhengzhu.bean.base.StoreInfo;
import com.szhengzhu.bean.base.StoreItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface StoreService {

    /**
     * 获取门店区域分页信息
     *
     * @date 2019年6月12日 下午2:57:53
     * @param base
     * @return
     */
    PageGrid<StoreInfo> getStorePage(PageParam<StoreInfo> base);

    /**
     * 添加门店区域信息
     *
     * @date 2019年6月12日 下午4:32:57
     * @param base
     * @return
     */
    StoreInfo addStore(StoreInfo base);

    /**
     * 编辑门店区域信息
     *
     * @date 2019年6月12日 下午4:33:00
     * @param base
     * @return
     */
    StoreInfo editStore(StoreInfo base);

    /**
     * 获取下拉列表
     *
     * @return
     * @date 2019年7月3日
     */
    List<Combobox> getComboboxList();

    /**
     * 添加门店人员
     *
     * @param base
     * @return
     */
    void addStoreStaff(BatchVo base);

    /**
     * 删除门店人员
     *
     * @param base
     * @return
     */
    void deleteStoreStaff(StoreItem base);

    /**
     * 根据员工id获取门店
     *
     * @param userId
     * @return
     */
    String getStoreByStaff(String userId);
}
