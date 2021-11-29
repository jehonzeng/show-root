package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.szhengzhu.bean.member.MatchInfo;
import com.szhengzhu.bean.member.MatchPay;
import com.szhengzhu.bean.member.vo.StageResultVo;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.MatchInfoMapper;
import com.szhengzhu.mapper.MatchPayMapper;
import com.szhengzhu.mapper.MatchResultMapper;
import com.szhengzhu.service.MatchPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author jehon
 */
@Service("matchPayService")
public class MatchPayServiceImpl implements MatchPayService {

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @Resource
    private MatchPayMapper matchPayMapper;

    @Resource
    private MatchResultMapper matchResultMapper;

    @Override
    public MatchPay matchPay(String matchId, Integer quantity, String userId) {
        MatchInfo matchInfo = matchInfoMapper.selectByPrimaryKey(matchId);
        ShowAssert.checkTrue(matchInfo.getAmount().compareTo(BigDecimal.ZERO) <= 0, StatusCode._4062);
        StageResultVo resultVo = matchResultMapper.selectLastByMatch(matchId);
        ShowAssert.checkTrue(resultVo.getLastTime() != null && DateUtil.date().isAfter(resultVo.getLastTime()), StatusCode._4061);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        MatchPay matchPay = MatchPay.builder().markId(snowflake.nextIdStr()).matchId(matchId).userId(userId).amount(NumberUtil.mul(matchInfo.getAmount(), quantity))
                .quantity(quantity).createTime(DateUtil.date()).status(false).build();
        matchPayMapper.insertSelective(matchPay);
        return matchPay;
    }
}
