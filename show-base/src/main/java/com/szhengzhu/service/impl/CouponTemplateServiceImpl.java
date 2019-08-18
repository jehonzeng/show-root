package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.CouponTemplateMapper;
import com.szhengzhu.service.CouponTemplateService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("couponTemplateService")
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Resource
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public Result<CouponTemplate> saveTemplate(CouponTemplate couponTemplate) {
        if (couponTemplate == null || StringUtils.isEmpty(couponTemplate.getCouponName())
                || couponTemplate.getLineType() == null || couponTemplate.getCouponType() == null
                || couponTemplate.getValidityType() == null
                || couponTemplate.getRangeType() == null) {
            return new Result<>(StatusCode._4004);
        }
        couponTemplate.setMarkId(IdGenerator.getInstance().nexId());
        couponTemplateMapper.insertSelective(couponTemplate);
        return new Result<>(couponTemplate);
    }

    @Override
    public Result<CouponTemplate> updateTemplate(CouponTemplate couponTemplate) {
        if (couponTemplate == null || StringUtils.isEmpty(couponTemplate.getMarkId())) {
            return new Result<>(StatusCode._4004);
        }
        couponTemplateMapper.updateByPrimaryKeySelective(couponTemplate);
        return new Result<>(couponTemplate);
    }

    @Override
    public Result<PageGrid<CouponTemplate>> pageTemplate(PageParam<CouponTemplate> templatePage) {
        PageHelper.startPage(templatePage.getPageIndex(), templatePage.getPageSize());
        PageHelper.orderBy(templatePage.getSidx() + " " + templatePage.getSort());
        PageInfo<CouponTemplate> pageInfo = new PageInfo<>(
                couponTemplateMapper.selectByExampleSelective(templatePage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public CouponTemplate getCouponTemplate(String templateId) {
        return couponTemplateMapper.selectByMark(templateId);
    }

    @Override
    public Result<List<Combobox>> getTemplateCombobox(Integer couponType) {
        List<Combobox> list = couponTemplateMapper.selectComboboxList(couponType);
        return new Result<>(list);
    }
}
