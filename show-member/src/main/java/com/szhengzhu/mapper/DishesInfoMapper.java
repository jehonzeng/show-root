package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.DishesInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2020-12-10 14:07:18
 */
public interface DishesInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    DishesInfo queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dishesInfo 实例对象
     * @return 对象列表
     */
    List<DishesInfo> queryAll(DishesInfo dishesInfo);

    /**
     * 新增数据
     *
     * @param dishesInfo 实例对象
     * @return 影响行数
     */
    int insert(DishesInfo dishesInfo);

    /**
     * 修改数据
     *
     * @param dishesInfo 实例对象
     * @return 影响行数
     */
    int update(DishesInfo dishesInfo);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(@Param("markId") String markId);
}
