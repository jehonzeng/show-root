package com.szhengzhu.mapper;

import com.szhengzhu.bean.order.PushTemplate;

import java.util.List;

/**
 * @author makejava
 * @since 2021-07-29 14:35:54
 */
public interface PushTemplateMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     PushTemplate queryById(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param pushTemplate 实例对象
     * @return 对象列表
     */
    List<PushTemplate> queryAll(PushTemplate pushTemplate);

    /**
     * 新增数据
     *
     * @param pushTemplate 实例对象
     */
    void add(PushTemplate pushTemplate);

    /**
     * 修改数据
     *
     * @param pushTemplate 实例对象
     */
    void modify(PushTemplate pushTemplate);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
