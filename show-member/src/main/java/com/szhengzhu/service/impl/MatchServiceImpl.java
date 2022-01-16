package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.param.ExchangeParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.MatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jehon
 */
@Service("matchService")
public class MatchServiceImpl implements MatchService {

    @Resource
    private Redis redis;

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @Resource
    private MatchItemMapper matchItemMapper;

    @Resource
    private MatchExchangeMapper matchExchangeMapper;

    @Resource
    private ExchangeDetailMapper exchangeDetailMapper;

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Override
    public PageGrid<MatchInfo> page(PageParam<MatchInfo> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<MatchInfo> pageInfo = new PageInfo<>(
                matchInfoMapper.selectByExampleSelective(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(MatchInfo matchInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        matchInfo.setMarkId(snowflake.nextIdStr());
        matchInfo.setCreateTime(DateUtil.date());
        matchInfo.setStatus(true);
        matchInfoMapper.insertSelective(matchInfo);
    }

    @Override
    public void modify(MatchInfo matchInfo) {
        matchInfo.setModifyTime(DateUtil.date());
        matchInfoMapper.updateByPrimaryKeySelective(matchInfo);
    }

    @Override
    public List<Map<String, Object>> list() {
        return matchInfoMapper.selectList();
    }

    @Override
    public void addItem(String matchId, List<String> teamIds) {
        matchItemMapper.deleteByMatch(matchId);
        matchItemMapper.insertBatch(teamIds, matchId);
    }

    @Override
    public Map<String, Object> getUserExchangeMark(String matchId, String userId) {
        Map<String, Object> map = new HashMap<>();
        MatchExchange matchExchange = matchExchangeMapper.selectUserExchangeByMatch(matchId, userId);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        Integer quantity = 0;
        if (ObjectUtil.isNotNull(matchExchange)) {
            quantity = matchExchange.getExchangeTotal() - matchExchange.getExchanged();
        }
        String mark = "";
        if (quantity > 0) {
            mark = snowflake.nextIdStr();
            mark = mark.substring(mark.length() - 14);
            redis.set("member:match:exchange:bar:" + mark, matchId + "," + userId, 60);
        }
        map.put("mark", mark);
        map.put("quantity", quantity);
        return map;
    }

    @Override
    public Map<String, Object> scanCodeByMark(String mark) {
        Map<String, Object> map = new HashMap<>();
        Object markBar = redis.get("member:match:exchange:bar:" + mark);
        ShowAssert.checkTrue(ObjectUtil.isNull(markBar), StatusCode._4060);
        String matchId = markBar.toString().split(",")[0];
        String userId = markBar.toString().split(",")[1];
        MatchExchange matchExchange = matchExchangeMapper.selectUserExchangeByMatch(matchId, userId);
        int quantity = matchExchange.getExchangeTotal() - matchExchange.getExchanged();
        MemberAccount memberAccount = memberAccountMapper.selectByUserId(matchExchange.getUserId());
        map.put("matchId", matchExchange.getMatchId());
        map.put("userId", matchExchange.getUserId());
        map.put("quantity", quantity);
        map.put("phone", memberAccount.getPhone());
        return map;
    }

    @Override
    public void exchange(ExchangeParam exchangeParam) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        MatchExchange matchExchange = matchExchangeMapper.selectUserExchangeByMatch(exchangeParam.getMatchId(), exchangeParam.getUserId());
        ShowAssert.checkTrue(matchExchange.getExchangeTotal() - matchExchange.getExchanged() < exchangeParam.getQuantity(), StatusCode._5041);
        matchExchange.setExchanged(matchExchange.getExchanged() + exchangeParam.getQuantity());
        matchExchange.setModifyTime(DateUtil.date());
        matchExchangeMapper.updateByPrimaryKeySelective(matchExchange);
        exchangeDetailMapper.insertSelective(ExchangeDetail.builder().markId(snowflake.nextIdStr()).createTime(DateUtil.date()).
                exchangeId(matchExchange.getMarkId()).quantity(-exchangeParam.getQuantity()).employeeId(exchangeParam.getEmployeeId()).build());
    }

    @Override
    public List<MatchInfo> selectByGiveChance(Integer type) {
        return matchInfoMapper.selectByGiveChance(type);
    }
}
