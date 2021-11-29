package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.IntegralService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("integralService")
public class IntegralServiceImpl implements IntegralService {

    @Resource
    private Sender sender;

    @Resource
    private IntegralDetailMapper integralDetailMapper;

    @Resource
    private IntegralExchangeMapper integralExchangeMapper;

    @Resource
    private TicketExchangeMapper ticketExchangeMapper;

    @Resource
    private IntegralExpireMapper integralExpireMapper;

    @Override
    public PageGrid<IntegralDetail> pageIntegral(PageParam<IntegralDetail> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<IntegralDetail> pageInfo = new PageInfo<>(
                integralDetailMapper.selectByExampleSelective(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public PageGrid<IntegralDetail> pageUserIntegral(PageParam<String> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<IntegralDetail> pageInfo = new PageInfo<>(
                integralDetailMapper.selectByUserId(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addTicketExchange(IntegralDetail integral) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        integral.setMarkId(snowflake.nextIdStr());
        integral.setCreateTime(DateUtil.date());
        integral.setStatus(1);
        integralDetailMapper.insertSelective(integral);
        sender.checkAccount(integral.getUserId(), true);
    }

    @Override
    public void consume(IntegralDetail detail) {
        int total = integralDetailMapper.selectTotalByUser(detail.getUserId());
        ShowAssert.checkTrue(detail.getIntegralLimit().compareTo(total) > 0, StatusCode._4057);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        detail.setMarkId(snowflake.nextIdStr());
        detail.setCreateTime(DateUtil.date());
        detail.setStatus(1);
        detail.setIntegralLimit(-detail.getIntegralLimit());
        integralDetailMapper.insertSelective(detail);
        sender.checkAccount(detail.getUserId(), true);
    }

    @Override
    public Integer getTotalByUserId(String userId) {
        // sender.checkAccount(userId, false);
        return integralDetailMapper.selectTotalByUser(userId);
    }

    @Override
    public Map<String, Integer> getSumByUser(String userId) {
        Map<String, Integer> result = new HashMap<>(4);
        int total = integralDetailMapper.selectTotalByUser(userId);
        int todayTotal = integralDetailMapper.selectTodayTotal(userId);
        int consumeTotal = integralDetailMapper.selectConsumeTotal(userId);
        result.put("total", total);
        result.put("todayTotal", todayTotal);
        result.put("consumeTotal", consumeTotal);
        return result;
    }

    @Override
    public PageGrid<IntegralDetail> integralRecord(PageParam<MemberRecord> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        param.setSort("desc");
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<IntegralDetail> pageInfo = new PageInfo<>(integralDetailMapper.memberIntegral(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public IntegralExchange queryByIntegralExchangeId(String markId) {
        return integralExchangeMapper.queryById(markId);
    }

    @Override
    public List<IntegralExchange> queryIntegralExchangeList(IntegralExchange integralExchange) {
        return integralExchangeMapper.queryAll(integralExchange);
    }

    @Override
    public void addIntegralExchange(IntegralExchange integralExchange) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        integralExchange.setMarkId(snowflake.nextIdStr());
        integralExchange.setCreateTime(DateUtil.date());
        integralExchange.setStatus(true);
        integralExchangeMapper.add(integralExchange);
    }

    @Override
    public void modifyIntegralExchange(IntegralExchange integralExchange) {
        integralExchange.setModifyTime(DateUtil.date());
        integralExchangeMapper.modify(integralExchange);
    }

    @Override
    public PageGrid<TicketExchange> queryTicketExchangePage(PageParam<TicketExchange> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("t.create_time " + param.getSort());
        PageInfo<TicketExchange> pageInfo = new PageInfo<>(ticketExchangeMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<TicketExchange> queryTicketExchangeList(TicketExchange ticketExchange) {
        return ticketExchangeMapper.queryAll(ticketExchange);
    }

    @Override
    public IntegralExpire queryByIntegralExpireId() {
        return integralExpireMapper.queryInfo();
    }


    @Override
    public void addIntegralExpire(IntegralExpire integralExpire) {
        IntegralExpire integral = integralExpireMapper.queryInfo();
        if (ObjectUtil.isEmpty(integral)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            integralExpire.setMarkId(snowflake.nextIdStr());
            integralExpire.setCreateTime(DateUtil.date());
            integralExpireMapper.add(integralExpire);
        } else {
            integralExpire.setMarkId(integral.getMarkId());
            integralExpire.setModifyTime(DateUtil.date());
            integralExpireMapper.modify(integralExpire);
        }
    }

    @Override
    public List<Map<String, String>> selectPushUser(String userId) {
        IntegralExpire expire = integralExpireMapper.queryInfo();
        return integralDetailMapper.selectPushUser(expire.getExpireTime(), expire.getPushDays(), userId);
    }
}
