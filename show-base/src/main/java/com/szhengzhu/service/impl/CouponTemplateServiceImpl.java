package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.CouponTemplateMapper;
import com.szhengzhu.service.CouponTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("couponTemplateService")
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Resource
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public CouponTemplate saveTemplate(CouponTemplate couponTemplate) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        couponTemplate.setMarkId(snowflake.nextIdStr());
        couponTemplateMapper.insertSelective(couponTemplate);
        return couponTemplate;
    }

    @Override
    public CouponTemplate updateTemplate(CouponTemplate couponTemplate) {
        couponTemplateMapper.updateByPrimaryKeySelective(couponTemplate);
        return couponTemplate;
    }

    @Override
    public PageGrid<CouponTemplate> pageTemplate(PageParam<CouponTemplate> templatePage) {
        PageMethod.startPage(templatePage.getPageIndex(), templatePage.getPageSize());
        PageMethod.orderBy(templatePage.getSidx() + " " + templatePage.getSort());
        PageInfo<CouponTemplate> pageInfo = new PageInfo<>(
                couponTemplateMapper.selectByExampleSelective(templatePage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public CouponTemplate getCouponTemplate(String templateId) {
        return couponTemplateMapper.selectByPrimaryKey(templateId);
    }

    @Override
    public List<Combobox> getTemplateCombobox(Integer couponType) {
        return couponTemplateMapper.selectComboboxList(couponType);
    }
}
