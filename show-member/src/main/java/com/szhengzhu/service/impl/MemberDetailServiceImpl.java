package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.MemberRecord;
import com.szhengzhu.bean.member.param.MemberPaymentParam;
import com.szhengzhu.bean.member.param.MemberRecordParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.MemberAccountMapper;
import com.szhengzhu.mapper.MemberDetailMapper;
import com.szhengzhu.service.MemberDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jehon
 */
@Service("memberDetailService")
public class MemberDetailServiceImpl implements MemberDetailService {

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Resource
    private MemberDetailMapper memberDetailMapper;

    private static final String SORT_KEY = "create_time ";

    @Override
    public PageGrid<MemberDetail> pageDetail(PageParam<MemberDetail> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(SORT_KEY + param.getSort());
        PageInfo<MemberDetail> pageInfo = new PageInfo<>(
                memberDetailMapper.selectByAccount(param.getData().getAccountId()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public PageGrid<MemberDetail> pageUserDetail(PageParam<String> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(SORT_KEY + param.getSort());
        PageInfo<MemberDetail> pageInfo = new PageInfo<>(memberDetailMapper.selectByUserId(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDetail(String detailId, String userId) {
        // 暂时不可删除会员自助充值
        MemberDetail detail = memberDetailMapper.selectByPrimaryKey(detailId);
        ShowAssert.checkTrue(StrUtil.isBlank(detail.getCreator()), StatusCode._5032);
        // 超过两小时不能撤回
        ShowAssert.checkTrue(System.currentTimeMillis() - detail.getCreateTime().getTime() > 2L * 60 * 60 * 1000L, StatusCode._5019);
        ShowAssert.checkTrue(detail.getStatus() == -1, StatusCode._5023);
        MemberAccount account = memberAccountMapper.selectByPrimaryKey(detail.getAccountId());
        memberDetailMapper.deleteByPrimaryKey(detailId);
        memberAccountMapper.updateByPrimaryKeySelective(MemberAccount.builder().modifyTime(DateUtil.date()).markId(account.getMarkId())
                .totalAmount(account.getTotalAmount().subtract(detail.getAmount())).build());
        // 保存撤回记录
        detail.setStatus(-1);
        detail.setCreateTime(DateUtil.date());
        detail.setCreator(userId);
        detail.setSurplusAmount(account.getTotalAmount());
        memberDetailMapper.insertSelective(detail);
    }

    @Override
    public PageGrid<MemberRecordParam> memberRecord(PageParam<MemberRecord> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(SORT_KEY + param.getSort());
        PageInfo<MemberRecordParam> pageInfo = new PageInfo<>(memberDetailMapper.memberRecord(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Map<String, Object> memberDetailTotal(MemberRecord memberRecord) {
        return memberDetailMapper.memberDetailTotal(memberRecord);
    }

    @Override
    public PageGrid<MemberPaymentParam> memberPayment(PageParam<MemberRecord> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(SORT_KEY + param.getSort());
        PageInfo<MemberPaymentParam> pageInfo = new PageInfo<>(memberDetailMapper.memberPayment(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void delete(String detailId) {
        memberDetailMapper.deleteByPrimaryKey(detailId);
    }

    @Override
    public MemberDetail selectByMarkId(String markId) {
        return memberDetailMapper.selectByPrimaryKey(markId);
    }
}
