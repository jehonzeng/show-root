package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.AccessoryInfo;

public interface AccessoryInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(AccessoryInfo record);

    int insertSelective(AccessoryInfo record);

    AccessoryInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(AccessoryInfo record);

    int updateByPrimaryKey(AccessoryInfo record);

    @Select("select count(*) from t_accessory_info where theme = #{theme} and mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);

    List<AccessoryInfo> selectByExampleSelective(AccessoryInfo data);
    
    @Select("SELECT mark_id AS accessoryId, theme, sale_price AS salePrice, 3 AS productType FROM t_accessory_info WHERE server_status=1 AND stock_size > 0 ORDER BY sort")
    List<Map<String, Object>> selectCartList();
}