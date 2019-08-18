package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.SysService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping("/sys")
public class SysController {

    @Resource
    private SysService sysService;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Result<String> getByName(@PathVariable("name") String name) {
        return sysService.getByName(name);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modifySys(@RequestBody SysInfo sysInfo) {
        return sysService.modifySys(sysInfo);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public Result<?> refreshMenu(@RequestParam("name") String name) {
        Result<String> dataResult = sysService.getByName(name);
        if (dataResult.getCode().equals("200") && StringUtils.isEmpty(dataResult.getData())) {
            return new Result<>(StatusCode._4008);
        }
        return new Result<>();
    }
}
