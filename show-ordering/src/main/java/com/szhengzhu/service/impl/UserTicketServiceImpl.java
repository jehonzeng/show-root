package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.ordering.TicketTemplate;
import com.szhengzhu.bean.ordering.UserTicket;
import com.szhengzhu.bean.ordering.param.GiveParam;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;
import com.szhengzhu.mapper.TicketTemplateMapper;
import com.szhengzhu.mapper.UserTicketMapper;
import com.szhengzhu.service.UserTicketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UserTicketServiceImpl implements UserTicketService {

    @Resource
    private UserTicketMapper userTicketMapper;

    @Resource
    private TicketTemplateMapper ticketTemplateMapper;

    @Override
    public void resGiveTicket(GiveParam giveParam) {
        TicketTemplate template = ticketTemplateMapper
                .selectByPrimaryKey(giveParam.getTemplateId());
        List<UserTicket> list = new LinkedList<>();
        int quantity = giveParam.getQuantity();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int i = 0; i < quantity; i++) {
            UserTicket userTicket = UserTicket.builder().markId(snowflake.nextIdStr()).templateId(template.getMarkId()).
                    userId(giveParam.getUserId()).name(template.getName()).type(template.getType()).
                    description(template.getDescription()).discount(template.getDiscount()).limitPrice(template.getLimitPrice()).
                    limitStore(template.getLimitStore()).overlayUse(template.getOverlayUse()).rankIds(template.getRankIds()).
                    specialDate(template.getSpecialDate()).useTime(null).createTime(DateUtil.date()).issueType(true).status(1).build();
            if ("0".equals(String.valueOf(template.getMode()))) {
                userTicket.setStartTime(template.getStartTime());
                userTicket.setStopTime(template.getStopTime());
            } else if ("1".equals(String.valueOf(template.getMode()))) {
                userTicket.setStartTime(DateUtil.date());
                userTicket.setStopTime(DateUtil.offsetDay(DateUtil.date(), template.getEffectiveDays()));
            }
            list.add(userTicket);
        }
        if (!list.isEmpty()) {
            userTicketMapper.insertBatch(list);
        }
    }

    @Override
    public List<UserTicketVo> listUserTicket(String userId, Integer status) {
        userTicketMapper.updateExpire(userId);
        return userTicketMapper.listUserTicket(userId, status);
    }

    @Override
    public List<UserTicketVo> resUserTicket(String userId) {
        userTicketMapper.updateExpire(userId);
        return userTicketMapper.selectRes(userId);
    }

    @Override
    public List<UserTicketVo> xlistUserTicketByIndent(String userId, String indentId) {
        return userTicketMapper.xlistUserTicketByIndent(userId, indentId);
    }

    @Override
    public List<MemberTicket> memberTicket(String markId) {
        return userTicketMapper.memberTicket(markId);
    }

    @Override
    public void deleteMemberTicket(String markId) {
        userTicketMapper.deleteMemberTicket(markId);
    }
}
