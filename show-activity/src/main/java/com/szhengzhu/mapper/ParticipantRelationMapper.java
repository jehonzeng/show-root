package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.activity.ParticipantRelation;
import com.szhengzhu.provider.ActivityProvider;

public interface ParticipantRelationMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ParticipantRelation record);

    int insertSelective(ParticipantRelation record);

    ParticipantRelation selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ParticipantRelation record);

    int updateByPrimaryKey(ParticipantRelation record);

    @Select("SELECT IFNULL(sum(point),0) FROM t_participant_relation WHERE server_status = 1 AND activity_id = #{activityId} AND father_id = #{fatherId} ")
    int selectPoint(@Param("activityId")String activityId, @Param("fatherId") String fatherId);

    @SelectProvider(type = ActivityProvider.class,method="selectRelationByType")
    int selectRelationCountByType(@Param("activityId") String activityId,@Param("sonId") String sonId, @Param("limited") String limited);
}