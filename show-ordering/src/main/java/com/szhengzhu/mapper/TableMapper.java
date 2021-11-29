package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Table;
import com.szhengzhu.bean.ordering.TableReservation;
import com.szhengzhu.bean.ordering.param.TableParam;
import com.szhengzhu.bean.ordering.vo.TableBaseVo;
import com.szhengzhu.bean.ordering.vo.TableVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TableMapper {

    int deleteByPrimaryKey(String markId);

    int insert(Table record);

    int insertSelective(Table record);

    Table selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Table record);

    int updateByPrimaryKey(Table record);

    List<TableVo> selectByExampleSelective(TableParam tableParam);

    @Update("update t_table_info set status=#{status} where mark_id=#{tableId}")
    int updateStatus(@Param("tableId") String tableId, @Param("status") Integer status);

    @Update("update t_table_info set table_status=#{tableStatus} where mark_id=#{tableId}")
    int updateTableStatus(@Param("tableId") String tableId, @Param("tableStatus") String tableStatus);

    List<TableBaseVo> selectResByStore(TableReservation tableReservation);

    @Update("UPDATE t_table_info SET open_time=NULL, man_num=0, temp_num=NULL, table_status='TS01' WHERE mark_id=#{tableId}")
    int clearTable(@Param("tableId") String tableId);

    Table selectByStoreCode(@Param("storeId") String storeId, @Param("tableCode")  String tableCode);

    @Select("select mark_id from t_table_info where qr_url=#{qrUrl}")
    String selectIdByUrl(@Param("qrUrl") String qrUrl);

    @Select("select temp_num from t_table_info where mark_id=#{tableId}")
    String selectTempNumById(@Param("tableId") String tableId);
}
