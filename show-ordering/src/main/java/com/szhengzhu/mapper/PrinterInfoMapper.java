package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.PrinterInfo;
import com.szhengzhu.bean.ordering.vo.PrinterVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface PrinterInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(PrinterInfo record);

    int insertSelective(PrinterInfo record);

    PrinterInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PrinterInfo record);

    int updateByPrimaryKey(PrinterInfo record);

    List<PrinterVo> selectByExampleSelective(PrinterInfo data);

    int updateBatchStatus(@Param("printerIds") String[] printerIds,
            @Param("status") Integer status);

    @Update("update t_printer_info set server_status = #{status} where mark_id = #{printerId}")
    int updatePrinterById(@Param("printerId") String printerId, @Param("status") int status);

    @Select("select count(*) from t_printer_info where printer_code = #{printerCode} and mark_id <> #{markId}")
    int repeatRecords(@Param("printerCode") String printerCode, @Param("markId") String markId);
    
    @Select("select mark_id as printerId, printer_code as printerCode from t_printer_info where server_status=1 and store_id=#{storeId} order by sort")
    List<Map<String,String>> selectPrinterCode(@Param("storeId") String storeId);
    
    List<PrinterInfo> selectStorePrinterByCode(@Param("storeId") String storeId, @Param("printerCode") String printerCode);
}