package com.szhengzhu.mapper;

import com.szhengzhu.bean.activity.ActivityInfo;
import com.szhengzhu.bean.vo.ActivityModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface ActivityInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    ActivityInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);

    List<ActivityModel> selectByExampleSelective(ActivityInfo data);

    @Select("select count(*) from t_activity_info where theme = #{theme} and mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);

    @Select("select mark_id as markId,theme,start_time as startTime,stop_time as stopTime,check_time as checkTime,end_time as endTime,server_status,award_url as awardUrl,image_path as imagePath from t_activity_info where mark_id = #{markId} and NOW() > check_time and NOW() < end_time and server_status = 1")
    ActivityInfo selectByEnd(@Param("markId") String markId);

    @Select("select mark_id as markId,theme,start_time as startTime,stop_time as stopTime,check_time as checkTime,end_time as endTime,server_status,award_url as awardUrl,image_path as imagePath from t_activity_info where mark_id = #{markId} and NOW() > start_time and NOW() < stop_time and server_status = 1")
    ActivityInfo selectByStop(@Param("markId") String markId);

    @Select("select mark_id, theme, start_time, stop_time, check_time, end_time, server_status, award_url,image_path from t_activity_info where server_status = 1 and mark_id = #{markId}")
    @ResultMap("BaseResultMap")
    ActivityInfo selectByMark(@Param("markId") String markId);
    
    
    
    
    
    
    
    
    

}