package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.member.IntegralAccount;

/**
 * @author Administrator
 */
public interface IntegralAccountMapper {

    /**
     * 根据主键
     *
     * @param markId
     * @return 删除数量
     */
    int deleteByPrimaryKey(String markId);

    /**
     * 全局添加
     *
     * @param record 实例
     * @return 添加数量
     */
    int insert(IntegralAccount record);

    /**
     * 局部添加
     *
     * @param record 实例
     * @return 添加数量
     */
    int insertSelective(IntegralAccount record);

    /**
     * 根据主键获取
     *
     * @param markId 主键
     * @return IntegralAccount
     */
    IntegralAccount selectByPrimaryKey(String markId);

    /**
     * 局部更新
     *
     * @param record 实例
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(IntegralAccount record);

    /**
     * 根据主键全局更新
     * @param record 实例
     * @return 更新数量
     */
    int updateByPrimaryKey(IntegralAccount record);

    /**
     * 根据用户id获取信息
     *
     * @param userId 用户id
     * @return IntegralAccount
     */
    IntegralAccount selectByUser(@Param("userId") String userId);

    /**
     * 根据用户id获取用户总积分
     *
     * @param userId 用户id
     * @return 总积分
     */
    @Select("select ifnull(total_integral, 0) from t_integral_account where user_id=#{userId}")
    Integer selectTotalByUser(@Param("userId") String userId);
}