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
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"券模板管理：CouponTemplateController"})
@RestController
@RequestMapping("/v1/coupontemplate")
public class CouponTemplateController {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @ApiOperation(value = "新增券模板", notes = "新增券模板")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<CouponTemplate> addTemplate(@RequestBody CouponTemplate couponTemplate) {
        return showBaseClient.addTemplate(couponTemplate);
    }
    
    @ApiOperation(value = "修改券模板", notes = "修改券模板")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<CouponTemplate> modfiyTemplate(@RequestBody CouponTemplate couponTemplate) {
        return showBaseClient.modfiyTemplate(couponTemplate);
    }
    
    @ApiOperation(value = "获取券模板分页列表", notes = "获取券模板分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<CouponTemplate>> pageTemplate(@RequestBody PageParam<CouponTemplate> templatePage) {
        return showBaseClient.pageTemplate(templatePage);
    }
    
    @ApiOperation(value="获取有效券模板下拉列表",notes="获取有效券模板下拉列表")
    @RequestMapping(value= "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> getCouponTemplateList(@RequestParam("couponType") Integer couponType){
        return showBaseClient.listCouponTempalte(couponType);
    }
    
}
