package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.member.LotteryInfo;
import com.szhengzhu.bean.member.MemberLottery;
import com.szhengzhu.bean.member.PrizeInfo;
import com.szhengzhu.bean.member.PrizeReceive;
import com.szhengzhu.code.LotteryTypeCode;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.LotteryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author makejava
 * @since 2021-03-01 10:46:05
 */
@Validated
@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Resource
    private LotteryService lotteryService;

    @GetMapping(value = "/type")
    public MemberLottery selectLotteryByType(@RequestParam("type") Integer type,
                                             @RequestParam(value = "status", required = false) Integer status) {
        return lotteryService.selectLotteryByType(type, status);
    }

    @GetMapping(value = "/info")
    public MemberLottery queryLottery(@RequestParam("markId") String markId) {
        return lotteryService.queryLottery(markId);
    }

    @PostMapping(value = "/add")
    public void addLottery(@RequestBody MemberLottery memberLottery) {
        lotteryService.addLottery(memberLottery);
    }

    @GetMapping(value = "/prize/{markId}")
    public PrizeInfo queryPrizeById(@PathVariable("markId") String markId) {
        return lotteryService.queryPrizeById(markId);
    }

    @PostMapping(value = "/prize/list")
    public List<PrizeInfo> queryAllPrize(@RequestBody PrizeInfo prizeInfo) {
        return lotteryService.queryAllPrize(prizeInfo);
    }

    @PostMapping(value = "/prize/add")
    public void addPrize(@RequestBody PrizeInfo prizeInfo) {
        lotteryService.addPrize(prizeInfo);
    }

    @PatchMapping(value = "/prize/modify")
    public void modifyPrize(@RequestBody PrizeInfo prizeInfo) {
        lotteryService.modifyPrize(prizeInfo);
    }

    @PostMapping(value = "/receive/list")
    public PageGrid<PrizeReceive> prizeReceiveByPage(@RequestBody PageParam<PrizeReceive> param) {
        return lotteryService.queryReceiveAll(param);
    }

    @PostMapping(value = "/receive/add")
    public void addReceive(@RequestBody PrizeReceive prizeReceive) {
        lotteryService.addReceive(prizeReceive);
    }

    @DeleteMapping(value = "/prize/{markId}")
    public void deletePrizeById(@PathVariable("markId") String markId) {
        lotteryService.deletePrizeById(markId);
    }

    @PostMapping(value = "/user/receive")
    public List<PrizeInfo> selectUserReceive(@RequestBody MemberLottery lottery, @RequestParam("userId") String userId) {
        return lotteryService.selectUserReceive(lottery, userId);
    }

    @GetMapping(value = "/detail/{lotteryId}")
    public LotteryInfo queryInfoById(@PathVariable("lotteryId") String lotteryId) {
        return lotteryService.queryInfoById(lotteryId);
    }

    @PostMapping(value = "/detail/add")
    public void addLotteryInfo(@RequestBody LotteryInfo lotteryInfo) {
        lotteryService.addLotteryInfo(lotteryInfo);
    }

    @PatchMapping(value = "/detail/modify")
    public void modifyLotteryInfo(@RequestBody LotteryInfo lotteryInfo) {
        lotteryService.modifyLotteryInfo(lotteryInfo);
    }

    @GetMapping(value = "/receive")
    public List<PrizeReceive> selectReceive() {
        return lotteryService.selectReceive();
    }
}
