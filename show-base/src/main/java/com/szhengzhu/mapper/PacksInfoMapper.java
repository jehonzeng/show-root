package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.PacksInfo;

public interface PacksInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(PacksInfo record);

    int insertSelective(PacksInfo record);

    PacksInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PacksInfo record);

    int updateByPrimaryKey(PacksInfo record);

    @Select("SELECT mark_id AS markId,theme AS theme,start_time AS startTime,end_time AS endTime, server_status AS serverStatus FROM t_packs_info WHERE mark_id=#{markId} AND NOW() > start_time and NOW() < end_time and server_status = 1")
    PacksInfo selectByEnd(@Param("markId")String markId);

    List<PacksInfo> selectByExampleSelective(PacksInfo data);
}