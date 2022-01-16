package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"券模板管理：CouponTemplateController"})
@RestController
@RequestMapping("/v1/coupontemplate")
public class CouponTemplateController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "新增券模板", notes = "新增券模板")
    @PostMapping(value = "")
    public Result<CouponTemplate> addTemplate(@RequestBody @Validated CouponTemplate couponTemplate) {
        return showBaseClient.addTemplate(couponTemplate);
    }

    @ApiOperation(value = "修改券模板", notes = "修改券模板")
    @PatchMapping(value = "")
    public Result<CouponTemplate> modifyTemplate(@RequestBody @Validated CouponTemplate couponTemplate) {
        return showBaseClient.modfiyTemplate(couponTemplate);
    }

    @ApiOperation(value = "获取券模板分页列表", notes = "获取券模板分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<CouponTemplate>> pageTemplate(
            @RequestBody PageParam<CouponTemplate> templatePage) {
        return showBaseClient.pageTemplate(templatePage);
    }

    @ApiOperation(value = "获取有效券模板下拉列表", notes = "获取有效券模板下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> getCouponTemplateList(@RequestParam("couponType") @NotNull Integer couponType) {
        return showBaseClient.listCouponTemplate(couponType);
    }

    @ApiOperation(value = "获取模板信息", notes = "获取模板信息")
    @GetMapping(value = "/{markId}")
    public Result<?> getCouponTmplate(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getCouponTemplateInfo(markId);
    }
}
