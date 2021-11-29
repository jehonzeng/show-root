package com.szhengzhu.controller;

import com.szhengzhu.bean.member.GradeRecord;
import com.szhengzhu.bean.member.GradeTicket;
import com.szhengzhu.bean.member.MemberGrade;
import com.szhengzhu.bean.member.MemberGradeShow;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MemberGradeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author makejava
 * @since 2021-04-21 10:57:10
 */
@Validated
@RestController
@RequestMapping("/member/grade")
public class MemberGradeController {

    @Resource
    private MemberGradeService memberGradeService;

    @GetMapping(value = "/{markId}")
    public MemberGrade selectOne(@PathVariable("markId") @NotBlank String markId) {
        return memberGradeService.queryById(markId);
    }

    @PostMapping(value = "/queryAll")
    public List<MemberGrade> queryAll(@RequestBody MemberGrade memberGrade) {
        return memberGradeService.queryAll(memberGrade);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody MemberGrade memberGrade) {
        memberGradeService.add(memberGrade);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody MemberGrade memberGrade) {
        memberGradeService.modify(memberGrade);
    }

    @DeleteMapping(value = "/{markId}")
    public void delete(@PathVariable("markId") @NotBlank String markId) {
        memberGradeService.deleteById(markId);
    }

    @GetMapping(value = "/show")
    public List<MemberGradeShow> memberGradeShow(@RequestParam("userId") String userId) {
        return memberGradeService.memberGradeShow(userId);
    }

    @GetMapping(value = "/ticket")
    public List<GradeTicket> queryGradeTicket(@RequestParam("gradeId") @NotBlank String gradeId) {
        return memberGradeService.queryGradeTicket(gradeId);
    }

    @GetMapping(value = "/sort")
    public MemberGrade selectByGradeId(@RequestParam("amount") @NotNull Integer amount) {
        return memberGradeService.selectByGradeId(amount);
    }

    @PostMapping(value = "/consume/record")
    public PageGrid<GradeRecord> queryGradeRecord(@RequestBody PageParam<GradeRecord> param) {
        return memberGradeService.queryGradeRecord(param);
    }
}
