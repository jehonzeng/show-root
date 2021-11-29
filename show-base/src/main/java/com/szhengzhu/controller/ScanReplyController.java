package com.szhengzhu.controller;

import com.szhengzhu.bean.base.ScanReply;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ScanReplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/scanreply")
public class ScanReplyController {

    @Resource
    private ScanReplyService scanReplyService;

    @GetMapping(value = "/list/code")
    public List<ScanReply> listByCode(@RequestParam("code") @NotBlank String code) {
        return scanReplyService.listByCode(code);
    }

    @GetMapping(value = "/{markId}")
    public ScanReply getInfo(@PathVariable("markId") @NotBlank String markId) {
        return scanReplyService.getInfo(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<ScanReply> page(@RequestBody PageParam<ScanReply> page) {
        return scanReplyService.page(page);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated ScanReply replyInfo) {
        scanReplyService.add(replyInfo);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated ScanReply replyInfo) {
        scanReplyService.modify(replyInfo);
    }
}
