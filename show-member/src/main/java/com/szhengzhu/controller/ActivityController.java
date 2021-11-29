package com.szhengzhu.controller;

import com.szhengzhu.bean.member.Activity;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ActivityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Activity activity) {
        activityService.add(activity);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Activity activity) {
        activityService.modify(activity);
    }

    @DeleteMapping(value = "/del/{markId}")
    public void deleteByActivityId(@PathVariable("markId") @NotBlank String markId) {
        activityService.deleteByActivityId(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<Activity> page(@RequestBody PageParam<Activity> pageParam) {
        return activityService.page(pageParam);
    }

    @GetMapping(value = "/info/code")
    public Activity getWellInfoByCode(@RequestParam("code") @NotBlank String code) {
        return activityService.getWellInfoByCode(code);
    }
}
