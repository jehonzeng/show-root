package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MatchTeam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MatchTeamService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/match/team")
public class MatchTeamController {

    @Resource
    private MatchTeamService matchTeamService;

    @PostMapping(value = "/page")
    public PageGrid<MatchTeam> page(@RequestBody PageParam<MatchTeam> param) {
        return matchTeamService.page(param);
    }

    @GetMapping(value = "/{markId}")
    public MatchTeam getInfo(@PathVariable("markId") String markId) {
        return matchTeamService.getInfo(markId);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated MatchTeam matchteam) {
        matchTeamService.add(matchteam);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated MatchTeam matchteam) {
        matchTeamService.modify(matchteam);
    }

    @GetMapping(value = "/list")
    public List<MatchTeam> list() {
        return matchTeamService.list();
    }

    @GetMapping(value = "/match/list/vo")
    public List<MatchTeamVo> listByMatch(@RequestParam("matchId") String matchId) {
        return matchTeamService.listMatchTeamByMatch(matchId);
    }

    @GetMapping(value = "/match/list")
    public List<MatchTeamVo> listMatchVoteTeamList(@RequestParam("userId") String userId, @RequestParam("matchId") String matchId) {
        return matchTeamService.listMatchVoteTeamList(userId, matchId);
    }

}
