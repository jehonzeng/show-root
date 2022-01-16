package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.bean.member.Activity;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author jehon
 */
@Validated
@Api(tags = { "活动：ActivityController" })
@RestController
@RequestMapping("/v1/activity")
public class ActivityController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "根据分类code获取活动列表")
    @GetMapping(value = "/listbycode")
    public Result<Activity> getInfoByCode(@RequestParam("code") @NotBlank String code) {
        return showMemberClient.getActivityWellInfoByCode(code);
    }
}
