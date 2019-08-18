package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface BrandService {

    /**
     * 添加新品牌
     * 
     * @date 2019年2月27日 下午4:15:59
     * @param info
     * @return
     */
    Result<?> addGoodsBrand(BrandInfo brandInfo);

    /**
     * 修改品牌
     * 
     * @date 2019年2月27日 下午4:18:22
     * @param brandInfo
     * @return
     */
    Result<?> editGoodsBrand(BrandInfo brandInfo);

    /**
     * 获取分页信息
     * 
     * @date 2019年2月28日 下午4:16:04
     * @param infos
     * @return
     */
    Result<PageGrid<BrandInfo>> getPage(PageParam<BrandInfo> base);

    /**
     * 根据id获品牌信息
     * 
     * @date 2019年3月1日 上午10:59:31
     * @param markId
     * @return
     */
    Result<?> getBrandInfo(String markId);
    
    /**
     * 获取品牌下拉列表
     * 
     * @date 2019年3月18日 下午3:38:48
     * @return
     */
    Result<List<Combobox>> listCombobox();

}
