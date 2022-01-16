package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.ordering.Offset;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.code.LotteryTypeCode;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Validated
@Slf4j
@Api(tags = {"抽奖：LotteryController"})
@RestController
@RequestMapping("/v1/lottery")
public class LotteryController {

    @Resource
    private Sender sender;

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "根据type查询抽奖活动数据")
    @GetMapping(value = "/type")
    public Result<MemberLottery> selectLotteryByType(@RequestParam("type") Integer type) {
        return showMemberClient.selectLotteryByType(type, null);
    }

    @ApiOperation(value = "查询抽奖活动数据")
    @GetMapping(value = "/info/{markId}")
    public Result<MemberLottery> queryLottery(@PathVariable("markId") String markId) {
        return showMemberClient.queryLottery(markId);
    }

    @ApiOperation(value = "添加抽奖活动数据")
    @PostMapping(value = "")
    public Result addLottery(@RequestBody MemberLottery memberLottery, HttpSession session) {
        memberLottery.setCreator((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.addLottery(memberLottery);
    }

    @ApiOperation(value = "查询奖品数据(查询奖品列表）")
    @PostMapping(value = "/prize/list")
    public Result<List<PrizeInfo>> queryAllPrize(@RequestBody PrizeInfo prizeInfo) {
        return showMemberClient.queryAllPrize(prizeInfo);
    }

    @ApiOperation(value = "添加奖品数据")
    @PostMapping(value = "/prize")
    public Result addPrize(@RequestBody PrizeInfo prizeInfo) {
        return showMemberClient.addPrize(prizeInfo);
    }

    @ApiOperation(value = "修改奖品数据")
    @PatchMapping(value = "/prize")
    public Result modifyPrize(@RequestBody PrizeInfo prizeInfo) {
        return showMemberClient.modifyPrize(prizeInfo);
    }

    @ApiOperation(value = "删除奖品数据")
    @DeleteMapping(value = "/prize/{markId}")
    public Result deletePrizeById(@PathVariable("markId") String markId) {
        return showMemberClient.deletePrizeById(markId);
    }

    @ApiOperation(value = "查询奖品领取记录")
    @PostMapping(value = "/receive/list")
    public Result<PageGrid<PrizeReceive>> prizeReceiveByPage(@RequestBody PageParam<PrizeReceive> param) {
        return showMemberClient.prizeReceiveByPage(param);
    }

    @ApiOperation(value = "根据id查询抽奖活动详细信息")
    @GetMapping(value = "/detail/{lotteryId}")
    public Result<LotteryInfo> queryInfoById(@PathVariable("lotteryId") String lotteryId) {
        return showMemberClient.queryInfoById(lotteryId);
    }

    @ApiOperation(value = "添加抽奖活动详细信息")
    @PostMapping(value = "/detail")
    public Result addLotteryInfo(@RequestBody LotteryInfo info) {
        showMemberClient.addLottery(MemberLottery.builder().markId(info.getLotteryId()).startTime(info.getStartTime()).
                endTime(info.getEndTime()).build());
        return showMemberClient.addLotteryInfo(info);
    }

    @ApiOperation(value = "修改抽奖活动详细信息")
    @PatchMapping(value = "/detail")
    public Result modifyLotteryInfo(@RequestBody LotteryInfo info) {
        showMemberClient.addLottery(MemberLottery.builder().markId(info.getLotteryId()).startTime(info.getStartTime()).
                endTime(info.getEndTime()).build());
        return showMemberClient.modifyLotteryInfo(info);
    }

    @ApiOperation(value = "查询新品尝鲜抽奖活动数据(奖品列表）")
    @PostMapping(value = "/test")
    public Result<List<Object>> queryDishesLottery(@RequestParam("userId") String userId) {
        MemberAccount memberAccount = showMemberClient.getMemberInfoByUser(userId).getData();
        ShowAssert.checkNull(memberAccount, StatusCode._4049);
        MemberLottery lottery = showMemberClient.selectLotteryByType(LotteryTypeCode.FRESH_LOTTERY.code, 1).getData();
        List<PrizeInfo> prizeInfoList = showMemberClient.selectUserReceive(lottery, userId).getData();
        List<Object> list = new ArrayList<>();
        list.add(lottery);
        list.add(showMemberClient.queryAllPrize(PrizeInfo.builder().status(1).lotteryId(lottery.getMarkId()).build()).getData());
        if (ObjectUtil.isNotEmpty(prizeInfoList)) {
            return new Result<>(StatusCode._5018, list);
        } else {
            return new Result<>(list);
        }
    }

    @ApiOperation(value = "会员新品尝鲜抽奖")
    @PostMapping(value = "/fresh")
    public Result<PrizeInfo> dishesLottery(@RequestParam("userId") String userId) {
        MemberLottery lottery = showMemberClient.selectLotteryByType(LotteryTypeCode.FRESH_LOTTERY.code, 1).getData();
        List<PrizeInfo> prizeInfoList = showMemberClient.selectUserReceive(lottery, userId).getData();
        ShowAssert.checkTrue(ObjectUtil.isNotEmpty(prizeInfoList), StatusCode._5018);
        return memberLottery(userId, lottery);
    }

    public Result<PrizeInfo> memberLottery(String userId, MemberLottery lottery) {
        ShowAssert.checkTrue(!lottery.getStatus(), StatusCode._5043);
        ShowAssert.checkNull(showMemberClient.getMemberInfoByUserId(userId).getData(), StatusCode._4049);
        Integer totalIntegral = showMemberClient.getIntegralTotalByUserId(userId).getData();
        ShowAssert.checkTrue((ObjectUtil.isEmpty(totalIntegral) ? 0 : totalIntegral) < lottery.getConsumeIntegral(), StatusCode._4057);
        int start = 0;// 区间开始
        int endNum = 0;//数量
        Map<String, Offset> offsetMap = new HashMap<>();
        List<PrizeInfo> prizeList = showMemberClient.queryAllPrize(PrizeInfo.builder().status(1).lotteryId(lottery.getMarkId()).limitNum(8).quantity(0).build()).getData();
        for (PrizeInfo prizeInfo : prizeList) {
            int span = new BigDecimal(prizeInfo.getProbability().floatValue() * lottery.getMultiple()).intValue();
            int end = start + span;
            offsetMap.put(prizeInfo.getMarkId(), Offset.builder().percent(prizeInfo.getProbability()).span(span).start(start).end(end).build());
            start = end;
            endNum += span;
        }
        int num = RandomUtil.randomInt(0, endNum);
        for (String id : offsetMap.keySet()) {
            Offset offset1 = offsetMap.get(id);
            if (offset1.getStart() <= num && num < offset1.getEnd()) {
                sender.memberLottery(id, userId);
                return showMemberClient.queryPrizeById(id);
            }
        }
        return null;
    }
}

