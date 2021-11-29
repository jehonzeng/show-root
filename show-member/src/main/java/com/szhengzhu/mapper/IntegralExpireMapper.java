package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.IntegralExpire;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-25 17:08:18
 */
public interface IntegralExpireMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @return 实例对象
     */
    IntegralExpire queryInfo();

    /**
     * 新增数据
     *
     * @param integralExpire 实例对象
     */
    void add(IntegralExpire integralExpire);

    /**
     * 修改数据
     *
     * @param integralExpire 实例对象
     */
    void modify(IntegralExpire integralExpire);
}
