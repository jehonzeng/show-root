package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.IntegralExchange;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-16 17:18:19
 */
public interface IntegralExchangeMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     IntegralExchange queryById(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param integralExchange 实例对象
     * @return 对象列表
     */
    List<IntegralExchange> queryAll(IntegralExchange integralExchange);

    /**
     * 新增数据
     *
     * @param integralExchange 实例对象
     */
    void add(IntegralExchange integralExchange);

    /**
     * 修改数据
     *
     * @param integralExchange 实例对象
     */
    void modify(IntegralExchange integralExchange);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
