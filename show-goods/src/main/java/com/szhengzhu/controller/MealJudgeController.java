package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.MealJudgeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/comments")
public class MealJudgeController {

    @Resource
    private MealJudgeService mealJudgeService;

    @PatchMapping(value = "/modify")
    public MealJudge modifyMealComment(@RequestBody @Validated MealJudge base) {
        return mealJudgeService.modifyJudge(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<MealJudgeVo> mealCommentPage(@RequestBody PageParam<MealJudge> base) {
        return mealJudgeService.getJudgePage(base);
    }

    @PostMapping(value = "/add")
    public MealJudge add(@RequestBody @Validated MealJudge base) {
        return mealJudgeService.addJudge(base);
    }

    @GetMapping(value = "/fore/list")
    public List<JudgeBase> list(@RequestParam("mealId") @NotBlank String mealId,
                                        @RequestParam(value = "userId", required = false) String userId) {
        return mealJudgeService.list(mealId, userId);
    }
}
