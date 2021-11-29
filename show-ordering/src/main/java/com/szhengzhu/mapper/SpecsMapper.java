package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Specs;
import com.szhengzhu.bean.ordering.vo.SpecsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SpecsMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Specs record);

    int insertSelective(Specs record);

    Specs selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Specs record);

    int updateByPrimaryKey(Specs record);
    
    List<Specs> selectByExampleSelective(Specs specs);
    
    void updateBatchStatus(@Param("specsIds") String[] specsIds, @Param("status") int status);
    
    @Select("select mark_id as code, name from t_specs_info where status=1 and store_id=#{storeId} ORDER BY create_time")
    List<SpecsVo> selectCombobox(@Param("storeId") String storeId);
    
    List<SpecsVo> selectVoByCateId(@Param("cateIds") String[] cateIds, @Param("storeId") String storeId);
    
    List<Map<String, String>> selectByName(@Param("name") String name);
}