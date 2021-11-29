package com.szhengzhu.controller;

import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.SysService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/sys")
public class SysController {

    @Resource
    private SysService sysService;

    @GetMapping(value = "/{name}")
    public Result<String> getByName(@PathVariable("name") @NotBlank String name) {
        return new Result<>(sysService.getByName(name));
    }

    @PatchMapping(value = "/modify")
    public void modifySys(@RequestBody @Validated SysInfo sysInfo) {
        sysService.modifySys(sysInfo);
    }

    @GetMapping(value = "/refresh")
    public Result<String> refreshMenu(@RequestParam("name") @NotBlank String name) {
        return new Result<>(sysService.getByName(name));
    }
}
