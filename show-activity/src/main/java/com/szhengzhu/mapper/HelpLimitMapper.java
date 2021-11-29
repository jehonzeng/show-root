package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.activity.HelpLimit;

public interface HelpLimitMapper {
    int deleteByPrimaryKey(String markId);

    int insert(HelpLimit record);

    int insertSelective(HelpLimit record);

    HelpLimit selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(HelpLimit record);

    int updateByPrimaryKey(HelpLimit record);

    @Select("select max_point as maxPoint,min_point as minPoint from t_help_limit where activity_id = #{activityId} and limit_point <= #{point} order by priority limit 1")
    HelpLimit selectByPriority(@Param("activityId") String activityId, @Param("point") Integer point);

    List<HelpLimit> selectByExampleSelective(HelpLimit data);
}