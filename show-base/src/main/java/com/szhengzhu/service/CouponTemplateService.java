package com.szhengzhu.service;

import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface CouponTemplateService {

    /**
     * 添加券模板
     *
     * @date 2019年3月4日 下午3:39:15
     * @param couponTemplate
     * @return
     */
    CouponTemplate saveTemplate(CouponTemplate couponTemplate);

    /**
     * 修改券模板
     *
     * @date 2019年3月4日 下午3:39:53
     * @param couponTemplate
     * @return
     */
    CouponTemplate updateTemplate(CouponTemplate couponTemplate);

    /**
     * 获取券模板分页列表
     *
     * @date 2019年3月4日 下午3:40:49
     * @param templatePage
     * @return
     */
    PageGrid<CouponTemplate> pageTemplate(PageParam<CouponTemplate> templatePage);

    /**
     * 获取模板信息
     *
     * @param templateId
     * @date 2019年6月21日 下午5:12:55
     * @return
     */
    CouponTemplate getCouponTemplate(String templateId);

    /**
     * 获取优惠券模板下拉列表
     * @param couponType
     * @date 2019年6月25日 下午4:07:29
     * @return
     */
    List<Combobox> getTemplateCombobox(Integer couponType);
}
