package com.szhengzhu.controller;

import com.szhengzhu.bean.member.RechargeRule;
import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.*;
import com.szhengzhu.service.RechargeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/recharge")
public class RechargeController {

    @Resource
    private RechargeService rechargeService;

    @PostMapping(value = "/page")
    public PageGrid<RechargeRule> page(@RequestBody PageParam<RechargeRule> base){
        return rechargeService.page(base);
    }

    @GetMapping(value = "/{markId}")
    public RechargeRule getInfo(@PathVariable("markId") @NotBlank String markId){
        return rechargeService.getInfo(markId);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated RechargeRule base) {
        rechargeService.add(base);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated RechargeRule base){
        rechargeService.modify(base);
    }

    @DeleteMapping(value = "/{ruleId}")
    public void delete(@PathVariable("ruleId") @NotBlank String ruleId){
        rechargeService.delete(ruleId);
    }

    @PatchMapping(value = "/batch/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] ruleIds, @PathVariable("status") @NotNull Integer status){
        rechargeService.modifyStatus(ruleIds,status);
    }

    @GetMapping(value = "/list")
    public List<RechargeRule> list() {
        return rechargeService.list();
    }

    @GetMapping(value = "/ticket/{ruleId}")
    public List<TicketTemplateVo> getRechargeTickets(@PathVariable("ruleId") @NotBlank String ruleId){
        return rechargeService.getRechargeTickets(ruleId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox() {
        return rechargeService.listCombobox();
    }
}
