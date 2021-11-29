package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.ColumnInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface ColumnInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(ColumnInfo record);

    int insertSelective(ColumnInfo record);

    ColumnInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ColumnInfo record);

    int updateByPrimaryKey(ColumnInfo record);

    @Select("SELECT COUNT(*) FROM t_column_info WHERE theme = #{theme} AND mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);

    List<ColumnInfo> selectByExampleSelective(ColumnInfo record);
}