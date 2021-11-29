package com.szhengzhu.controller;

import com.szhengzhu.bean.excel.VoucherCodeExcel;
import com.szhengzhu.bean.ordering.Voucher;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.VoucherService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private VoucherService voucherService;

    @PostMapping(value = "/page")
    public PageGrid<Voucher> page(@RequestBody  PageParam<Voucher> param) {
        return voucherService.page(param);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Voucher voucher) throws InterruptedException {
        voucherService.add(voucher);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Voucher voucher) {
        voucherService.modify(voucher);
    }

    @GetMapping(value = "/code/list")
    public List<VoucherCodeExcel> listCode(@RequestParam("voucherId") @NotBlank String voucherId) {
         return voucherService.listCode(voucherId);
    }

    @GetMapping(value = "/code/{code}")
    public Voucher getCodeInfo(@PathVariable("code") @NotBlank String code) {
        return voucherService.getCodeInfo(code);
    }

    @GetMapping(value = "/code/use")
    public void useCode(@RequestParam @NotBlank String code, @RequestParam @NotNull Integer amount) {
        voucherService.useCode(code, amount);
    }
}
