package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"系统属性管理：AttributeController"})
@RestController
@RequestMapping("/v1/attributes")
public class AttributeController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加系统属性信息", notes = "添加系统属性信息")
    @PostMapping(value = "")
    public Result<AttributeInfo> addAttribute(@RequestBody @Validated AttributeInfo attributeInfo) {
        return showBaseClient.addAttribute(attributeInfo);
    }

    @ApiOperation(value = "修改系统属性信息", notes = "修改系统属性信息")
    @PatchMapping(value = "")
    public Result<AttributeInfo> modifyAttribute(@RequestBody @Validated AttributeInfo attributeInfo) {
        return showBaseClient.modifyAttribute(attributeInfo);
    }

    @ApiOperation(value = "根据主键获取属性详细信息", notes = "根据主键获取属性详细信息")
    @GetMapping(value = "/{markId}")
    public Result<AttributeInfo> getAttributeInfo(@ApiParam(name = "markId", value = "主键", required = true) @PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getAttributeInfo(markId);
    }

    @ApiOperation(value = "获取系统属性分页列表", notes = "获取系统属性分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<AttributeInfo>> pageAttribute(@RequestBody PageParam<AttributeInfo> attrPage) {
        return showBaseClient.pageAttribute(attrPage);
    }

    @ApiOperation(value = "通过类型值代码获取下拉值列表", notes = "通过类型值代码获取下拉值列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox(@RequestParam("type") String type) {
        return showBaseClient.listComboboxByType(type);
    }
}
