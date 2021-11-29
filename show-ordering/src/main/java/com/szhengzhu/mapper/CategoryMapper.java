package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.Category;
import com.szhengzhu.bean.ordering.vo.CategoryVo;
import com.szhengzhu.bean.vo.Combobox;

public interface CategoryMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    
    List<CategoryVo> selectByExampleSelective(Category info);
    
    void updateBatchStatus(@Param("cateIds") String[] cateIds, @Param("status") int status);
    
    @Select("SELECT mark_id as code, name from t_category_info where `status`= 1 and store_id=#{storeId} ORDER BY sort, create_time")
    List<Combobox> selectCombobox(@Param("storeId") String storeId);
}