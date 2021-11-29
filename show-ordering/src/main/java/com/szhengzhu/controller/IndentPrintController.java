package com.szhengzhu.controller;

import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.bean.ordering.param.IncomeParam;
import com.szhengzhu.service.IndentPrintService;
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
@RequestMapping("/indent/print")
public class IndentPrintController {

    @Resource
    private IndentPrintService indentPrintService;

    @NoRepeatSubmit
    @GetMapping(value = "/preview")
    public List<String> resPreview(@RequestParam("indentId") @NotBlank String indentId,
                                           @RequestParam("employeeId") String employeeId) {
        return indentPrintService.resPrintPreview(indentId, employeeId);
    }

    @NoRepeatSubmit
    @GetMapping(value = "/bill")
    public List<String> resPrintBill(@RequestParam("indentId") @NotBlank String indentId,
                                  @RequestParam("employeeId") String employeeId) {
        return indentPrintService.resPrintBill(indentId, employeeId);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/income")
    public List<String> resPrintIncome(@RequestBody IncomeParam incomeParam) {
        return indentPrintService.resPrintIncome(incomeParam);
    }
}
