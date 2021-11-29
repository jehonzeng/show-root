package com.szhengzhu.controller;

import com.szhengzhu.bean.member.VoteInfo;
import com.szhengzhu.bean.member.param.MatchVoteParam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import com.szhengzhu.service.MatchVoteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/match/vote")
public class MatchVoteController {

    @Resource
    private MatchVoteService matchVoteService;

    @GetMapping(value = "/team/list")
    public List<MatchTeamVo> listVoteTeam(@RequestParam("userId") String userId, @RequestParam("matchId") String matchId) {
        return matchVoteService.listVoteTeam(userId, matchId);
    }

    @GetMapping(value = "/count")
    public Integer getVoteCount(@RequestParam("userId") String userId) {
        return matchVoteService.getVoteChancesByUser(userId);
    }

    @PostMapping(value = "/team")
    public void voteTeams(@RequestBody MatchVoteParam matchVoteParam) {
        matchVoteService.voteTeams(matchVoteParam);
    }

    @GetMapping(value = "/team/user")
    public List<String> listUserByMatch(@RequestParam("matchId") String matchId, @RequestParam("teamId") String teamId) {
        return matchVoteService.listUserByMatch(matchId, teamId);
    }

    @GetMapping(value = "/info")
    public List<VoteInfo> selectVoteInfo(@RequestParam(value = "matchId", required = false) String matchId,
                                         @RequestParam(value = "teamId", required = false) String teamId,
                                         @RequestParam(value = "stageId", required = false) String stageId) {
        return matchVoteService.selectVoteInfo(matchId, teamId, stageId);
    }
}
