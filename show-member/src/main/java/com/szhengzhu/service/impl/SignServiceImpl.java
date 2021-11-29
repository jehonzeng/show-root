package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.SignDetail;
import com.szhengzhu.bean.member.SignMember;
import com.szhengzhu.bean.member.SignRule;
import com.szhengzhu.bean.member.param.SignInfoParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.SignDetailMapper;
import com.szhengzhu.mapper.SignMemberMapper;
import com.szhengzhu.mapper.SignRuleMapper;
import com.szhengzhu.service.SignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author makejava
 * @since 2021-06-07 16:15:13
 */
@Service("signService")
public class SignServiceImpl implements SignService {
    @Resource
    private SignMemberMapper signMemberMapper;

    @Resource
    private SignRuleMapper signRuleMapper;

    @Resource
    private SignDetailMapper signDetailMapper;

    @Override
    public SignInfoParam selectBySignId(String markId) {
        return signMemberMapper.queryById(markId);
    }

    @Override
    public List<SignInfoParam> selectBySignMember(SignMember signMember) {
        return signMemberMapper.queryAll(signMember);
    }

    @Override
    public SignInfoParam queryByUserId(SignMember signInfo) {
        SignInfoParam sign = signMemberMapper.queryByUserId(signInfo.getUserId(), signInfo.getYear(), signInfo.getMonth() + 1);
        SignInfoParam lastSign = signMemberMapper.queryByUserId(signInfo.getUserId(), signInfo.getYear(), signInfo.getMonth());
        if (ObjectUtil.isNotEmpty(lastSign) && ObjectUtil.isNotEmpty(sign)) {
            long betweenDay = DateUtil.betweenDay(lastSign.getSignTime(), sign.getSignTime(), true);
            if (betweenDay == 1) {
                sign.setLastMonthNum(lastSign.getContinueSign());
            }
        }
        return sign;
    }

    @Override
    public SignRule queryBySignRuleId(String markId) {
        return signRuleMapper.queryById(markId);
    }

    @Override
    public List<SignRule> querySignRuleList(SignRule signRule) {
        return signRuleMapper.queryAll(signRule);
    }

    @Override
    public void addSignRule(SignRule signRule) {
        Integer result = 0;
        List<SignRule> signRuleList = signRuleMapper.queryAll(SignRule.builder().build());
        for (SignRule rule : signRuleList) {
            if (!signRule.getDays().equals(rule.getDays())) {
                result += 1;
            }
        }
        ShowAssert.checkTrue(result < signRuleList.size(), StatusCode._5045);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        signRule.setMarkId(snowflake.nextIdStr());
        signRule.setCreateTime(new Date());
        signRule.setStatus(true);
        signRuleMapper.add(signRule);
    }

    @Override
    public void modifySignRule(SignRule signRule) {
        signRule.setModifyTime(new Date());
        signRuleMapper.modify(signRule);
    }

    @Override
    public PageGrid<SignDetail> querySignDetailList(PageParam<SignDetail> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<SignDetail> pageInfo = new PageInfo<>(signDetailMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void deleteBySignRuleId(String markId) {
        signRuleMapper.deleteById(markId);
    }
}
