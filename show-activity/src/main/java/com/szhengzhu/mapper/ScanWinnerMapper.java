package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.activity.ScanWinner;

public interface ScanWinnerMapper {
    
    int insert(ScanWinner record);

    int insertSelective(ScanWinner record);
    
    @Select("SELECT count(1) FROM t_scan_winner WHERE win_id=#{winId}")
    int selectCount(@Param("winId") String winId);
}