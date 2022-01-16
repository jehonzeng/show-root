package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MatchInfo;
import com.szhengzhu.bean.member.param.ExchangeParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MatchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/match")
public class MatchController {

    @Resource
    private MatchService matchService;

    @PostMapping(value = "/page")
    public PageGrid<MatchInfo> page(@RequestBody PageParam<MatchInfo> param) {
        return matchService.page(param);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated MatchInfo matchInfo) {
        matchService.add(matchInfo);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated MatchInfo matchInfo) {
        matchService.modify(matchInfo);
    }

    @GetMapping(value = "/list")
    public List<Map<String, Object>> list() {
        return matchService.list();
    }

    @PostMapping(value = "/item/{matchId}")
    public void addMatchItem(@PathVariable("matchId") String matchId, @RequestBody List<String> teamIds) {
        matchService.addItem(matchId, teamIds);
    }

    @GetMapping(value = "/create/mark")
    public Map<String, Object> createMark(@RequestParam("matchId") String matchId, @RequestParam("userId") @NotBlank String userId) {
        return matchService.getUserExchangeMark(matchId, userId);
    }

    @GetMapping(value = "/scancode")
    public Map<String, Object> scanCode(@RequestParam(value = "mark") String mark) {
        return matchService.scanCodeByMark(mark);
    }

    @PostMapping(value = "/exchange")
    public void exchange(@RequestBody @Validated ExchangeParam exchangeParam) {
        matchService.exchange(exchangeParam);
    }

    @GetMapping(value = "/type")
    public List<MatchInfo> selectByGiveChance(@RequestParam(value = "type", required = false) Integer type) {
        return matchService.selectByGiveChance(type);
    }
}
