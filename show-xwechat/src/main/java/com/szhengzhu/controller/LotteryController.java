package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.ordering.Offset;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.code.LotteryTypeCode;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@Api(tags = {"抽奖：LotteryController"})
@RestController
@RequestMapping("/v1/lottery")
public class LotteryController {

    @Resource
    private Sender sender;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "查询积分抽奖活动数据(奖品列表）")
    @GetMapping(value = "/info")
    public Result<List<Object>> queryLotteryInfo() {
        List<Object> list = new ArrayList<>();
        MemberLottery lottery = showMemberClient.selectLotteryByType(LotteryTypeCode.INTEGRAL_LOTTERY.code, 1).getData();
        list.add(lottery);
        list.add(showMemberClient.queryAllPrize(PrizeInfo.builder().status(1).lotteryId(lottery.getMarkId()).build()).getData());
        return new Result<>(list);
    }

    @ApiOperation(value = "会员积分抽奖")
    @GetMapping(value = "")
    public Result<PrizeInfo> integerLottery(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        MemberLottery lottery = showMemberClient.selectLotteryByType(LotteryTypeCode.INTEGRAL_LOTTERY.code, 1).getData();
        return memberLottery(userToken, lottery);
    }

    @ApiOperation(value = "会员新品尝鲜抽奖")
    @GetMapping(value = "/fresh")
    public Result<PrizeInfo> dishesLottery(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        MemberLottery lottery = showMemberClient.selectLotteryByType(LotteryTypeCode.FRESH_LOTTERY.code, 1).getData();
        List<PrizeInfo> prizeInfoList = showMemberClient.selectUserReceive(lottery, userToken.getUserId()).getData();
        ShowAssert.checkTrue(ObjectUtil.isNotEmpty(prizeInfoList), StatusCode._5018);
        return memberLottery(userToken, lottery);
    }

    public Result<PrizeInfo> memberLottery(UserToken userToken, MemberLottery lottery) {
        ShowAssert.checkTrue(!lottery.getStatus(), StatusCode._5043);
        ShowAssert.checkNull(showMemberClient.getMemberInfoByUserId(userToken.getUserId()).getData(), StatusCode._4049);
        Integer totalIntegral = showMemberClient.getIntegralTotalByUserId(userToken.getUserId()).getData();
        if (lottery.getConsumeIntegral() != 0) {
            ShowAssert.checkTrue((ObjectUtil.isEmpty(totalIntegral) ? 0 : totalIntegral) < lottery.getConsumeIntegral(), StatusCode._4057);
        }
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
                sender.memberLottery(id, userToken.getUserId());
                return showMemberClient.queryPrizeById(id);
            }
        }
        return null;
    }

    @ApiOperation(value = "查询用户抽奖领取记录")
    @GetMapping(value = "/receive/list")
    public Result<PageGrid<PrizeReceive>> prizeReceiveByPage(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        PageParam<PrizeReceive> param = new PageParam<>();
        PrizeReceive prizeReceive = PrizeReceive.builder().userId(userToken.getUserId()).build();
        param.setData(prizeReceive);
        param.setPageSize(30);
        return showMemberClient.prizeReceiveByPage(param);
    }

    @ApiOperation(value = "查询新品尝鲜抽奖活动数据(奖品列表）")
    @GetMapping(value = "/fresh/info")
    public Result<List<Object>> queryDishesLottery(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        MemberAccount memberAccount = showMemberClient.getMemberInfoByUser(userToken.getUserId()).getData();
        ShowAssert.checkNull(memberAccount, StatusCode._4049);
        MemberLottery lottery = showMemberClient.selectLotteryByType(LotteryTypeCode.FRESH_LOTTERY.code, 1).getData();
        List<PrizeInfo> prizeInfoList = showMemberClient.selectUserReceive(lottery, userToken.getUserId()).getData();
        List<Object> list = new ArrayList<>();
        list.add(lottery);
        list.add(showMemberClient.queryAllPrize(PrizeInfo.builder().status(1).lotteryId(lottery.getMarkId()).build()).getData());
        if (ObjectUtil.isEmpty(prizeInfoList)) {
            list.add(1);
        }
        return new Result<>(list);
    }

    @ApiOperation(value = "根据id查询抽奖活动详细信息")
    @GetMapping(value = "/detail/{lotteryId}")
    public Result<LotteryInfo> queryInfoById(@PathVariable("lotteryId") String lotteryId) {
        return showMemberClient.queryInfoById(lotteryId);
    }
}
