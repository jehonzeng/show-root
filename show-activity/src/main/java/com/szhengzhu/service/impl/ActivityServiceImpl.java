package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.activity.*;
import com.szhengzhu.bean.vo.ActHistoryVo;
import com.szhengzhu.bean.vo.ActivityModel;
import com.szhengzhu.bean.wechat.vo.ActRelation;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author terry
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityInfoMapper activityInfoMapper;

    @Resource
    private ActivityGiftMapper activityGiftMapper;

    @Resource
    private ActivityRuleMapper activityRuleMapper;

    @Resource
    private ActivityHistoryMapper activityHistoryMapper;

    @Resource
    private ParticipantRelationMapper participantRelationMapper;

    @Resource
    private GiftInfoMapper giftInfoMapper;

    @Resource
    private HelpLimitMapper helpLimitMapper;

    @Override
    public PageGrid<ActivityModel> getPage(PageParam<ActivityInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("a.start_time desc,a." + base.getSidx() + " " + base.getSort());
        PageInfo<ActivityModel> page = new PageInfo<>(
                activityInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    @Transactional
    public ActivityInfo addActivity(ActivityInfo base) {
        int count = activityInfoMapper.repeatRecords(base.getTheme(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        activityInfoMapper.insertSelective(base);
        ActivityRule rule = new ActivityRule();
        rule.setMarkId(base.getMarkId());
        rule.setFollow(0);
        rule.setHelperLimit("AR01");
        rule.setLimited("ZL01");
        rule.setInitiatorLimit("AR01");
        activityRuleMapper.insert(rule);
        return base;
    }

    @Override
    public ActivityInfo modifyActivity(ActivityInfo base) {
        int count = activityInfoMapper.repeatRecords(base.getTheme(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        activityInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public ActivityInfo getActivityInfo(String markId) {
        return activityInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<ActivityGift> getActGiftPage(PageParam<ActivityGift> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<ActivityGift> page = new PageInfo<>(
                activityGiftMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public ActivityGift addActGift(ActivityGift base) {
        GiftInfo giftInfo = giftInfoMapper.selectByPrimaryKey(base.getGiftId());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        base.setGiftName(giftInfo.getGiftName());
        activityGiftMapper.insertSelective(base);
        return base;
    }

    @Override
    public ActivityGift updateActGift(ActivityGift base) {
        activityGiftMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public ActivityGift getActGiftById(String markId) {
        return activityGiftMapper.selectByPrimaryKey(markId);
    }

    @Override
    public ActivityRule updateActRule(ActivityRule base) {
        activityRuleMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<ActHistoryVo> getHistoryPage(PageParam<ActivityHistory> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("add_time desc," + base.getSidx() + " " + base.getSort());
        List<ActHistoryVo> list = activityHistoryMapper.selectByExampleSelective(base.getData());
        PageInfo<ActHistoryVo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addActHistory(ActivityHistory history) {
        activityHistoryMapper.insertSelective(history);
        synchronized (activityGiftMapper) {
            // 兑换奖品数量+1
            activityGiftMapper.updateEndTotal(history.getActGiftId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ParticipantRelation helpRelation(ActRelation base) {
        // 活动id
        String markId = base.getActivityId();
        // userId
        String userId = base.getSonId();
        ActivityInfo activityInfo = activityInfoMapper.selectByStop(markId);
        ShowAssert.checkNull(activityInfo, StatusCode._4026);
        ActivityRule rule = activityRuleMapper.selectByPrimaryKey(markId);
        ShowAssert.checkNull(rule, StatusCode._4026);
        // 活动限制（ZL01 每人 ZL02 每天）（这里没限制关注）
        int count = participantRelationMapper.selectRelationCountByType(markId, userId,
                rule.getLimited());
        ShowAssert.checkTrue(count > 0, StatusCode._4032);
        // 助力分数统计
        int point = participantRelationMapper.selectPoint(markId, base.getFatherId());
        // 在一定范围类随机生成助力分
        HelpLimit rel = helpLimitMapper.selectByPriority(markId, point);
        point = RandomUtil.randomInt(rel.getMinPoint(), rel.getMaxPoint());
        // 创建活动参与者关系
        ParticipantRelation data = createRel(base, activityInfo.getTheme(), rule.getFollow(),
                point);
        participantRelationMapper.insertSelective(data);
        return data;
    }

    private ParticipantRelation createRel(ActRelation base, String name, int follow, int point) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return ParticipantRelation.builder()
                .markId(snowflake.nextIdStr()).activityId(base.getActivityId()).fatherId(base.getFatherId()).addTime(DateUtil.date())
                .refreshTime(DateUtil.date()).sonId(base.getSonId()).activityName(name).point(point).serverType(follow)
                .serverStatus(1).build();
    }

    @Override
    public HelpLimit addHelpPointRule(HelpLimit base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        helpLimitMapper.insertSelective(base);
        return base;
    }

    @Override
    public HelpLimit updateHelpPointRule(HelpLimit base) {
        helpLimitMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<HelpLimit> getHelpPointRulePage(PageParam<HelpLimit> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<HelpLimit> list = helpLimitMapper.selectByExampleSelective(base.getData());
        PageInfo<HelpLimit> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }
}
