package com.szhengzhu.controller;

import com.szhengzhu.bean.order.PushInfo;
import com.szhengzhu.bean.order.PushInfoVo;
import com.szhengzhu.bean.order.PushTemplate;
import com.szhengzhu.bean.order.PushType;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author makejava
 * @since 2021-07-29 14:35:54
 */
@Validated
@RestController
@Api(tags = {"推送模板：PushTemplateController"})
@RequestMapping("/v1/push")
public class PushTemplateController {
    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "根据id获取推送模板信息")
    @GetMapping(value = "/{markId}")
    public Result<PushInfo> queryPushInfoById(@PathVariable("markId") @NotBlank String markId) {
        return showOrderClient.queryPushInfoById(markId);
    }

    @ApiOperation(value = "获取推送模板信息分页列表")
    @PostMapping(value = "/template/list")
    public Result<PageGrid<PushTemplate>> queryPushTemplateList(@RequestBody PageParam<PushTemplate> param) {
        return showOrderClient.queryPushTemplateList(param);
    }

    @ApiOperation(value = "获取推送模板详细信息分页列表")
    @PostMapping(value = "/info/list")
    public Result<PageGrid<PushInfo>> queryPushInfoList(@RequestBody PageParam<PushInfo> param) {
        return showOrderClient.queryPushInfoList(param);
    }

    @ApiOperation(value = "根据id获取推送模板详细信息")
    @GetMapping(value = "/info/{templateId}")
    public Result<List<PushInfo>> queryPushInfoByTemplateId(@PathVariable("templateId") @NotBlank String templateId) {
        return showOrderClient.queryPushInfoByTemplateId(templateId);
    }

    @ApiOperation(value = "使用该推送模板信息")
    @GetMapping(value = "/use/{markId}")
    public Result usePushInfo(@PathVariable("markId") @NotBlank String markId) {
        return showOrderClient.usePushInfo(markId);
    }

    @ApiOperation(value = "添加推送模板")
    @PostMapping(value = "/template/add")
    public Result addPushTemplate(@RequestBody PushInfoVo pushInfoVo) {
        return showOrderClient.addPushTemplate(pushInfoVo);
    }

    @ApiOperation(value = "修改推送模板信息")
    @PatchMapping(value = "/template/modify")
    public Result modifyPushTemplate(@RequestBody PushTemplate pushTemplate) {
        return showOrderClient.modifyPushTemplate(pushTemplate);
    }

    @ApiOperation(value = "添加推送模板详细信息")
    @PostMapping(value = "/info")
    public Result addPushInfo(@RequestBody PushInfoVo infoVo) {
        return showOrderClient.addPushInfo(infoVo);
    }

    @ApiOperation(value = "修改推送模板详细信息")
    @PatchMapping(value = "/info")
    public Result modifyPushInfo(@RequestBody PushInfoVo infoVo) {
        return showOrderClient.modifyPushInfo(infoVo);
    }

    @ApiOperation(value = "删除推送模板信息")
    @DeleteMapping(value = "/template/{markId}")
    public Result deletePushTemplateById(@PathVariable("markId") @NotBlank String markId) {
        return showOrderClient.deletePushTemplateById(markId);
    }

    @ApiOperation(value = "删除推送模板详细信息")
    @DeleteMapping(value = "/info/{markId}")
    public Result deletePushInfoById(@PathVariable("markId") @NotBlank String markId) {
        return showOrderClient.deletePushInfoById(markId);
    }

    @ApiOperation(value = "展示信息类型列表")
    @GetMapping(value = "/type/list")
    public Result<List<PushType>> queryPushTypeList() {
        return showOrderClient.queryPushTypeList();
    }

    @ApiOperation(value = "分页展示信息类型列表")
    @PostMapping(value = "/type/page")
    public Result<PageGrid<PushType>> queryPushTypeByPage(@RequestBody PageParam<PushType> param) {
        return showOrderClient.queryPushTypeByPage(param);
    }

    @ApiOperation(value = "添加信息类型")
    @PostMapping(value = "/type")
    public Result addPushType(@RequestBody PushType pushType) {
        return showOrderClient.addPushType(pushType);
    }

    @ApiOperation(value = "删除信息类型")
    @DeleteMapping(value = "/type/{markId}")
    public Result deletePushTypeById(@PathVariable("markId") String markId){
        return showOrderClient.deletePushTypeById(markId);
    }
}
