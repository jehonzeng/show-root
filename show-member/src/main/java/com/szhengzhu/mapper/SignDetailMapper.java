package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.SignDetail;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-17 15:30:16
 */
public interface SignDetailMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     SignDetail queryById(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param signDetail 实例对象
     * @return 对象列表
     */
    List<SignDetail> queryAll(SignDetail signDetail);

    /**
     * 新增数据
     *
     * @param signDetail 实例对象
     */
    void add(SignDetail signDetail);

    /**
     * 修改数据
     *
     * @param signDetail 实例对象
     */
    void modify(SignDetail signDetail);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
