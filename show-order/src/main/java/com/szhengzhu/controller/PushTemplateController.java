package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.order.PushInfo;
import com.szhengzhu.bean.order.PushInfoVo;
import com.szhengzhu.bean.order.PushTemplate;
import com.szhengzhu.bean.order.PushType;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.PushTemplateService;
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
@RequestMapping("/push")
public class PushTemplateController {
    @Resource
    private PushTemplateService pushTemplateService;

    @GetMapping(value = "/{markId}")
    public PushInfo queryPushInfoById(@PathVariable("markId") @NotBlank String markId) {
        return pushTemplateService.queryPushInfoById(markId);
    }

    @PostMapping(value = "/template/list")
    public PageGrid<PushTemplate> queryPushTemplateList(@RequestBody PageParam<PushTemplate> param) {
        return pushTemplateService.queryPushTemplateList(param);
    }

    @PostMapping(value = "/info/list")
    public PageGrid<PushInfo> queryPushInfoList(@RequestBody PageParam<PushInfo> param) {
        return pushTemplateService.queryPushInfoList(param);
    }

    @GetMapping(value = "/info/{templateId}")
    public List<PushInfo> queryPushInfoByTemplateId(@PathVariable("templateId") @NotBlank String templateId) {
        return pushTemplateService.queryPushInfoByTemplateId(templateId);
    }

    @GetMapping(value = "/use/{markId}")
    public void usePushInfo(@PathVariable("markId") @NotBlank String markId) {
        pushTemplateService.usePushInfo(markId);
    }

    @PostMapping(value = "/template/add")
    public void addPushTemplate(@RequestBody PushInfoVo pushInfoVo) {
        pushTemplateService.addPushTemplate(pushInfoVo);
    }

    @PatchMapping(value = "/template/modify")
    public void modifyPushTemplate(@RequestBody PushTemplate pushTemplate) {
        pushTemplateService.modifyPushTemplate(pushTemplate);
    }

    @PostMapping(value = "/info/add")
    public void addPushInfo(@RequestBody PushInfoVo pushInfoVo) {
        pushTemplateService.addPushInfo(pushInfoVo);
    }

    @PatchMapping(value = "/info/modify")
    public void modifyPushInfo(@RequestBody PushInfoVo pushInfoVo) {
        pushTemplateService.modifyPushInfo(pushInfoVo);
    }

    @DeleteMapping(value = "/template/{markId}")
    public void deletePushTemplateById(@PathVariable("markId") @NotBlank String markId) {
        pushTemplateService.deletePushTemplateById(markId);
    }

    @DeleteMapping(value = "/info/{markId}")
    public void deletePushInfoById(@PathVariable("markId") @NotBlank String markId) {
        pushTemplateService.deletePushInfoById(markId);
    }

    @GetMapping(value = "/type/list")
    public List<PushType> queryPushTypeList() {
        return pushTemplateService.queryPushTypeList();
    }

    @PostMapping(value = "/type/page")
    public PageGrid<PushType> queryPushTypeByPage(@RequestBody PageParam<PushType> param) {
        return pushTemplateService.queryPushTypeByPage(param);
    }

    @PostMapping(value = "/type/add")
    public void addPushType(@RequestBody PushType pushType) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        pushType.setMarkId(snowflake.nextIdStr());
        pushType.setCreateTime(DateUtil.date());
        pushTemplateService.addPushType(pushType);
    }

    @DeleteMapping(value = "/type/{markId}")
    public void deletePushTypeById(@PathVariable("markId") String markId) {
        pushTemplateService.deletePushTypeById(markId);
    }

    @GetMapping(value = "/template")
    public PushInfo queryPushTemplate(@RequestParam("modelId") @NotBlank String modelId,
                                      @RequestParam(value = "typeId", required = false) String typeId){
        return pushTemplateService.queryPushTemplate(modelId,typeId);
    }
}
