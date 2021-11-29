package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.DiscountInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.DiscountService;
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
@RequestMapping(value = "discount")
public class DiscountController {

    @Resource
    private DiscountService discountService;

    @PostMapping(value = "/add")
    public void addDiscount(@RequestBody DiscountInfo base) {
        discountService.addDiscount(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<DiscountInfo> pageTemplate(@RequestBody PageParam<DiscountInfo> base) {
        return discountService.getPage(base);
    }

    @GetMapping(value = "/{markId}")
    public DiscountInfo getDiscountInfo(@PathVariable("markId") @NotBlank String markId) {
        return discountService.getDiscountInfo(markId);
    }

    @PatchMapping(value = "/modify")
    public void modifyDiscount(@RequestBody @Validated DiscountInfo base) {
        discountService.modifyDiscount(base);
    }

    @DeleteMapping(value = "/{discountId}")
    public void deleteDiscount(@PathVariable("discountId") @NotBlank String discountId) {
        discountService.deleteDiscount(discountId);
    }

    @PatchMapping(value = "/batch/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] discountIds, @PathVariable("status") @NotNull Integer status) {
        discountService.modifyStatus(discountIds, status);
    }

    @GetMapping(value = "/combobox/res")
    public List<Combobox> listCombobox(@RequestParam("employeeId") String employeeId,
            @RequestParam("storeId") @NotBlank String storeId) {
        return discountService.listCombobox(employeeId, storeId);
    }
}
