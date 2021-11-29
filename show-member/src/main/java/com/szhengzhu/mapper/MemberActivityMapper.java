package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MemberActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2020-12-16 14:30:38
 */
public interface MemberActivityMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    MemberActivity queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param memberActivity 实例对象
     * @return 对象列表
     */
    List<MemberActivity> queryAll(MemberActivity memberActivity);

    /**
     * 新增数据
     *
     * @param memberActivity 实例对象
     * @return 影响行数
     */
    int insert(MemberActivity memberActivity);

    /**
     * 修改数据
     *
     * @param memberActivity 实例对象
     * @return 影响行数
     */
    int update(MemberActivity memberActivity);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteActivity(@Param("markId") String markId);
}
