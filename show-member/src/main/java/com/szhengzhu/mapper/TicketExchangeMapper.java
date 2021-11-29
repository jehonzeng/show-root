package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.TicketExchange;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-17 14:25:14
 */
public interface TicketExchangeMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     TicketExchange queryById(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param ticketExchange 实例对象
     * @return 对象列表
     */
    List<TicketExchange> queryAll(TicketExchange ticketExchange);

    /**
     * 新增数据
     *
     * @param ticketExchange 实例对象
     */
    void add(TicketExchange ticketExchange);

    /**
     * 修改数据
     *
     * @param ticketExchange 实例对象
     */
    void modify(TicketExchange ticketExchange);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
