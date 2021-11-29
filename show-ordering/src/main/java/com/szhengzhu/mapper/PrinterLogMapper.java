package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.PrinterLog;
import com.szhengzhu.bean.ordering.param.PrintLogParam;
import com.szhengzhu.bean.ordering.vo.PrintLogVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PrinterLogMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(PrinterLog record);

    int insertSelective(PrinterLog record);

    PrinterLog selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PrinterLog record);

    int updateByPrimaryKey(PrinterLog record);
    
    List<PrintLogVo> selectManageList(PrintLogParam param);
    
    int insertBatch(List<PrinterLog> list);
    
    @Select("select print_data from t_printer_log where mark_id=#{logId} ")
    String selectPrintData(@Param("logId") String logId);
    
    @Delete("DELETE FROM t_printer_log WHERE DATE(send_time) <=DATE_SUB(CURDATE(), INTERVAL 15 DAY)")
    int deleteOldLog();
    
    @Update("UPDATE t_printer_log set status_code=#{statusCode}, error_info=#{errorInfo} where mark_id=#{logId}")
    int updateStatusInfo(@Param("logId") String logId, @Param("statusCode") Integer statusCode, @Param("errorInfo") String errorInfo);
}