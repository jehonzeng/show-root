package com.szhengzhu.controller;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.IntegralService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/integral")
public class IntegralController {

    @Resource
    private IntegralService integralService;

    @PostMapping(value = "/detail/page")
    public PageGrid<IntegralDetail> pageIntegral(@RequestBody PageParam<IntegralDetail> param) {
        return integralService.pageIntegral(param);
    }

    @PostMapping(value = "/detail/user/page")
    public PageGrid<IntegralDetail> pageUserIntegral(@RequestBody PageParam<String> param) {
        return integralService.pageUserIntegral(param);
    }

    @PostMapping(value = "/detail/add")
    public void addIntegral(@RequestBody @Validated IntegralDetail integral) {
        integralService.addTicketExchange(integral);
    }

    @PostMapping(value = "/consume")
    public void consume(@RequestBody @Validated IntegralDetail detail) {
        integralService.consume(detail);
    }

    @GetMapping(value = "/account/total")
    public Integer getTotalByUserId(@RequestParam("userId") @NotBlank String userId) {
        return integralService.getTotalByUserId(userId);
    }

    @GetMapping(value = "/account/sum/{userId}")
    public Map<String, Integer> getSumByUser(@PathVariable("userId") @NotBlank String userId) {
        return integralService.getSumByUser(userId);
    }

    @PostMapping(value = "/record")
    public PageGrid<IntegralDetail> integralRecord(@RequestBody PageParam<MemberRecord> param) {
        return integralService.integralRecord(param);
    }

    @GetMapping(value = "/exchange/{markId}")
    public IntegralExchange queryByIntegralExchangeId(@PathVariable("markId") @NotBlank String markId) {
        return integralService.queryByIntegralExchangeId(markId);
    }

    @PostMapping(value = "/exchange/list")
    public List<IntegralExchange> queryIntegralExchangeList(@RequestBody IntegralExchange integralExchange) {
        return integralService.queryIntegralExchangeList(integralExchange);
    }

    @PostMapping(value = "/exchange/add")
    public void addIntegralExchange(@RequestBody IntegralExchange integralExchange) {
        integralService.addIntegralExchange(integralExchange);
    }

    @PatchMapping(value = "/exchange/modify")
    public void modifyIntegralExchange(@RequestBody IntegralExchange integralExchange) {
        integralService.modifyIntegralExchange(integralExchange);
    }

    @PostMapping(value = "/exchange/ticket/page")
    public PageGrid<TicketExchange> queryTicketExchangePage(@RequestBody PageParam<TicketExchange> param) {
        return integralService.queryTicketExchangePage(param);
    }

    @PostMapping(value = "/exchange/ticket/list")
    public List<TicketExchange> queryTicketExchangeList(@RequestBody(required = false) TicketExchange ticketExchange) {
        return integralService.queryTicketExchangeList(ticketExchange);
    }


    @GetMapping(value = "/expire")
    public IntegralExpire queryByIntegralExpireId() {
        return integralService.queryByIntegralExpireId();
    }

    @PostMapping(value = "/expire/add")
    public void addIntegralExpire(@RequestBody IntegralExpire integralExpire) {
        integralService.addIntegralExpire(integralExpire);
    }

    @GetMapping(value = "/expire/show")
    public List<Map<String, String>> selectPushUser(@RequestParam(value = "userId", required = false) String userId) {
        return integralService.selectPushUser(userId);
    }
}
