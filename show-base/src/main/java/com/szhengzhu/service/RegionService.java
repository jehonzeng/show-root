package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.base.RegionInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface RegionService {

    /**
     * 获取门店区域分页信息
     * 
     * @date 2019年6月12日 下午2:57:53
     * @param base
     * @return
     */
    Result<?> getRegionPage(PageParam<RegionInfo> base);

    /**
     * 添加门店区域信息
     * 
     * @date 2019年6月12日 下午4:32:57
     * @param base
     * @return
     */
    Result<?> addRegion(RegionInfo base);

    /**
     * 编辑门店区域信息
     * 
     * @date 2019年6月12日 下午4:33:00
     * @param base
     * @return
     */
    Result<?> editRegion(RegionInfo base);

    /**
     * 获取下拉列表
     *
     * @return
     * @date 2019年7月3日
     */
    Result<List<Combobox>> getComboboxList();
    
}
