package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.CouponTemplateService;

@RestController
@RequestMapping("/coupontemplate")
public class CouponTemplateController {

    @Resource
    private CouponTemplateService couponTemplateService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<CouponTemplate> addTemplate(@RequestBody CouponTemplate couponTemplate) {
        return couponTemplateService.saveTemplate(couponTemplate);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<CouponTemplate> modfiyTemplate(@RequestBody CouponTemplate couponTemplate) {
        return couponTemplateService.updateTemplate(couponTemplate);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<CouponTemplate>> pageTemplate(
            @RequestBody PageParam<CouponTemplate> templatePage) {
        return couponTemplateService.pageTemplate(templatePage);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CouponTemplate getCouponTmplate(@RequestParam("templateId") String templateId) {
        return couponTemplateService.getCouponTemplate(templateId);
    }
    
    @RequestMapping(value= "/list", method = RequestMethod.GET)
    public Result<List<Combobox>> listCouponTempalte(@RequestParam("couponType") Integer couponType){
        return couponTemplateService.getTemplateCombobox(couponType);
    }
}
