package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MatchPay;
import com.szhengzhu.service.MatchPayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jehon
 */
@RestController
@RequestMapping("/match/pay")
public class MatchPayController {

    @Resource
    private MatchPayService matchPayService;

    @GetMapping(value = "/add")
    public MatchPay addMatchPay(@RequestParam("matchId") String matchId, @RequestParam("quantity") Integer quantity,
                                @RequestParam("userId") String userId) {
        return matchPayService.matchPay(matchId, quantity, userId);
    }
}
