package com.szhengzhu.service.base;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.PayBack;
import com.szhengzhu.bean.member.RechargeRule;
import com.szhengzhu.code.IntegralCode;
import com.szhengzhu.code.MemberCode;
import com.szhengzhu.mapper.IntegralDetailMapper;
import com.szhengzhu.mapper.MemberDetailMapper;
import com.szhengzhu.mapper.PayBackMapper;
import com.szhengzhu.rabbitmq.Sender;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jehon
 */
public class MemberRechargeBase {

    @Resource
    private Sender sender;

    @Resource
    private PayBackMapper payBackMapper;

    @Resource
    private MemberDetailMapper memberDetailMapper;

    @Resource
    private IntegralDetailMapper integralDetailMapper;

    public void sendDetailByRule(RechargeRule rule, MemberDetail detail, String userId) {
        // 充值满多少赠送
        if (rule.getRuleType() == 1 && detail.getAmount().compareTo(rule.getLimitAmount()) < 0) {
            return;
        }
        if (rule.getType() != 2 && (ObjectUtil.isNull(rule.getAmount()) || BigDecimal.ZERO.compareTo(rule.getAmount()) >= 0)) {
            return;
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        if (rule.getType() == 0) {
            // 赠送金额记录
            detail.setParentId(detail.getMarkId());
            detail.setMarkId(snowflake.nextIdStr());
            detail.setAmount(rule.getAmount());
            detail.setType(MemberCode.GIVE.code);
            detail.setSurplusAmount(detail.getSurplusAmount().add(rule.getAmount()));
            memberDetailMapper.insertSelective(detail);
        } else if (rule.getType() == 1) {
            giveIntegral(userId, detail.getCreateTime(), rule);
        } else if (rule.getType() == 2) {
            // 赠送优惠券
            Map<String, String> map = new HashMap<>(4);
            map.put("userId", userId);
            map.put("ruleId", rule.getMarkId());
            sender.giveCoupon(map);
        }
    }

    private void giveIntegral(String userId, Date currentTime, RechargeRule memberRecharge) {
        // 赠送积分
//        IntegralAccount integralAccount = integralAccountMapper.selectByUser(userId);
        int integral = memberRecharge.getAmount().intValue();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
//        if (ObjectUtil.isNull(integralAccount)) {
//            String markId = snowflake.nextIdStr();
//            integralAccount = IntegralAccount.builder().markId(markId)
//                    .accountNo(new StringBuffer(markId.substring(0, 12)).reverse().toString())
//                    .createTime(currentTime).userId(userId).totalIntegral(integral).build();
//            integralAccountMapper.insertSelective(integralAccount);
//        } else {
//            integralAccount.setModifyTime(DateUtil.date());
//            integralAccount.setTotalIntegral(integralAccount.getTotalIntegral() + integral);
//            integralAccountMapper.updateByPrimaryKeySelective(integralAccount);
//        }
        IntegralDetail integralDetail = IntegralDetail.builder().markId(snowflake.nextIdStr()).createTime(currentTime)
                .integralLimit(integral).userId(userId).status(1).build();
        integralDetail.setIntegralType(IntegralCode.OTHER_GIVE);
        integralDetailMapper.insertSelective(integralDetail);
        sender.checkAccount(userId, true);
    }

    public void saveRechargeBack(String ruleId, String detailId, Date createTime, String userId, String xopenId) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        PayBack back = PayBack.builder().markId(snowflake.nextIdStr()).type(1)
                .payId(detailId).ruleId(ruleId).backType(-1)
                .addTime(createTime).userId(userId).code(xopenId).build();
        payBackMapper.insertSelective(back);
    }
}
