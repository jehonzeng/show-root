package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.order.HolidayInfo;

public interface HolidayInfoMapper {
    
    int deleteByPrimaryKey(Date holiday);

    int insert(HolidayInfo record);

    int insertSelective(HolidayInfo record);
    
    int insertBatch(List<Date> dateList);
    
    @Select("SELECT COUNT(*) FROM t_holiday_info WHERE TO_DAYS(holiday)=TO_DAYS(#{holiday})")
    int countHoliday(@Param("holiday") Date holiday);
    
    @Select("SELECT holiday FROM t_holiday_info WHERE holiday BETWEEN #{start} AND #{end}")
    List<HolidayInfo> selectHoliday(@Param("start") String start, @Param("end") String end);
    
    @Select("SELECT holiday FROM t_holiday_info WHERE holiday BETWEEN #{start} AND #{end}")
    Set<String> selectHolidaySet(@Param("start") String start, @Param("end") String end);
    
    @Select("SELECT holiday FROM t_holiday_info WHERE holiday > NOW()")
    Set<Date> selectNewHolidaySet();
    
    @Select("SELECT holiday FROM t_holiday_info WHERE holiday=#{date}")
    HolidayInfo selectByDate(@Param("date") Date date);
}