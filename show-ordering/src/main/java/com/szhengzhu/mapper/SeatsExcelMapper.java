package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SeatsExcelMapper {

	/**
	 * 查询日期之间的不同座位类型的座位数
	 * 
	 * @param beginDate 开始日期
	 * @param lastDate  结束日期
	 * @return
	 */
	List<Map<String, Object>> seatsInfo(@Param("beginDate") Date beginDate, @Param("lastDate") Date lastDate);

	/**
	 * 查询日期之间的不同座位类型的情况
	 * 
	 * @param beginDate 开始日期
	 * @param lastDate  结束日期
	 * @return
	 */
	List<Map<String, Object>> seatsTypeInfo(@Param("beginDate") Date beginDate, @Param("lastDate") Date lastDate);
}
