package com.szhengzhu.service;

import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface SpecificationService {

    /**
     * 添加属性基本信息
     *
     * @date 2019年3月5日 上午11:00:19
     * @param base
     * @return
     */
    SpecificationInfo addSpecification(SpecificationInfo base);

    /**
     * 编辑属性基本信息
     *
     * @date 2019年3月5日 上午11:57:22
     * @param base
     * @return
     */
    SpecificationInfo modifySpecification(SpecificationInfo base);

    /**
     * 获取属性分页信息
     *
     * @date 2019年3月5日 下午12:01:11
     * @param base
     * @return
     */
    PageGrid<SpecificationInfo> getPage(PageParam<SpecificationInfo> base);

    /**
     * 批量插入
     *
     * @date 2019年3月20日 下午7:04:34
     * @param info
     * @return
     */
    SpecBatchVo insertBatchSpec(SpecBatchVo info);

    /**
     * 根据goodsid获取属性列表
     *
     * @date 2019年3月21日 下午6:35:31
     * @param goodsId
     * @return
     */
    List<SpecChooseBox> getSpecList(String goodsId);

    /**
     * 获取关联某一类型的规格列表
     *
     * @date 2019年6月18日 下午6:38:12
     * @param base
     * @return
     */
    PageGrid<SpecificationInfo> pageInByType(PageParam<SpecificationInfo> base);

    /**
     * 获取未关联某一类型的规格列表
     *
     * @date 2019年6月18日 下午6:39:14
     * @param base
     * @return
     */
    PageGrid<SpecificationInfo> pageNotInByType(PageParam<SpecificationInfo> base);
}
