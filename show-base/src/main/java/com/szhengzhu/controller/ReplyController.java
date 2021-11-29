package com.szhengzhu.controller;

import com.szhengzhu.bean.base.ReplyInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ReplyInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Resource
    private ReplyInfoService replyInfoService;

    @GetMapping(value = "/info")
    public ReplyInfo getInfoByMsg(@RequestParam("msg") @NotBlank String msg) {
        return replyInfoService.getInfoByMsg(msg);
    }

    @GetMapping(value = "/{markId}")
    public ReplyInfo getInfo(@PathVariable("markId") @NotBlank String markId) {
        return replyInfoService.getInfo(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<ReplyInfo> page(@RequestBody PageParam<ReplyInfo> page) {
        return replyInfoService.page(page);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated ReplyInfo replyInfo) {
        replyInfoService.add(replyInfo);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated ReplyInfo replyInfo) {
        replyInfoService.modify(replyInfo);
    }
}
