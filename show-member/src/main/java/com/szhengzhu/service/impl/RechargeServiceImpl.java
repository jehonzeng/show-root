package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.RechargeRule;
import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.RechargeRuleMapper;
import com.szhengzhu.mapper.RechargeTicketMapper;
import com.szhengzhu.service.RechargeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeRuleMapper rechargeRuleMapper;

    @Resource
    private RechargeTicketMapper rechargeTicketMapper;

    @Override
    public PageGrid<RechargeRule> page(PageParam<RechargeRule> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<RechargeRule> list = rechargeRuleMapper.selectByExampleSelective(base.getData());
        for (RechargeRule memberRecharge : list) {
            List<TicketTemplateVo> tickets = rechargeTicketMapper.selectByRuleId(memberRecharge.getMarkId());
            memberRecharge.setTickets(tickets);
        }
        PageInfo<RechargeRule> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public RechargeRule getInfo(String markId) {
        RechargeRule memberRecharge = rechargeRuleMapper.selectByPrimaryKey(markId);
        List<TicketTemplateVo> tickets = rechargeTicketMapper.selectByRuleId(memberRecharge.getMarkId());
        memberRecharge.setTickets(tickets);
        return memberRecharge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RechargeRule base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setCreateTime(DateUtil.date());
        rechargeRuleMapper.insertSelective(base);
        if (base.getTickets() != null && !base.getTickets().isEmpty()) {
            rechargeTicketMapper.insertBatchTicket(base.getMarkId(), base.getTickets());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(RechargeRule base) {
        base.setModifyTime(DateUtil.date());
        rechargeRuleMapper.updateByPrimaryKey(base);
        rechargeTicketMapper.deleteByRuleId(base.getMarkId());
        if (base.getTickets() != null && !base.getTickets().isEmpty()) {
            List<TicketTemplateVo> tickets = base.getTickets();
            rechargeTicketMapper.insertBatchTicket(base.getMarkId(), tickets);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ruleId) {
        rechargeRuleMapper.updateByRuleId(ruleId, -1);
        rechargeTicketMapper.deleteByRuleId(ruleId);
    }

    @Override
    public void modifyStatus(String[] ruleIds, Integer status) {
        rechargeRuleMapper.updateBatchStatus(ruleIds, status);
    }

    @Override
    public List<RechargeRule> list() {
        List<RechargeRule> list = rechargeRuleMapper.selectList();
        for (RechargeRule memberRecharge : list) {
            List<TicketTemplateVo> tickets = rechargeTicketMapper.selectByRuleId(memberRecharge.getMarkId());
            memberRecharge.setTickets(tickets);
        }
        return list;
    }

    @Override
    public List<TicketTemplateVo> getRechargeTickets(String ruleId) {
        return rechargeTicketMapper.selectByRuleId(ruleId);
    }

    @Override
    public List<Combobox> listCombobox() {
        return rechargeRuleMapper.selectCombobox();
    }

}
