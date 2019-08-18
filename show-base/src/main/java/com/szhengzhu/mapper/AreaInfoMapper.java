package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;

public interface AreaInfoMapper {
    
    int deleteByPrimaryKey(String num);

    int insert(AreaInfo record);

    int insertSelective(AreaInfo record);

    AreaInfo selectByPrimaryKey(String num);

    int updateByPrimaryKeySelective(AreaInfo record);

    int updateByPrimaryKey(AreaInfo record);

    List<AreaInfo> selectAll();

    @Select("SELECT num AS code,`name` AS name FROM t_area_info WHERE super_id IS NULL")
    List<Combobox> selectProvinceList();

    @Select("SELECT b.num AS city ,c.num AS area FROM t_area_info a LEFT JOIN t_area_info b ON a.num = b.super_id LEFT JOIN t_area_info c ON b.num= c.super_id WHERE a.num = #{province}")
    List<AreaVo> selectCityAndAreaList(@Param("province")String province);
}