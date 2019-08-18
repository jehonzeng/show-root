package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"系统属性管理：AttributeController"})
@RestController
@RequestMapping("/v1/attributes")
public class AttributeController {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @ApiOperation(value = "添加系统属性信息", notes = "添加系统属性信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<AttributeInfo> addAttribute(@RequestBody AttributeInfo attributeInfo) {
        return showBaseClient.addAttribute(attributeInfo);
    }
    
    @ApiOperation(value = "修改系统属性信息", notes = "修改系统属性信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<AttributeInfo> modifyAttribute(@RequestBody AttributeInfo attributeInfo) {
        return showBaseClient.modifyAttribute(attributeInfo);
    }
    
    @ApiOperation(value = "根据主键获取属性详细信息", notes = "根据主键获取属性详细信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<AttributeInfo> getAttributeInfo(@ApiParam(name = "markId", value = "主键", required = true) @PathVariable("markId") String markId) {
        return showBaseClient.getAttributeInfo(markId);
    }
    
    @ApiOperation(value = "获取系统属性分页列表", notes = "获取系统属性分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<AttributeInfo>> pageAttribute(@RequestBody PageParam<AttributeInfo> attrPage) {
        return showBaseClient.pageAttribute(attrPage);
    }
    
    @ApiOperation(value = "通过类型值代码获取下拉值列表", notes = "通过类型值代码获取下拉值列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox(@RequestParam("type") String type) {
        return showBaseClient.listCombobox(type);
    }
}
