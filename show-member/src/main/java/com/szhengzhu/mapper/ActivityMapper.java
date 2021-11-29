package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.Activity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface ActivityMapper {

    /**
     * 根据主键删除活动信息
     *
     * @param markId
     * @return
     */
    int deleteByPrimaryKey(String markId);

    /**
     * 全局添加
     *
     * @param record
     * @return
     */
    int insert(Activity record);

    /**
     * 局部添加
     * @param record
     * @return
     */
    int insertSelective(Activity record);

    /**
     * 根据主键获取实例
     *
     * @param markId
     * @return
     */
    Activity selectByPrimaryKey(String markId);

    /**
     * 局部更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * 全局更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Activity record);

    /**
     * 根据实例获取列表
     *
     * @param activity
     * @return
     */
    List<Activity> selectByExampleSelective(Activity activity);

    /**
     * 根据code获取有效信息
     *
     * @param code
     * @return
     */
    Activity selectWellByCode(@Param("code") String code);

    @Update("update t_activity_info set status = #{status} where code=#{code}")
    void updateStatus(@Param("code") String code, @Param("status") Boolean status);
}