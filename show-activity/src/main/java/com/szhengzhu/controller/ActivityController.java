package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.activity.*;
import com.szhengzhu.bean.vo.ActHistoryVo;
import com.szhengzhu.bean.vo.ActivityModel;
import com.szhengzhu.bean.wechat.vo.ActRelation;
import com.szhengzhu.bean.wechat.vo.ActivityGiftVo;
import com.szhengzhu.context.ActivityContext;
import com.szhengzhu.context.PartContext;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractActivity;
import com.szhengzhu.handler.AbstractPart;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.ActivityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author terry shi
 */
@Validated
@RestController
@RequestMapping(value = "acts")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityContext activityContext;

    @Resource
    private PartContext partContext;

    @Resource
    private Sender sender;

    @PostMapping(value = "/page")
    public PageGrid<ActivityModel> getActivityPage(
            @RequestBody PageParam<ActivityInfo> base) {
        return activityService.getPage(base);
    }

    @PostMapping(value = "/add")
    public ActivityInfo addAct(@RequestBody @Validated ActivityInfo base) {
        return activityService.addActivity(base);
    }

    @PatchMapping(value = "/modify")
    public ActivityInfo updateAct(@RequestBody @Validated ActivityInfo base) {
        return activityService.modifyActivity(base);
    }

    @GetMapping(value = "/{markId}")
    public ActivityInfo getActivityInfo(@PathVariable("markId") @NotBlank String markId) {
        return activityService.getActivityInfo(markId);
    }

    @PostMapping(value = "/gift/page")
    public PageGrid<ActivityGift> getActGiftPage(
            @RequestBody PageParam<ActivityGift> base) {
        return activityService.getActGiftPage(base);
    }

    @PostMapping(value = "/gift/add")
    public ActivityGift addActGift(@RequestBody @Validated ActivityGift base) {
        return activityService.addActGift(base);
    }

    @PatchMapping(value = "/gift/modify")
    public ActivityGift updateActGift(@RequestBody @Validated ActivityGift base) {
        return activityService.updateActGift(base);
    }

    @GetMapping(value = "/gift/{markId}")
    public ActivityGift getActGiftById(@PathVariable("markId") @NotBlank String markId) {
        return activityService.getActGiftById(markId);
    }

    @PatchMapping(value = "/rule/modify")
    public ActivityRule updateActRule(@RequestBody ActivityRule base) {
        return activityService.updateActRule(base);
    }

    @PostMapping(value = "/history/page")
    public PageGrid<ActHistoryVo> getHistoryPage(
            @RequestBody PageParam<ActivityHistory> base) {
        return activityService.getHistoryPage(base);
    }

    @PostMapping(value = "/help/rule/add")
    public void addHelpPointRule(@RequestBody @Validated HelpLimit base) {
        activityService.addHelpPointRule(base);
    }

    @PatchMapping(value = "/help/rule/modify")
    public HelpLimit updateHelpPointRule(@RequestBody HelpLimit base) {
        return activityService.updateHelpPointRule(base);
    }

    @PostMapping(value = "/help/rulePage")
    public PageGrid<HelpLimit> getHelpPointRulePage(PageParam<HelpLimit> base) {
        return activityService.getHelpPointRulePage(base);
    }

    @GetMapping(value = "/fore/info")
    public Result<Object> getActInfo(@RequestParam("markId") @NotBlank String markId,
                                     @RequestParam("type") @NotNull Integer type,
                                     @RequestParam(value = "userId", required = false) String userId) {
        AbstractActivity abstractActivity = activityContext.getInstance(type.toString());
        return abstractActivity.getActBaseInfo(markId, userId);
    }

    @GetMapping(value = "/manual/gift")
    public void manualGift(@RequestParam("markId") @NotBlank String markId,
                           @RequestParam("userId") @NotBlank String userId, @RequestParam("type") @NotNull Integer type) {
        AbstractPart abstractPart = partContext.getInstance(type.toString());
        ActivityGiftVo activityGiftVo = abstractPart.manualGift(markId, userId, type);
        gift(activityGiftVo, userId);
    }

    @PostMapping(value = "/help/rel")
    public void helpRelation(@RequestBody ActRelation base) {
        ParticipantRelation participantRelation = activityService.helpRelation(base);
        sender.receiveAuto(participantRelation);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "/auto/gift")
    public void autoReceiveGift(@RequestParam("activityId") String activityId,
                                @RequestParam("userId") String userId, @RequestParam("type") Integer type) {
        AbstractPart abstractPart = partContext.getInstance(type.toString());
        List<ActivityGiftVo> list = abstractPart.atuoGift(activityId, userId, type);
        for (ActivityGiftVo activityGiftVo : list) {
            gift(activityGiftVo, userId);
        }
    }

    private void gift(ActivityGiftVo base, String userId) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        ActivityHistory history = ActivityHistory.builder().markId(snowflake.nextIdStr()).activityId(base.getActivityId()).actGiftId(base.getMarkId())
                .addTime(DateUtil.date()).serverStatus(0).userId(userId).type(base.getPartType()).activityName(base.getActName()).build();
        activityService.addActHistory(history);
        // 可以采用策略模式优化
        switch (base.getGiftType().intValue()) {
            case 0:
                // 商品发放 未处理
                break;
            case 1:
            case 2:
                sender.sendCoupon(base.getProductId(), userId);
                break;
            case 3:
                sender.sendVoucher(base.getProductId(), userId);
            default:
                break;
        }
    }
}
