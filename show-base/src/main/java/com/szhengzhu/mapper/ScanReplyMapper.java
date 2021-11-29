package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.base.ScanReply;

public interface ScanReplyMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(ScanReply record);

    int insertSelective(ScanReply record);

    ScanReply selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ScanReply record);

    int updateByPrimaryKey(ScanReply record);
    
    List<ScanReply> selectByExampleSelective(ScanReply reply);
    
    List<ScanReply> selectByCode(@Param("scanCode") String scanCode);
}