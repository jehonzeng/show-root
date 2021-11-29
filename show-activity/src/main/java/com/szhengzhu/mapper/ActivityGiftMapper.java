package com.szhengzhu.mapper;

import com.szhengzhu.bean.activity.ActivityGift;
import com.szhengzhu.bean.wechat.vo.ActivityGiftVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface ActivityGiftMapper {

    int deleteByPrimaryKey(String markId);

    int insert(ActivityGift record);

    int insertSelective(ActivityGift record);

    ActivityGift selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ActivityGift record);

    int updateByPrimaryKey(ActivityGift record);

    List<ActivityGift> selectByExampleSelective(ActivityGift data);

    @Select("SELECT count(*) FROM t_activity_gift WHERE activity_id = #{activityId} AND participant_type= #{participantType} AND server_status = 1 AND mark_id <> #{markId}")
    int exitEffectiveActGift(@Param("markId") String markId, @Param("activityId") String activityId,@Param("participantType") Integer participantType);

    List<ActivityGiftVo> selectGiftsByActId(@Param("activityId") String activityId);

    ActivityGiftVo selectById(@Param("markId") String markId, @Param("type") Integer type);

    @Update("update t_activity_gift set exchange_total = exchange_total + 1 where mark_id = #{markId} and gift_total > exchange_total ")
    int updateEndTotal(@Param("markId") String markId);

    List<ActivityGiftVo> selectGiftList(@Param("activityId") String activityId,@Param("type") Integer type);
}