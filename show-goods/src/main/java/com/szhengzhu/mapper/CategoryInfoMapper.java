package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.provider.CategoryProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author Administrator
 */
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

//    @Select("SELECT c.mark_id AS markId,c.super_id AS superId,(SELECT s.name FROM t_category_info s WHERE s.mark_id = c.super_id) AS superName ,c.name AS name,c.level AS level FROM t_category_info c WHERE c.mark_id = #{markId}")
//    CategoryInfo selectByMark(@Param("markId") String markId);

    @SelectProvider(type = CategoryProvider.class, method = "selectDownList")
    List<Combobox> selectDownList(@Param("serverStatus") String serverStatus);
}