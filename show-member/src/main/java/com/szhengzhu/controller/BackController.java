package com.szhengzhu.controller;

import com.szhengzhu.bean.member.PayBack;
import com.szhengzhu.service.PayBackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author jehon
 */
@RestController
@RequestMapping("/pay/back")
public class BackController {

    @Resource
    private PayBackService payBackService;

    @GetMapping(value = "/modify")
    public void rechargeBack(@RequestParam("payId") @NotBlank String payId) {
        payBackService.modifyPayBack(payId);
    }

    @PostMapping(value = "add")
    public void matchPayBack(@RequestBody PayBack payBack) {
        payBackService.payBack(payBack);
    }

    @PostMapping(value = "/combo/add")
    public void comboPayBack(@RequestBody PayBack payBack) {
        payBackService.payBack(payBack);
    }
}
