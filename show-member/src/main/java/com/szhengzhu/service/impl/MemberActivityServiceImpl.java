package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.MemberActivity;
import com.szhengzhu.bean.member.ReceiveTicket;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.MemberAccountMapper;
import com.szhengzhu.mapper.MemberActivityMapper;
import com.szhengzhu.mapper.ReceiveTicketMapper;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.MemberActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("memberActivityService")
public class MemberActivityServiceImpl implements MemberActivityService {

    @Resource
    private MemberActivityMapper memberActivityMapper;

    @Resource
    private ReceiveTicketMapper receiveTicketMapper;

    @Override
    public MemberActivity queryActivityById(String markId) {
        return memberActivityMapper.queryById(markId);
    }

    @Override
    public List<MemberActivity> memberActivity(MemberActivity dishesActivity) {
        return memberActivityMapper.queryAll(dishesActivity);
    }

    @Override
    public PageGrid<MemberActivity> memberActivityByPage(PageParam<MemberActivity> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(param.getSidx() + " " + param.getSort());
        List<MemberActivity> list = memberActivityMapper.queryAll(param.getData());
        for (MemberActivity memberActivity : list) {
            List<ReceiveTicket> tickets = receiveTicketMapper.queryAll(ReceiveTicket.builder().
                    giveId(memberActivity.getMarkId()).build());
            memberActivity.setTickets(tickets);
        }
        PageInfo<MemberActivity> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addActivity(MemberActivity memberActivity) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        memberActivity.setMarkId(snowflake.nextIdStr());
        memberActivity.setCreateTime(DateUtil.date());
        memberActivity.setStatus(1);
        memberActivityMapper.insert(memberActivity);
        if (ObjectUtil.isNotEmpty(memberActivity.getTickets())) {
            receiveTicketMapper.addBatchTicket(memberActivity.getMarkId(), memberActivity.getTickets());
        }
    }

    @Override
    public void modifyActivity(MemberActivity memberActivity) {
        memberActivity.setModifyTime(DateUtil.date());
        memberActivityMapper.update(memberActivity);
        receiveTicketMapper.deleteById(memberActivity.getMarkId());
        if (ObjectUtil.isNotEmpty(memberActivity.getTickets())) {
            receiveTicketMapper.addBatchTicket(memberActivity.getMarkId(), memberActivity.getTickets());
        }
    }

    @Override
    public List<ReceiveTicket> queryById(String receiveId) {
        return receiveTicketMapper.queryById(receiveId);
    }

    @Override
    public void deleteActivity(String markId) {
        memberActivityMapper.deleteActivity(markId);
    }
}
