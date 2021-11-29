package com.szhengzhu.controller;

import com.szhengzhu.bean.member.MatchPrize;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MatchPrizeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/match/prize")
public class MatchPrizeController {

    @Resource
    private MatchPrizeService matchPrizeService;

    @PostMapping(value = "/page")
    public PageGrid<MatchPrize> page(@RequestBody PageParam<MatchPrize> param) {
        return matchPrizeService.page(param);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated MatchPrize matchPrize) {
        matchPrizeService.add(matchPrize);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated MatchPrize matchPrize) {
        matchPrizeService.modify(matchPrize);
    }

}
