package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.SpecsItem;
import com.szhengzhu.bean.vo.Combobox;

public interface SpecsItemMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SpecsItem record);

    int insertSelective(SpecsItem record);

    SpecsItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SpecsItem record);

    int updateByPrimaryKey(SpecsItem record);
    
    List<SpecsItem> selectByExampleSelective(SpecsItem item);
    
    void updateBatchStatus(@Param("itemIds") String[] itemIds, @Param("status") int status);
    
    @Select("select mark_id as code, name from t_specs_item where status=1 and specs_id=#{specsId} ORDER BY create_time")
    List<Combobox> selectComboboxBySpecsId(@Param("specsId") String specsId);
}