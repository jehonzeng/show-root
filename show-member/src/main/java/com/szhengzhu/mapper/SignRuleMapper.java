package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.SignRule;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-09 10:24:30
 */
public interface SignRuleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     SignRule queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param signRule 实例对象
     * @return 对象列表
     */
    List<SignRule> queryAll(SignRule signRule);

    /**
     * 新增数据
     *
     * @param signRule 实例对象
     */
    void add(SignRule signRule);

    /**
     * 修改数据
     *
     * @param signRule 实例对象
     */
    void modify(SignRule signRule);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);

}
