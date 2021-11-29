package com.szhengzhu.service;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

public interface IntegralService {

    /**
     * 获取用户积分分页列表
     *
     * @param param
     * @return
     * @date 2019年2月26日 上午11:09:16
     */
    PageGrid<IntegralDetail> pageIntegral(PageParam<IntegralDetail> param);

    /**
     * 获取用户积分记录
     *
     * @param param
     * @return
     */
    PageGrid<IntegralDetail> pageUserIntegral(PageParam<String> param);

    /**
     * 添加用户积分
     *
     * @param integral
     * @date 2019年7月26日 上午10:42:12
     */
    void addTicketExchange(IntegralDetail integral);

    /**
     * 消费积分
     *
     * @param integralDetail
     */
    void consume(IntegralDetail integralDetail);

    /**
     * 获取用户总积分
     *
     * @param userId
     * @return
     */
    Integer getTotalByUserId(String userId);

    /**
     * 获取会员积分汇总
     *
     * @param userId
     */
    Map<String, Integer> getSumByUser(String userId);

    /**
     * 查询会员积分明细
     *
     * @return
     */
    PageGrid<IntegralDetail> integralRecord(PageParam<MemberRecord> param);

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    IntegralExchange queryByIntegralExchangeId(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param integralExchange 实例对象
     * @return 对象列表
     */
    List<IntegralExchange> queryIntegralExchangeList(IntegralExchange integralExchange);

    /**
     * 新增数据
     *
     * @param integralExchange 实例对象
     */
    void addIntegralExchange(IntegralExchange integralExchange);

    /**
     * 修改数据
     *
     * @param integralExchange 实例对象
     */
    void modifyIntegralExchange(IntegralExchange integralExchange);


    /**
     * 通过实体对象筛选查询
     *
     * @param param 分页对象
     * @return 对象列表
     */
    PageGrid<TicketExchange> queryTicketExchangePage(PageParam<TicketExchange> param);

    List<TicketExchange> queryTicketExchangeList(TicketExchange ticketExchange);

    /**
     * 通过主键ID查询单条数据
     *
     * @return 实例对象
     */
    IntegralExpire queryByIntegralExpireId();

    /**
     * 新增数据
     *
     * @param integralExpire 实例对象
     */
    void addIntegralExpire(IntegralExpire integralExpire);

    List<Map<String, String>> selectPushUser(String userId);
}
