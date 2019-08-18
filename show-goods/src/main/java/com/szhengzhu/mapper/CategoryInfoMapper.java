package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.provider.CategoryProvider;

public interface CategoryInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(CategoryInfo record);

    int insertSelective(CategoryInfo record);

    CategoryInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(CategoryInfo record);

    int updateByPrimaryKey(CategoryInfo record);

    @Select("SELECT COUNT(*) FROM t_category_info WHERE name= #{name} AND mark_id <> #{markId}")
    int repeatRecords(@Param("name") String name, @Param("markId") String markId);

    List<CategoryInfo> selectByExampleSelective(CategoryInfo record);

    @Select("SELECT mark_id AS markIdValue,name AS nameKey FROM t_category_info WHERE server_status = #{serverStatus}")
    List<Combobox> selectValidComboboxList(@Param("serverStatus") Boolean serverStatus);

    @Select("SELECT mark_id AS markId,super_id AS superId,(SELECT name FROM t_category_info WHERE mark_id = super_id) AS superName ,name AS name,level AS level FROM t_category_info WHERE mark_id = #{markId}")
    CategoryInfo selectByMark(@Param("markId") String markId);

    @SelectProvider(type = CategoryProvider.class, method = "selectDownList")
    List<Combobox> selectDownList(@Param("serverStatus") String serverStatus);

    @Select("SELECT super_id AS code,name AS name FROM t_category_info order by sort")
    List<Combobox> selectSuperList();
}