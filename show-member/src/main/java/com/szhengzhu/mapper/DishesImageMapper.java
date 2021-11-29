package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.DishesImage;
import com.szhengzhu.bean.member.vo.DishesImageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2020-12-10 14:07:28
 */
public interface DishesImageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    DishesImage queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param image 实例对象
     * @return 对象列表
     */
    List<DishesImage> queryAll(DishesImageVo image);

    /**
     * 新增数据
     *
     * @param image 实例对象
     * @return 影响行数
     */
    int insert(DishesImageVo image);

    /**
     * 修改数据
     *
     * @param image 实例对象
     * @return 影响行数
     */
    int update(DishesImageVo image);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(@Param("markId") String markId);
}
