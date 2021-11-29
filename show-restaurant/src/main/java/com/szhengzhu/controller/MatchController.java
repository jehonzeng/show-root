package com.szhengzhu.controller;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.param.ExchangeParam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author jehon
 */
@Validated
@RestController
@Api(tags = "竞赛活动：MatchController")
@RequestMapping("/v1/match")
public class MatchController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "获取竞赛活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<MatchInfo>> pageMatch(@RequestBody PageParam<MatchInfo> param) {
        return showMemberClient.pageMatch(param);
    }

    @ApiOperation(value = "添加竞赛活动信息")
    @PostMapping(value = "")
    public Result addMatch(@RequestBody @Validated MatchInfo matchInfo) {
        return showMemberClient.addMatch(matchInfo);
    }

    @ApiOperation(value = "修改竞赛活动信息")
    @PatchMapping(value = "")
    public Result modifyMatch(@RequestBody @Validated MatchInfo matchInfo) {
        return showMemberClient.modifyMatch(matchInfo);
    }

    @ApiOperation(value = "扫码获取可领取券的数量")
    @GetMapping(value = "/scancode")
    public Result scanExchangeCode(@RequestParam(value = "mark") String mark) {
        return showMemberClient.scanExchangeCode(mark);
    }

    @ApiOperation(value = "用户兑换券")
    @PostMapping(value = "/exchange")
    public Result exchange(@RequestBody @Validated ExchangeParam exchangeParam, HttpSession session) {
        String employeeId = (String) session.getAttribute(Contacts.RESTAURANT_USER);
        exchangeParam.setEmployeeId(employeeId);
        return showMemberClient.exchange(exchangeParam);
    }

    @ApiOperation(value = "添加竞赛活动与队伍的关联关系")
    @PostMapping(value = "/item/{matchId}")
    public Result addMatchItem(@PathVariable("matchId") String matchId, @RequestBody List<String> teamIds) {
        return showMemberClient.addMatchItem(matchId, teamIds);
    }

    @ApiOperation(value = "获取竞赛阶段奖池分页列表")
    @PostMapping(value = "/prize/page")
    public Result<PageGrid<MatchPrize>> pageMatchPrize(@RequestBody PageParam<MatchPrize> param) {
        return showMemberClient.pageMatchPrize(param);
    }

    @ApiOperation(value = "添加竞赛阶段奖池信息")
    @PostMapping(value = "/prize")
    public Result addMatchPrize(@RequestBody @Validated MatchPrize matchPrize) {
        return showMemberClient.addMatchPrize(matchPrize);
    }

    @ApiOperation(value = "修改竞赛奖池信息")
    @PatchMapping(value = "/prize")
    public Result modifyMatchPrize(@RequestBody @Validated MatchPrize matchPrize) {
        return showMemberClient.modifyMatchPrize(matchPrize);
    }

    @ApiOperation(value = "获取竞赛赛程列表")
    @GetMapping(value = "/stage/list")
    public Result<List<MatchStage>> listByMatch(@RequestParam("matchId") String matchId) {
        return showMemberClient.listByMatch(matchId);
    }

    @ApiOperation(value = "添加竞赛赛程信息")
    @PostMapping(value = "/stage")
    public Result addMatchStage(@RequestBody @Validated MatchStage matchStage) {
        return showMemberClient.addMatchStage(matchStage);
    }

    @ApiOperation(value = "修改竞赛赛程信息")
    @PatchMapping(value = "/stage")
    public Result modifyMatchStage(@RequestBody @Validated MatchStage matchStage) {
        return showMemberClient.modifyMatchStage(matchStage);
    }

    @ApiOperation(value = "赛程关联或取消关联队伍信息")
    @PostMapping(value = "/stage/team")
    public Result addMatchStageTeam(@RequestBody MatchResult matchResult) {
        return showMemberClient.addMatchStageTeam(matchResult);
    }

    @ApiOperation(value = "获取竞赛队伍分页列表")
    @PostMapping(value = "/team/page")
    public Result<PageGrid<MatchTeam>> pageMatchTeam(@RequestBody PageParam<MatchTeam> param) {
        return showMemberClient.pageMatchTeam(param);
    }

    @ApiOperation(value = "添加竞赛队伍信息")
    @PostMapping(value = "/team")
    public Result addMatchTeam(@RequestBody @Validated MatchTeam matchteam) {
        return showMemberClient.addMatchTeam(matchteam);
    }

    @ApiOperation(value = "修改竞赛队伍信息")
    @PatchMapping(value = "/team")
    public Result modifyMatchTeam(@RequestBody @Validated MatchTeam matchteam) {
        return showMemberClient.modifyMatchTeam(matchteam);
    }

    @ApiOperation(value = "获取所有队伍的列表")
    @GetMapping(value = "/team/list")
    Result<List<MatchTeam>> listMatchTeam() {
        return showMemberClient.listMatchTeam();
    }

    @ApiOperation(value = "获取已关联赛事的队伍")
    @GetMapping(value = "/team/match/list")
    public Result<List<MatchTeamVo>> listMatchItem(@RequestParam("matchId") String matchId) {
        return showMemberClient.listMatchTeamByMatch(matchId);
    }

    @ApiOperation(value = "获取当前队伍的投票情况")
    @GetMapping(value = "/vote/info")
    public Result<List<VoteInfo>> selectVoteInfo(@RequestParam(value = "matchId", required = false) String matchId,
                                                 @RequestParam(value = "teamId", required = false) String teamId,
                                                 @RequestParam(value = "stageId", required = false) String stageId) {
        return showMemberClient.selectVoteInfo(matchId, teamId, stageId);
    }
}
