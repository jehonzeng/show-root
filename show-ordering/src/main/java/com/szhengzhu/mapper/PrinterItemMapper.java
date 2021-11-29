package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.PrinterItem;
import com.szhengzhu.bean.ordering.print.PrintBase;
import com.szhengzhu.bean.ordering.print.PrintIndent;
import com.szhengzhu.bean.ordering.print.PrintProduce;
import com.szhengzhu.bean.ordering.vo.PrinterCommodityVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PrinterItemMapper {

    int deleteByPrimaryKey(PrinterItem key);

    int insert(PrinterItem record);

    int insertSelective(PrinterItem record);

    int insertBatchCommodity(@Param("printerId") String printerId,
            @Param("commoditys") List<PrinterCommodityVo> commoditys);

    @Delete("delete from t_printer_item where printer_id = #{printerId}")
    int deleteByPrinterId(@Param("printerId") String printerId);

    @Select("select commodity_id from t_printer_item where printer_id = #{printerId}")
    List<String> selectCommodityByPrinterId(@Param("printerId") String printerId);

    PrintIndent selectPrintIndent(@Param("indentId") String indentId, @Param("printerId") String printerId,
                                  @Param("timeCodes") String timeCodes, @Param("status") Integer status);

    List<PrintProduce> selectPrintProduce(@Param("indentId") String indentId, @Param("printerId") String printerId,
                                          @Param("timeCodes") String timeCodes);

    List<PrintBase> selectReturnProduce(@Param("detailId") String detailId);

}