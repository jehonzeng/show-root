package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.PendDishes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2020-12-10 14:07:29
 */
public interface PendDishesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    PendDishes queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param pendDishes 实例对象
     * @return 对象列表
     */
    List<PendDishes> queryAll(PendDishes pendDishes);

    /**
     * 新增数据
     *
     * @param pendDishes 实例对象
     * @return 影响行数
     */
    int insert(PendDishes pendDishes);

    /**
     * 修改数据
     *
     * @param pendDishes 实例对象
     * @return 影响行数
     */
    int update(PendDishes pendDishes);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(String markId);
}
