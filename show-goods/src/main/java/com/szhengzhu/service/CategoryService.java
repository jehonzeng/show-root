package com.szhengzhu.service;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface CategoryService {

    /**
     * 添加分类信息
     * 
     * @date 2019年2月27日 下午5:10:40
     * @param categoryInfo
     * @return
     */
    Result<?> addCategory(CategoryInfo categoryInfo);

    /**
     * 修改分类信息
     * 
     * @date 2019年2月27日 下午5:11:08
     * @param categoryInfo
     * @return
     */
    Result<?> editCategory(CategoryInfo categoryInfo);

    /**
     * 获取分页信息
     * 
     * @date 2019年2月28日 下午4:16:04
     * @param infos
     * @return
     */
    Result<PageGrid<CategoryInfo>> getPage(PageParam<CategoryInfo> base);

    /**
     * 根据id获取分类信息
     * 
     * @date 2019年3月1日 上午11:31:27
     * @param markId
     * @return
     */
    Result<?> getCategoryInfo(String markId);

    /**
     * 获取不同状态或者所有分类列表
     * 
     * @date 2019年3月26日 下午7:31:31
     * @return
     */
    Result<?> getDownList(String serverStatus);

    /**
     * 获取上级分类列表
     * 
     * @date 2019年3月29日 下午9:31:24
     * @return
     */
    Result<?> getSuperList();
}
