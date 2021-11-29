package com.szhengzhu.controller;

import com.szhengzhu.bean.member.SignDetail;
import com.szhengzhu.bean.member.SignMember;
import com.szhengzhu.bean.member.SignRule;
import com.szhengzhu.bean.member.param.SignInfoParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.SignService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author makejava
 * @since 2021-06-07 16:15:15
 */
@Validated
@RestController
@RequestMapping("/sign")
public class SignController {
    @Resource
    private SignService signService;

    @GetMapping(value = "/{markId}")
    public SignInfoParam selectBySignMemberId(@PathVariable("markId") @NotBlank String markId) {
        return signService.selectBySignId(markId);
    }

    @PostMapping(value = "/info")
    public List<SignInfoParam> selectBySignMember(@RequestBody(required = false) SignMember signMember) {
        return signService.selectBySignMember(signMember);
    }

    @GetMapping(value = "/rule/{markId}")
    public SignRule queryBySignRuleId(@PathVariable("markId") @NotBlank String markId) {
        return signService.queryBySignRuleId(markId);
    }

    @PostMapping(value = "/rule/info")
    public List<SignRule> querySignRule(@RequestBody SignRule signRule) {
        return signService.querySignRuleList(signRule);
    }

    @PostMapping(value = "/rule/add")
    public void addSignRule(@RequestBody SignRule signRule) {
        signService.addSignRule(signRule);
    }

    @PatchMapping(value = "/rule/modify")
    public void modifySignRule(@RequestBody SignRule signRule) {
        signService.modifySignRule(signRule);
    }

    @PostMapping(value = "/detail/page")
    public PageGrid<SignDetail> querySignDetailList(PageParam<SignDetail> param) {
        return signService.querySignDetailList(param);
    }

    @PostMapping(value = "/user/show")
    public SignInfoParam queryByUserId(@RequestBody(required = false) SignMember signMember) {
        return signService.queryByUserId(signMember);
    }

    @DeleteMapping(value = "/rule/{markId}")
    public void deleteBySignRuleId(@PathVariable("markId") @NotBlank String markId) {
        signService.deleteBySignRuleId(markId);
    }
}
