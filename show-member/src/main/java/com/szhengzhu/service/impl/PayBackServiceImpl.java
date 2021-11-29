package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.code.MemberCode;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.PayBackService;
import com.szhengzhu.service.base.MemberRechargeBase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jehon
 */
@Service("payBackService")
public class PayBackServiceImpl extends MemberRechargeBase implements PayBackService {

    @Resource
    private PayBackMapper payBackMapper;

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Resource
    private MemberDetailMapper memberDetailMapper;

    @Resource
    private RechargeRuleMapper rechargeRuleMapper;

    @Resource
    private MatchPayMapper matchPayMapper;

    @Resource
    private MatchExchangeMapper matchExchangeMapper;

    @Resource
    private ExchangeDetailMapper exchangeDetailMapper;

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @Resource
    private MatchChanceMapper matchChanceMapper;

    @Resource
    private MemberRechargeBase memberRechargeBase;

    @Transactional
    @Override
    public void modifyPayBack(String payId) {
        PayBack back = payBackMapper.selectByPayId(payId);
        back.setBackType(1);
        back.setBackInfo("SUCCESS");
        payBackMapper.updateByPrimaryKey(back);
        switch (back.getType()) {
            case 1:
                memberPayBack(payId, back.getRuleId());
                break;
            case 2:
                memberMatchPayBack(payId, back.getUserId());
                break;
            default:
                break;
        }
    }

    private void memberPayBack(String detailId, String ruleId) {
        MemberDetail detail = memberDetailMapper.selectByPrimaryKey(detailId);
        MemberAccount account = memberAccountMapper.selectByPrimaryKey(detail.getAccountId());
        Date currentTime = DateUtil.date();
        BigDecimal total = account.getTotalAmount();
        total = total.add(detail.getAmount());
        detail.setCreateTime(currentTime);
        detail.setStatus(1);
        detail.setSurplusAmount(total);
        detail.setType(MemberCode.RECHARGE.code);
        memberDetailMapper.updateByPrimaryKey(detail);
        RechargeRule rule = rechargeRuleMapper.selectByPrimaryKey(ruleId);
        // 赠送设置
        memberRechargeBase.sendDetailByRule(rule, detail, account.getUserId());
        account.setTotalAmount(detail.getSurplusAmount());
        account.setModifyTime(detail.getCreateTime());
        memberAccountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 支付返回
     * 1、添加可领券记录
     * 2、添加可投票记录
     *
     * @param payId
     * @param userId
     */
    private void memberMatchPayBack(String payId, String userId) {
        MatchPay matchPay = matchPayMapper.selectByPrimaryKey(payId);
        matchPay.setModifyTime(DateUtil.date());
        matchPay.setStatus(true);
        matchPayMapper.updateByPrimaryKeySelective(matchPay);
        MatchExchange matchExchange = matchExchangeMapper.selectUserExchangeByMatch(matchPay.getMatchId(), userId);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        if (ObjectUtil.isNull(matchExchange)) {
            matchExchange = MatchExchange.builder().markId(snowflake.nextIdStr()).matchId(matchPay.getMatchId())
                    .userId(userId).createTime(DateUtil.date()).exchanged(0).exchangeTotal(matchPay.getQuantity()).build();
            matchExchangeMapper.insertSelective(matchExchange);
        } else {
            matchExchange.setExchangeTotal(matchExchange.getExchangeTotal() + matchPay.getQuantity());
            matchExchange.setModifyTime(DateUtil.date());
            matchExchangeMapper.updateByPrimaryKeySelective(matchExchange);
        }
        ExchangeDetail detail = ExchangeDetail.builder().markId(snowflake.nextIdStr()).exchangeId(matchExchange.getMarkId())
                .quantity(matchPay.getQuantity()).createTime(DateUtil.date()).build();
        exchangeDetailMapper.insertSelective(detail);
        MatchChance chance = matchChanceMapper.selectByPrimaryKey(userId);
        List<Map<String, Object>> mapList = matchInfoMapper.selectList();
        for (Map<String, Object> StringMap : mapList) {
            if (StringMap.get("giveChance").equals("0")) {
                if (ObjectUtil.isNull(chance)) {
                    chance = MatchChance.builder().userId(userId).totalCount(matchPay.getQuantity()).usedCount(0).createTime(DateUtil.date()).build();
                    matchChanceMapper.insertSelective(chance);
                    return;
                }
                chance.setTotalCount(chance.getTotalCount() + matchPay.getQuantity());
                chance.setModifyTime(DateUtil.date());
                matchChanceMapper.updateByPrimaryKeySelective(chance);
            }
        }
    }

    @Override
    public void matchPayBack(PayBack payBack) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        payBack.setMarkId(snowflake.nextIdStr());
        payBack.setBackType(-1);
        payBack.setAddTime(DateUtil.date());
        payBackMapper.insertSelective(payBack);
    }
}
