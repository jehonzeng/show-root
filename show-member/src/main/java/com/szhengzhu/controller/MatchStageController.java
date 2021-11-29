package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MatchResult;
import com.szhengzhu.bean.member.MatchStage;
import com.szhengzhu.service.MatchStageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/match/stage")
public class MatchStageController {

    @Resource
    private MatchStageService matchStageService;

    @GetMapping(value = "/list")
    public List<MatchStage> listByMatch(@RequestParam("matchId") String matchId) {
        return matchStageService.listByMatch(matchId);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated MatchStage matchStage) {
        matchStageService.add(matchStage);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated MatchStage matchStage) {
        matchStageService.modify(matchStage);
    }

    @PostMapping(value = "/team/add")
    public void addStageTeam(@RequestBody MatchResult matchResult) {
        matchStageService.addStageTeam(matchResult);
    }

    @GetMapping(value = "/prize/open")
    public void openPrize(@RequestParam("stageId") String stageId) {

    }
}
