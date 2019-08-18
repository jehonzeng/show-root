package com.szhengzhu.service;

import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface SpecificationService {

    /**
     * 添加属性基本信息
     * 
     * @date 2019年3月5日 上午11:00:19
     * @param base
     * @return
     */
    Result<?> addSpecification(SpecificationInfo base);

    /**
     * 编辑属性基本信息
     * 
     * @date 2019年3月5日 上午11:57:22
     * @param base
     * @return
     */
    Result<?> editSpecification(SpecificationInfo base);

    /**
     * 获取属性分页信息
     * 
     * @date 2019年3月5日 下午12:01:11
     * @param base
     * @return
     */
    Result<PageGrid<SpecificationInfo>> getPage(PageParam<SpecificationInfo> base);

    /**
     * 批量插入
     * 
     * @date 2019年3月20日 下午7:04:34
     * @param info
     * @return
     */
    Result<?> insertBatchSpec(SpecBatchVo info);

    /**
     * 根据goodsid获取属性列表
     * 
     * @date 2019年3月21日 下午6:35:31
     * @param goodsId
     * @return
     */
    Result<?> getSpecList(String goodsId);
    
    /**
     * 获取关联某一类型的规格列表
     * 
     * @date 2019年6月18日 下午6:38:12
     * @param typeId
     * @return
     */
    Result<PageGrid<SpecificationInfo>> pageInByType(PageParam<SpecificationInfo> base);
    
    /**
     * 获取未关联某一类型的规格列表
     * 
     * @date 2019年6月18日 下午6:39:14
     * @param typeId
     * @return
     */
    Result<PageGrid<SpecificationInfo>> pageNotInByType(PageParam<SpecificationInfo> base);
}
