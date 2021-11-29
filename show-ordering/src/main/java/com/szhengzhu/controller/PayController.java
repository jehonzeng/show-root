package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Pay;
import com.szhengzhu.bean.ordering.PayType;
import com.szhengzhu.bean.ordering.vo.PayBaseVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.PayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping(value = "/page")
    public PageGrid<Pay> page(@RequestBody PageParam<Pay> pageParam) {
        return payService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated Pay pay) {
        return new Result<>(payService.add(pay));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Pay pay) {
        payService.modify(pay);
    }

    @DeleteMapping(value = "/{payId}")
    public void delete(@PathVariable("payId") @NotBlank String payId) {
        payService.delete(payId);
    }

    @GetMapping(value = "/list/res")
    public List<PayBaseVo> resListPay(@RequestParam("storeId") @NotBlank String storeId) {
        return payService.resListPay(storeId);
    }

    @PostMapping(value = "/type/page")
    public PageGrid<PayType> pageType(@RequestBody PageParam<PayType> pageParam) {
        return payService.pageType(pageParam);
    }

    @PostMapping(value = "/type/add")
    public Result<String> addType(@RequestBody @Validated PayType payType) {
        return new Result<>(payService.addType(payType));
    }

    @PatchMapping(value = "/type/modify")
    public void modifyType(@RequestBody @Validated PayType payType) {
        payService.modifyType(payType);
    }

    @DeleteMapping(value = "/type/{typeId}")
    public void deleteType(@PathVariable("typeId") @NotBlank String typeId) {
        payService.deleteType(typeId);
    }

    @GetMapping(value = "/type/combobox")
    public List<Combobox> combobox(@RequestParam("storeId") @NotBlank String storeId) {
        return payService.getTypeCombobox(storeId);
    }
}
