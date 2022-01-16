package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"配置管理：SysController"})
@RestController
@RequestMapping("/v1/sys")
public class SysController {

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private WechatService wechatService;

    @ApiOperation(value = "根据名称获取数据", notes = "根据名称获取数据")
    @GetMapping(value = "/{name}")
    public Result<String> getByName(@PathVariable("name") @NotBlank String name) {
        return showBaseClient.getByName(name);
    }

    @ApiOperation(value = "根据名称修改数据", notes = "根据名称修改数据")
    @PatchMapping(value = "")
    public Result<?> modifySys(@RequestBody @Validated SysInfo sysInfo) {
        return showBaseClient.modifySys(sysInfo);
    }

    @ApiOperation(value = "将数据刷新到微信平台", notes = "将数据刷新到微信平台")
    @GetMapping(value = "/refresh")
    public Result<?> refreshMenu(@RequestParam("name") @NotBlank String name) {
        Result<String> jsonResult = showBaseClient.getByName(name);
        if (!jsonResult.getCode().equals("200")) {
            return jsonResult;
        }
        return wechatService.refreshMenu(jsonResult.getData());
    }
}
