package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.TableStatus;
import com.szhengzhu.bean.vo.Combobox;

public interface TableStatusMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TableStatus record);

    int insertSelective(TableStatus record);

    TableStatus selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TableStatus record);

    int updateByPrimaryKey(TableStatus record);
    
    List<TableStatus> selectByExampleSelective(TableStatus tableStatus);
    
    @Select("select code, name as name from t_table_status where store_id=#{storeId} and status=1")
    List<Combobox> selectCombobox(@Param("storeId") String storeId);
    
    @Update("update t_table_status set status=#{status} where mark_id=#{statusId}")
    int updateStatus(@Param("statusId") String statusId, @Param("status") Integer status);
}