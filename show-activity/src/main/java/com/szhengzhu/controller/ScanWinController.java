package com.szhengzhu.controller;

import com.szhengzhu.bean.activity.ScanWin;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.ScanWinService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/scanwin")
public class ScanWinController {

    @Resource
    private ScanWinService scanWinService;

    @PostMapping(value = "/page")
    public PageGrid<ScanWin> page(@RequestBody PageParam<ScanWin> page) {
        return scanWinService.page(page);
    }

    @GetMapping(value = "/{markId}")
    public ScanWin getInfo(@PathVariable("markId") @NotBlank String markId) {
        return scanWinService.getInfo(markId);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated ScanWin win) {
        scanWinService.add(win);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated ScanWin win) {
        scanWinService.modify(win);
    }

    @GetMapping(value = "/win")
    public Result<String> scanWin(@RequestParam("scanCode") @NotBlank String scanCode, @RequestParam("openId") @NotBlank String openId) {
        return new Result<>(scanWinService.scanWin(scanCode, openId));
    }
}
