package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.PartType;
import com.szhengzhu.bean.activity.ActivityInfo;
import com.szhengzhu.bean.activity.ActivityRule;
import com.szhengzhu.bean.wechat.vo.ActivityGiftVo;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.handler.AbstractPart;
import com.szhengzhu.mapper.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Component
@PartType("0")
public class InitiatorHandler extends AbstractPart {

    @Resource
    private ActivityGiftMapper activityGiftMapper;

    @Resource
    private ActivityInfoMapper activityInfoMapper;

    @Resource
    private ActivityRuleMapper activityRuleMapper;

    @Resource
    private ActivityHistoryMapper activityHistoryMapper;

    @Resource
    private ParticipantRelationMapper participantRelationMapper;

    @Override
    public List<ActivityGiftVo> atuoGift(String activityId, String userId, Integer type) {
        List<ActivityGiftVo> gifts = activityGiftMapper.selectGiftList(activityId, type);
        List<ActivityGiftVo> temp = new LinkedList<>();
        for (ActivityGiftVo activityGiftVo : gifts) {
            temp.add(initiatorReceive(userId, activityGiftVo));
        }
//        for (int i = 0, len = gifts.size(); i < len; i++) {
//            result = initiatorReceive(userId, gifts.get(i));
//            //当有活动奖品领取出现问题时跳出执行
//            if (!result.isSuccess()) { break;}
//            temp.add((ActivityGiftVo) result.getData());
//        }
        //确保所有的活动奖品领取都检验成功
        ShowAssert.checkTrue(temp.isEmpty() || temp.size() != gifts.size(), StatusCode._4025);
        return temp;
    }

    @Override
    public ActivityGiftVo manualGift(String markId, String userId, Integer type) {
        ActivityGiftVo gift = activityGiftMapper.selectById(markId, type);
        ShowAssert.checkNull(gift, StatusCode._4025);
        // (0:手动 ,1:自动)
        ShowAssert.checkTrue(gift.getGrantType().intValue() == 1, StatusCode._4029);
        return initiatorReceive(userId, gift);
    }

    private ActivityGiftVo initiatorReceive(String userId, ActivityGiftVo gift) {
        String activityId = gift.getActivityId();// 活动id
        ActivityInfo info = activityInfoMapper.selectByEnd(activityId);
        ShowAssert.checkNull(info, StatusCode._4026);
        ActivityRule rule = activityRuleMapper.selectByPrimaryKey(activityId);
        ShowAssert.checkNull(rule, StatusCode._4026);
        String limit = rule.getInitiatorLimit();// 领奖规则(发起者)
        int count = 0;
        if (!"AR03".equals(limit)) {
            count = activityHistoryMapper.selectHistoryByType(activityId, gift.getMarkId(), userId,
                    limit);
        }
        ShowAssert.checkTrue(!"AR03".equals(limit) && count > 0, StatusCode._4027);
        int temp = participantRelationMapper.selectPoint(activityId, userId);
        ShowAssert.checkTrue(gift.getPoint() > temp, StatusCode._4028);
        return gift;
    }
}
