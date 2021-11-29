package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.param.MemberDetailParam;
import com.szhengzhu.bean.member.param.RechargeParam;
import com.szhengzhu.bean.member.vo.MemberAccountVo;
import com.szhengzhu.bean.member.vo.MemberTicketVo;
import com.szhengzhu.code.MemberCode;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.MemberService;
import com.szhengzhu.service.base.MemberRechargeBase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Resource
    private Redis redis;

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Resource
    private MemberDetailMapper memberDetailMapper;

    @Resource
    private IntegralAccountMapper integralAccountMapper;

    @Resource
    private RechargeRuleMapper rechargeRuleMapper;

    @Resource
    private IntegralDetailMapper integralDetailMapper;

    @Resource
    private MemberGradeMapper memberGradeMapper;

    @Resource
    private GradeRecordMapper gradeRecordMapper;

    @Resource
    private MemberRechargeBase memberRechargeBase;

    @Override
    public PageGrid<MemberAccount> pageAccount(PageParam<MemberByType> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(param.getSidx() + " " + param.getSort());
        List<MemberAccount> list = memberAccountMapper.selectByExampleSelective(param.getData());
        list.stream()
                .forEach(account -> account.setConsumeAmount(gradeRecordMapper.consumeTotal(account.getMarkId()).intValue()));
        list.stream()
                .filter(account -> StrUtil.isNotEmpty(account.getGradeId()))
                .forEach(account -> account.setMemberGrade(memberGradeMapper.queryById(account.getGradeId())));
        PageInfo<MemberAccount> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public String addAccount(MemberAccount account) {
        MemberAccount memberAccount = memberAccountMapper.selectByUserId(account.getUserId());
        String accountNo;
        if (ObjectUtil.isNull(memberAccount)) {
            String id = memberAccountMapper.selectIdByPhone(account.getPhone());
            ShowAssert.checkTrue(!StrUtil.isEmpty(id), StatusCode._4056);
            account.setMarkId(account.getMarkId());
            accountNo = new StringBuffer(account.getMarkId().substring(0, 12)).reverse().toString();
            account.setAccountNo(accountNo);
            account.setTotalAmount(BigDecimal.ZERO);
            account.setCreateTime(DateUtil.date());
            memberAccountMapper.insertSelective(account);
        } else {
            accountNo = memberAccount.getAccountNo();
            memberAccount.setBirthday(account.getBirthday());
            memberAccount.setPhone(account.getPhone());
            memberAccount.setGender(account.getGender());
            memberAccountMapper.updateByPrimaryKeySelective(memberAccount);
        }
        return accountNo;
    }

    @Override
    public MemberAccount modifyAccount(MemberAccount base) {
        // 查看已有的账户记录
        MemberAccount account = memberAccountMapper.selectByUserId(base.getUserId());
        Optional<MemberAccount> op = Optional.ofNullable(account);
        account = op.orElse(memberAccountMapper.selectByPrimaryKey(base.getMarkId()));
        // 一年仅修改一次
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        Date modifyTime = account.getModifyTime();
        Date birthday = account.getBirthday();
        if (ObjectUtil.isNotNull(modifyTime) && ObjectUtil.isNotNull(birthday) && ObjectUtil.isNotNull(base.getBirthday())
                && !account.getBirthday().equals(base.getBirthday())) {
            calendar.setTime(modifyTime);
            calendar.add(Calendar.YEAR, 1);
            Date date = calendar.getTime();
            ShowAssert.checkTrue(currentTime.before(date), StatusCode._5022);
        }
        account.setName(base.getName());
        account.setGender(base.getGender());
        account.setBirthday(base.getBirthday());
        account.setPhone(base.getPhone());
        account.setModifyTime(currentTime);
        memberAccountMapper.updateByPrimaryKeySelective(account);
        return base;
    }

    @Override
    public MemberAccount getInfo(String markId) {
        return memberAccountMapper.selectByPrimaryKey(markId);
    }

    @Override
    public MemberAccountVo getVoInfoById(String markId) {
        MemberAccountVo account = memberAccountMapper.selectVoById(markId);
        int total = integralDetailMapper.selectTotalByUser(account.getUserId());
        account.setIntegralTotal(total);
        return account;
    }

    @Override
    public List<MemberAccountVo> getInfoByNoOrPhone(String phone, String accountNo) {
        List<MemberAccountVo> list = memberAccountMapper.selectByNoOrPhone(phone, accountNo);
        list.stream()
                .forEach(account -> account.setConsumeAmount(gradeRecordMapper.consumeTotal(account.getMarkId()).intValue()));
        list.stream()
                .filter(account -> StrUtil.isNotEmpty(account.getGradeId()))
                .forEach(account -> account.setMemberGrade(memberGradeMapper.queryById(account.getGradeId())));
        return list;
    }

    @Override
    public MemberAccount getInfoByUserId(String userId) {
        MemberAccount account = memberAccountMapper.selectByUserId(userId);
        ShowAssert.checkNull(account, StatusCode._4049);
        if (StrUtil.isNotEmpty(account.getGradeId())) {
            MemberGrade memberGrade = memberGradeMapper.queryById(account.getGradeId());
            List<MemberGrade> gradeList = memberGradeMapper.queryAll(new MemberGrade());
            memberGrade.setSort(gradeList.indexOf(memberGrade));
            account.setMemberGrade(memberGrade);
        }
        BigDecimal consumeTotal = gradeRecordMapper.consumeTotal(account.getMarkId());
        if (ObjectUtil.isNotEmpty(consumeTotal)) {
            account.setConsumeAmount(consumeTotal.intValue());
        }
        return account;
    }

    @Transactional
    @Override
    public MemberAccount recharge(MemberDetailParam detailParam) {
        MemberAccount account = memberAccountMapper.selectByPrimaryKey(detailParam.getAccountId());
        ShowAssert.checkNull(account, StatusCode._4034);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        Date currentTime = DateUtil.date();
        BigDecimal total = account.getTotalAmount();
        total = total.add(detailParam.getAmount());
        MemberDetail detail = MemberDetail.builder().markId(snowflake.nextIdStr()).accountId(account.getMarkId()).amount(detailParam.getAmount())
                .creator(detailParam.getEmployeeId()).createTime(currentTime).storeId(detailParam.getStoreId()).type(MemberCode.RECHARGE.code)
                // 消费后的余额
                .surplusAmount(total).status(1).build();
        memberDetailMapper.insertSelective(detail);
        // 赠送金额记录
        if (ObjectUtil.isNotNull(detailParam.getBonusAmount()) && detailParam.getBonusAmount().compareTo(BigDecimal.ZERO) != 0) {
            detail.setParentId(detail.getMarkId());
            detail.setMarkId(snowflake.nextIdStr());
            detail.setAmount(detailParam.getBonusAmount());
            detail.setCreateTime(currentTime);
            detail.setType(MemberCode.GIVE.code);
            detail.setStatus(1);
            total = total.add(detailParam.getBonusAmount());
            // 赠送后余额
            detail.setSurplusAmount(total);
            memberDetailMapper.insertSelective(detail);
        }
        account.setModifyTime(currentTime);
        account.setTotalAmount(total);
        memberAccountMapper.updateByPrimaryKeySelective(account);
        return account;
    }

    @Transactional
    @Override
    public void rechargeByRule(RechargeParam param) {
        RechargeRule rule = rechargeRuleMapper.selectByPrimaryKey(param.getRuleId());
        MemberAccount account = memberAccountMapper.selectByPrimaryKey(param.getAccountId());
        BigDecimal total = account.getTotalAmount();
        BigDecimal amount = rule.getLimitAmount();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        total = total.add(amount);
        MemberDetail detail = MemberDetail.builder().markId(snowflake.nextIdStr()).accountId(account.getMarkId()).amount(amount).createTime(DateUtil.date())
                .creator(param.getEmployeeId()).storeId(param.getStoreId()).type(MemberCode.RECHARGE.code).status(1)
                // 消费后的余额
                .surplusAmount(total).build();
        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            memberDetailMapper.insertSelective(detail);
        }
        // 赠送设置
        memberRechargeBase.sendDetailByRule(rule, detail, account.getUserId());
        account.setTotalAmount(detail.getSurplusAmount());
        account.setModifyTime(detail.getCreateTime());
        memberAccountMapper.updateByPrimaryKeySelective(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MemberDetail rechargeByRule(String ruleId, String userId, String xopenId, BigDecimal indentTotal) {
        RechargeRule rule = rechargeRuleMapper.selectByPrimaryKey(ruleId);
        MemberAccount account = memberAccountMapper.selectByUserId(userId);
        BigDecimal amount = BigDecimal.ZERO;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        MemberDetail detail = MemberDetail.builder().markId(snowflake.nextIdStr()).accountId(account.getMarkId())
                .type(MemberCode.RECHARGE.code).createTime(DateUtil.date()).status(0).build();
        if (rule.getRuleType() == 0) {
            amount = rule.getLimitAmount();
        } else if (rule.getRuleType() == 1) {
            amount = indentTotal.multiply(new BigDecimal(rule.getTimes()));
        }
        detail.setAmount(amount);
        memberDetailMapper.insertSelective(detail);
        memberRechargeBase.saveRechargeBack(ruleId, detail.getMarkId(), detail.getCreateTime(), userId, xopenId);
        return detail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String consume(MemberDetail detail) {
        MemberAccount account = memberAccountMapper.selectByPrimaryKey(detail.getAccountId());
        ShowAssert.checkNull(account, StatusCode._4049);
        ShowAssert.checkTrue(detail.getAmount().compareTo(account.getTotalAmount()) > 0, StatusCode._4035);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        Date currentTime = DateUtil.date();
        account.setTotalAmount(account.getTotalAmount().subtract(detail.getAmount()));
        account.setModifyTime(currentTime);
        memberAccountMapper.updateByPrimaryKeySelective(account);
        detail.setMarkId(snowflake.nextIdStr());
        detail.setAccountId(account.getMarkId());
        detail.setAmount(detail.getAmount().negate());
        detail.setCreateTime(currentTime);
        detail.setStatus(1);
        // 消费后的余额
        detail.setSurplusAmount(account.getTotalAmount());
        memberDetailMapper.insertSelective(detail);
        return detail.getMarkId();
    }

    @Override
    public String createBarMark(String userId) {
        MemberAccount account = memberAccountMapper.selectByUserId(userId);
        ShowAssert.checkNull(account, StatusCode._4049);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String mark = snowflake.nextIdStr();
        mark = mark.substring(mark.length() - 14);
        redis.set("member:account:bar:" + mark, account.getMarkId(), 60);
        return mark;
    }

    @Override
    public String scanCodeByMark(String mark, String phone) {
        Object accountId = redis.get("member:account:bar:" + mark);
        if (ObjectUtil.isNull(accountId)) {
            accountId = memberAccountMapper.selectIdByPhone(phone);
        }
        return (String) accountId;
    }

    @Override
    public MemberTicketVo resInfoById(String memberId) {
        MemberTicketVo memberVo = memberAccountMapper.selectTicketVoById(memberId);
        IntegralAccount integralAccount = integralAccountMapper.selectByUser(memberVo.getUserId());
        memberVo.setIntegralTotal(integralAccount == null ? 0 : integralAccount.getTotalIntegral());
        return memberVo;
    }

    @Override
    public BigDecimal getTotalByUserId(String userId) {
        return memberAccountMapper.selectTotalByUserId(userId);
    }

    @Override
    public Map<String, Object> getMemberInfo() {
        return memberDetailMapper.selectMemberInfo();
    }

    @Override
    public BigDecimal selectMemberDiscount(String markId) {
        return memberAccountMapper.selectMemberDiscount(markId);
    }
}
