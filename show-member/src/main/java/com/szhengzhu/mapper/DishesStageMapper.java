package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.DishesStage;
import com.szhengzhu.bean.member.vo.DishesStageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2020-12-10 14:07:25
 */
public interface DishesStageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    DishesStage queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param stage 实例对象
     * @return 对象列表
     */
    List<DishesStage> queryAll(DishesStageVo stage);

    /**
     * 新增数据
     *
     * @param stage 实例对象
     * @return 影响行数
     */
    int insert(DishesStageVo stage);

    /**
     * 修改数据
     *
     * @param stage 实例对象
     * @return 影响行数
     */
    int update(DishesStageVo stage);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(@Param("markId") String markId);

    /**
     * 通过菜品id和排序查询信息
     *
     * @param dishesId 菜品id
     * @return 实例对象
     */
    DishesStage selectByStage(@Param("dishesId") String dishesId, @Param("sort") int sort);
}
