package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.Tag;
import com.szhengzhu.bean.vo.Combobox;

public interface TagMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
    
    List<Tag> selectByExampleSelective(Tag icon);
    
    void updateBatchStatus(@Param("tagIds") String[] tagIds, @Param("status") int status);
    
    @Select("select mark_id as code, name from t_tag_info where status=1 and store_id=#{storeId}")
    List<Combobox> selectCombobox(@Param("storeId") String storeId);
}