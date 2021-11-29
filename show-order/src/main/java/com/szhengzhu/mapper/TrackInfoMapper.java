package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.order.TrackInfo;

/**
 * @author Jehon Zeng
 */
public interface TrackInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TrackInfo record);

    int insertSelective(TrackInfo record);

    TrackInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TrackInfo record);

    int updateByPrimaryKey(TrackInfo record);
    
    TrackInfo selectByNoAndCom(@Param("com") String com, @Param("trackNo") String trackNo);
}