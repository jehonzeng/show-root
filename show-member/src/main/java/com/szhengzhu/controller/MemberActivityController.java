package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.bean.member.MemberActivity;
import com.szhengzhu.bean.member.ReceiveTicket;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MemberActivityService;
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
@RequestMapping("/member/activity")
public class MemberActivityController {

    @Resource
    private MemberActivityService memberActivityService;

    @PostMapping(value = "/info")
    public PageGrid<MemberActivity> memberActivity(@RequestBody PageParam<MemberActivity> param) {
        return memberActivityService.memberActivityByPage(param);
    }

    @PostMapping(value = "/add")
    public void addActivity(@RequestBody @Validated MemberActivity memberActivity) {
        memberActivityService.addActivity(memberActivity);
    }

    @PatchMapping(value = "/modify")
    public void modifyActivity(@RequestBody MemberActivity memberActivity) {
        memberActivityService.modifyActivity(memberActivity);
    }

    @PatchMapping(value = "/{markId}")
    public void statusActivity(@PathVariable("markId") @NotBlank String markId) {
        MemberActivity memberActivity = memberActivityService.queryActivityById(markId);
        if (memberActivity.getStatus() == 1) {
            memberActivityService.modifyActivity(MemberActivity.builder().markId(markId).status(0).
                    modifyTime(DateUtil.date()).build());
        } else {
            memberActivityService.modifyActivity(MemberActivity.builder().markId(markId).status(1).
                    modifyTime(DateUtil.date()).build());
        }
    }

    @GetMapping(value = "/ticket")
    public List<ReceiveTicket> queryById(@RequestParam("receiveId") @NotBlank String receiveId) {
        return memberActivityService.queryById(receiveId);
    }

    @DeleteMapping(value = "/{markId}")
    public void deleteActivity(@PathVariable("markId") @NotBlank String markId) {
        memberActivityService.deleteActivity(markId);
    }
}
