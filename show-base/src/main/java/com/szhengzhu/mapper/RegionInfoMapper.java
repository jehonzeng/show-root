package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.RegionInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface RegionInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(RegionInfo record);

    int insertSelective(RegionInfo record);

    RegionInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(RegionInfo record);

    int updateByPrimaryKey(RegionInfo record);

    @Select("SELECT COUNT(*) FROM t_region_info WHERE store_name = #{storeName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("storeName") String storeName, @Param("markId") String markId);

    List<RegionInfo> selectByExampleSelective(RegionInfo data);

    @Select("select mark_id as code,store_name as name from t_region_info where server_status = 1 ")
    List<Combobox> selectComboboxList();
}