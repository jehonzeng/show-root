package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.MemberRecord;
import com.szhengzhu.bean.member.param.MemberPaymentParam;
import com.szhengzhu.bean.member.param.MemberRecordParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MemberDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/member/detail")
public class MemberDetailController {

    @Resource
    private MemberDetailService memberDetailService;

    @PostMapping(value = "/page")
    public PageGrid<MemberDetail> pageDetail(@RequestBody PageParam<MemberDetail> param) {
        return memberDetailService.pageDetail(param);
    }

    @PostMapping(value = "/user/page")
    public PageGrid<MemberDetail> pageUserDetail(@RequestBody PageParam<String> param) {
        return memberDetailService.pageUserDetail(param);
    }

    @DeleteMapping(value = "/{detailId}")
    public void deleteDetail(@PathVariable("detailId") @NotBlank String detailId, @RequestParam(value = "userId", required = false) String userId) {
        memberDetailService.deleteDetail(detailId, userId);
    }

    @PostMapping(value = "/record")
    public PageGrid<MemberRecordParam> memberRecord(@RequestBody PageParam<MemberRecord> param) {
        return memberDetailService.memberRecord(param);
    }

    @PostMapping(value = "/total")
    public Map<String, Object> memberDetailTotal(@RequestBody MemberRecord memberRecord) {
        return memberDetailService.memberDetailTotal(memberRecord);
    }

    @PostMapping(value = "/payment")
    public PageGrid<MemberPaymentParam> memberPayment(@RequestBody PageParam<MemberRecord> param) {
        return memberDetailService.memberPayment(param);
    }

    @GetMapping(value = "/markId")
    public MemberDetail selectByMarkId(@RequestParam(value = "markId") @NotBlank String markId) {
        return memberDetailService.selectByMarkId(markId);
    }
}
