package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.ReceiveDishes;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2020-12-10 14:07:31
 */
public interface ReceiveDishesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    ReceiveVo queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param receiveDishes 实例对象
     * @return 对象列表
     */
    List<ReceiveVo> queryAll(ReceiveDishes receiveDishes);

    /**
     * 新增数据
     *
     * @param receiveDishes 实例对象
     * @return 影响行数
     */
    int insert(ReceiveDishes receiveDishes);

    /**
     * 修改数据
     *
     * @param receiveDishes 实例对象
     * @return 影响行数
     */
    int update(ReceiveDishes receiveDishes);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(@Param("markId") String markId);

    /**
     * 查询领取菜品的详细信息
     *
     * @param markId 主键
     * @return 实例对象
     */
    List<ReceiveVo> selectByDish(@Param("markId") String markId);
}
