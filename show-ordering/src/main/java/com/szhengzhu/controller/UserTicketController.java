package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.ordering.param.GiveParam;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;
import com.szhengzhu.service.UserTicketService;
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
@RequestMapping("/userTicket")
public class UserTicketController {

    @Resource
    private UserTicketService userTicketService;

    @PostMapping(value = "/give/res")
    public void resGive(@RequestBody @Validated GiveParam giveParam) {
        userTicketService.resGiveTicket(giveParam);
    }

    @GetMapping(value = "/list")
    public List<UserTicketVo> resList(@RequestParam("userId") @NotBlank String userId, @RequestParam(value = "status", required = false) Integer status) {
        return userTicketService.listUserTicket(userId, status);
    }

    @GetMapping(value = "/list/res")
    public List<UserTicketVo> resList(@RequestParam("userId") @NotBlank String userId) {
        return userTicketService.resUserTicket(userId);
    }

    @GetMapping(value = "/list/indent/x")
    public List<UserTicketVo> xlistByIndent(@RequestParam("userId") @NotBlank String userId, @RequestParam("indentId") @NotBlank String indentId) {
        return userTicketService.xlistUserTicketByIndent(userId, indentId);
    }

    @GetMapping(value = "/select/{markId}")
    public List<MemberTicket> memberTicket(@PathVariable("markId") @NotBlank String markId) {
        return userTicketService.memberTicket(markId);
    }

    @DeleteMapping(value = "/del/{markId}")
    public void deleteMemberTicket(@PathVariable("markId") @NotBlank String markId) {
        userTicketService.deleteMemberTicket(markId);
    }
}
