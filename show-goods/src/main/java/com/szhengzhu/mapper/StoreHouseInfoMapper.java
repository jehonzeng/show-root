package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface StoreHouseInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(StoreHouseInfo record);

    int insertSelective(StoreHouseInfo record);

    StoreHouseInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(StoreHouseInfo record);

    int updateByPrimaryKey(StoreHouseInfo record);

    @Select("SELECT COUNT(*) FROM t_storehouse_info WHERE `name`= #{name} AND mark_id <> #{markId}")
    int repeatRecords(@Param("name") String name, @Param("markId") String markId);
    
    List<StoreHouseInfo> selectByExampleSelective(StoreHouseInfo record);
    
    @Select("SELECT mark_id AS code, `name` FROM t_storehouse_info WHERE server_status=1")
    List<Combobox> selectCombobox();
}