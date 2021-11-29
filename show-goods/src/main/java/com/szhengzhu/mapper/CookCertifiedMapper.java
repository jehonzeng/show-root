package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.Cooker;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface CookCertifiedMapper {
    
    int insert(CookCertified record);

    int insertSelective(CookCertified record);
    
    int updateByPrimaryKeySelective(CookCertified record);
    
    List<CookCertified> selectByExampleSelective(CookCertified cookCertified);

    @Select("SELECT COUNT(*) FROM t_cook_certified  WHERE short_name = #{shortName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("shortName") String shortName, @Param("markId") String markId);
    
    List<Cooker> selectCookList(@Param("userId") String userId, @Param("number") Integer number);
    
    Cooker selectByUser(@Param("cooker") String cooker, @Param("userId") String userId);
    
    @Select("SELECT mark_id AS code, CONCAT(short_name, '-', phone) AS name FROM t_cook_certified WHERE certified = 1")
    List<Combobox> selectCombobox();

    @Select("SELECT mark_id,user_id,short_name,cook_style,cook_level,phone,province,city,address,certified,image_path,description,personal_signature FROM t_cook_certified WHERE mark_id = #{markId}")
    @ResultMap("BaseResultMap")
    CookCertified selectCookeById(@Param("markId") String markId);
}