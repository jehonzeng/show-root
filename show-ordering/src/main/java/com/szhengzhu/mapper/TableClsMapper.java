package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.TableCls;
import com.szhengzhu.bean.vo.Combobox;

public interface TableClsMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TableCls record);

    int insertSelective(TableCls record);

    TableCls selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TableCls record);

    int updateByPrimaryKey(TableCls record);
    
    List<TableCls> selectByExampleSelective(TableCls tableCls);
    
    @Select("select mark_id as code, name, seats as other from t_table_cls where store_id=#{storeId} and status=1")
    List<Combobox> selectCombobox(@Param("storeId") String storeId);
    
    @Update("update t_table_cls set status=#{status} where mark_id=#{clsId}")
    int updateStatus(@Param("clsId") String clsId, @Param("status") Integer status);
}