package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.StoreInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface StoreInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    StoreInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(StoreInfo record);

    int updateByPrimaryKey(StoreInfo record);

    @Select("SELECT COUNT(*) FROM t_store_info WHERE store_name = #{storeName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("storeName") String storeName, @Param("markId") String markId);

    List<StoreInfo> selectByExampleSelective(StoreInfo data);

    @Select("select mark_id as code,store_name as name from t_store_info where server_status = 1 ")
    List<Combobox> selectComboboxList();
}