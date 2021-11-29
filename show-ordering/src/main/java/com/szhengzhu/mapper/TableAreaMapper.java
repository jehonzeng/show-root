package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.TableArea;
import com.szhengzhu.bean.vo.Combobox;

public interface TableAreaMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TableArea record);

    int insertSelective(TableArea record);

    TableArea selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TableArea record);

    int updateByPrimaryKey(TableArea record);
    
    List<TableArea> selectByExampleSelective(TableArea tableArea);
    
    @Select("select mark_id as code, name, code as other from t_table_area where store_id=#{storeId} and status=1")
    List<Combobox> selectCombobx(@Param("storeId") String storeId);
    
    @Update("update t_table_area set status=#{status} where mark_id=#{areaId}")
    int updateStatus(@Param("areaId") String areaId, @Param("status") Integer status);
}