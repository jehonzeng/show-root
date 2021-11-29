package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.GradeRecordMapper;
import com.szhengzhu.mapper.GradeTicketMapper;
import com.szhengzhu.mapper.MemberAccountMapper;
import com.szhengzhu.mapper.MemberGradeMapper;
import com.szhengzhu.service.MemberGradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author makejava
 * @since 2021-04-21 10:57:10
 */
@Service("memberGradeService")
public class MemberGradeServiceImpl implements MemberGradeService {

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Resource
    private MemberGradeMapper memberGradeMapper;

    @Resource
    private GradeTicketMapper gradeTicketMapper;

    @Resource
    private GradeRecordMapper gradeRecordMapper;

    @Override
    public MemberGrade queryById(String markId) {
        return memberGradeMapper.queryById(markId);
    }

    @Override
    public List<MemberGrade> queryAll(MemberGrade memberGrade) {
        return memberGradeMapper.queryAll(memberGrade);
    }

    @Override
    public void add(MemberGrade memberGrade) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        memberGrade.setMarkId(snowflake.nextIdStr());
        memberGrade.setCreateTime(DateUtil.date());
        memberGrade.setStatus(1);
        memberGradeMapper.add(memberGrade);
        if (ObjectUtil.isNotEmpty(memberGrade.getTickets())) {
            gradeTicketMapper.addBatchGradeTicket(memberGrade.getMarkId(), memberGrade.getTickets());
        }
    }

    @Override
    public void modify(MemberGrade memberGrade) {
        memberGrade.setModifyTime(DateUtil.date());
        memberGradeMapper.modify(memberGrade);
        gradeTicketMapper.deleteById(memberGrade.getMarkId());
        if (ObjectUtil.isNotEmpty(memberGrade.getTickets())) {
            gradeTicketMapper.addBatchGradeTicket(memberGrade.getMarkId(), memberGrade.getTickets());
        }
    }

    @Override
    public void deleteById(String markId) {
        memberGradeMapper.deleteById(markId);
    }

    @Override
    public List<MemberGradeShow> memberGradeShow(String userId) {
        MemberAccount account = memberAccountMapper.selectByUserId(userId);
        ShowAssert.checkNull(account, StatusCode._4049);
        List<MemberGradeShow> show = null;
        if (StrUtil.isNotEmpty(account.getGradeId())) {
            MemberGrade memberGrade = memberGradeMapper.queryById(account.getGradeId());
            account.setMemberGrade(memberGrade);
            show = memberGradeMapper.memberGradeShow(account.getConsumeAmount());
            if (ObjectUtil.isEmpty(show)) {
                show = memberGradeMapper.memberGradeShow(null);
            }
            account.setMemberGradeShows(show);
        }
        return show;
    }

    @Override
    public List<GradeTicket> queryGradeTicket(String gradeId) {
        return gradeTicketMapper.queryAll(GradeTicket.builder().gradeId(gradeId).build());
    }

    @Override
    public MemberGrade selectByGradeId(Integer amount) {
        return memberGradeMapper.selectByGradeId(amount);
    }

    @Override
    public PageGrid<GradeRecord> queryGradeRecord(PageParam<GradeRecord> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(param.getSidx() + " " + param.getSort());
        PageInfo<GradeRecord> pageInfo = new PageInfo<>(gradeRecordMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }
}
