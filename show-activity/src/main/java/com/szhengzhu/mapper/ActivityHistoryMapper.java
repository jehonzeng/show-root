package com.szhengzhu.mapper;

import com.szhengzhu.bean.activity.ActivityHistory;
import com.szhengzhu.bean.vo.ActHistoryVo;
import com.szhengzhu.provider.ActivityProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface ActivityHistoryMapper {

    int deleteByPrimaryKey(String markId);

    int insert(ActivityHistory record);

    int insertSelective(ActivityHistory record);

    ActivityHistory selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ActivityHistory record);

    int updateByPrimaryKey(ActivityHistory record);

    List<ActHistoryVo> selectByExampleSelective(ActivityHistory data);

    @SelectProvider(type = ActivityProvider.class, method = "selectHistoryByType")
    int selectHistoryByType(@Param("activityId") String activityId,
            @Param("actGiftId") String actGiftId, @Param("userId") String userId,
            @Param("type") String type);
}