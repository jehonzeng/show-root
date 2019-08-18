package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.MealJudgeService;

@RestController
@RequestMapping(value = "/comments")
public class MealJudgeController {

    @Resource
    private MealJudgeService mealJudgeService;

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modifyMealComment(@RequestBody MealJudge base) {
        return mealJudgeService.modifyJudge(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<MealJudgeVo>> mealCommentPage(@RequestBody PageParam<MealJudge> base) {
        return mealJudgeService.getJudgePage(base);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<MealJudge> add(@RequestBody MealJudge base) {
        return mealJudgeService.addJudge(base);
    }

    @RequestMapping(value = "/fore/list", method = RequestMethod.GET)
    public Result<List<JudgeBase>> list(@RequestParam("mealId") String mealId,
            @RequestParam(value = "userId", required = false) String userId) {
        return mealJudgeService.list(mealId, userId);
    }
}
