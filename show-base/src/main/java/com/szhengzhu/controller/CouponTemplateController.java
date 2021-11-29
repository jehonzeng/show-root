package com.szhengzhu.controller;

import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.CouponTemplateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/coupontemplate")
public class CouponTemplateController {

    @Resource
    private CouponTemplateService couponTemplateService;

    @PostMapping(value = "/add")
    public CouponTemplate addTemplate(@RequestBody @Validated CouponTemplate couponTemplate) {
        return couponTemplateService.saveTemplate(couponTemplate);
    }

    @PatchMapping(value = "/modify")
    public CouponTemplate modfiyTemplate(@RequestBody @Validated CouponTemplate couponTemplate) {
        return couponTemplateService.updateTemplate(couponTemplate);
    }

    @PostMapping(value = "/page")
    public PageGrid<CouponTemplate> pageTemplate(
            @RequestBody PageParam<CouponTemplate> templatePage) {
        return couponTemplateService.pageTemplate(templatePage);
    }

    @GetMapping(value = "/info")
    public CouponTemplate getCouponTemplate(@RequestParam("templateId") @NotBlank String templateId) {
        return couponTemplateService.getCouponTemplate(templateId);
    }

    @GetMapping(value = "/{markId}")
    public CouponTemplate getCouponTemplateInfo(@PathVariable("markId") @NotBlank String markId) {
        return couponTemplateService.getCouponTemplate(markId);
    }

    @GetMapping(value = "/list")
    public List<Combobox> listCouponTempalte(@RequestParam("couponType") @NotNull Integer couponType) {
        return couponTemplateService.getTemplateCombobox(couponType);
    }
}
