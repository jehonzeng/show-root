package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.activity.ScanWin;

public interface ScanWinMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ScanWin record);

    int insertSelective(ScanWin record);

    ScanWin selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ScanWin record);

    int updateByPrimaryKey(ScanWin record);
    
    List<ScanWin> selectByExampleSelective(ScanWin win);
    
    ScanWin selectByCode(@Param("scanCode") String scanCode);
}