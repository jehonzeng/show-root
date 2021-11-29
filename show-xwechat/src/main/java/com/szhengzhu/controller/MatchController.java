package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MatchPay;
import com.szhengzhu.bean.member.PayBack;
import com.szhengzhu.bean.member.param.MatchVoteParam;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.util.UserUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author jehon
 */
@Api(tags = {"竞赛活动：MatchController"})
@Validated
@RestController
@RequestMapping("/v1/match")
public class MatchController {

    @Resource
    private WechatConfig config;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取竞赛列表")
    @GetMapping(value = "/list")
    public Result getMatchList() {
        return showMemberClient.listMatch();
    }

    @ApiOperation(value = "生成有时效性的条形码（兑换券）")
    @GetMapping(value = "/exchange/mark")
    public Result<Map<String, Object>> createMatchExchangeMark(HttpServletRequest request, @RequestParam("matchId") String matchId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.createMatchExchangeMark(matchId, userToken.getUserId());
    }

    @ApiOperation(value = "获取参与竞赛活动的队伍列表")
    @GetMapping(value = "/team/match/list")
    public Result getMatchTeam(HttpServletRequest request, @RequestParam("matchId") @NotBlank String matchId) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        return showMemberClient.listMatchTeamList(userInfo.getMarkId(), matchId);
    }

    @ApiOperation(value = "获取用户可投票次数")
    @GetMapping(value = "/vote/count")
    public Result getVoteCount(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.getUserVoteCount(userToken.getUserId());
    }

    @ApiOperation(value = "进行投票")
    @PostMapping(value = "/vote/team")
    public Result voteTeams(HttpServletRequest request, @RequestBody @Validated MatchVoteParam matchVoteParam) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        matchVoteParam.setUserId(userToken.getUserId());
        return showMemberClient.voteMatchTeams(matchVoteParam);
    }

    @ApiOperation(value = "获取已投票的队伍信息列表")
    @GetMapping(value = "/vote/team/list")
    public Result listVote(HttpServletRequest request, @RequestParam("matchId") String matchId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showMemberClient.listVoteTeam(userToken.getUserId(), matchId);
    }

    @ApiOperation(value = "会员支付获取投票次数")
    @GetMapping(value = "/pay")
    public Result pay(HttpServletRequest request, @RequestParam("matchId") String matchId, @RequestParam("quantity") Integer quantity) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        Result<MatchPay> result = showMemberClient.addMatchPay(matchId, quantity, userInfo.getMarkId(), userInfo.getXopenId());
        ShowAssert.checkResult(result);
        MatchPay matchPay = result.getData();
        PayBack payBack = new PayBack();
        payBack.setType(2);
        payBack.setPayId(matchPay.getMarkId());
        payBack.setUserId(userInfo.getMarkId());
        payBack.setCode(userInfo.getXopenId());
        showMemberClient.matchPayBack(payBack);
        return WechatUtils.matchPay(config, matchPay.getMarkId(), request.getRemoteAddr(), userInfo.getXopenId(), matchPay.getAmount());
    }
}
