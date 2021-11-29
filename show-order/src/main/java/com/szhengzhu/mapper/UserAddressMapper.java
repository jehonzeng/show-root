package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.UserAddress;

/**
 * @author Jehon Zeng
 */
public interface UserAddressMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
    
    List<UserAddress> selectByExampleSelective(UserAddress userAddress);
    
    @Select("select mark_id, user_name, phone, longitude, latitude, area, city, province, user_address, address_type, user_id, create_time, default_or, server_status, " + 
            "(SELECT GROUP_CONCAT(a.name SEPARATOR '') FROM db_base.t_area_info a WHERE a.num in (province,city,area) ORDER BY a.num) AS displayValue " + 
            "FROM t_user_address WHERE user_id=#{userId} AND server_status=1")
    @ResultMap("BaseResultMap")
    List<UserAddress> selectByUser(@Param("userId") String userId);
    
    @Select("SELECT count(1) FROM t_user_address WHERE user_id=#{userId} AND default_or=1")
    int countDefault(@Param("userId") String userId);
    
    @Update("UPDATE t_user_address SET default_or=0 WHERE user_id=#{userId} AND default_or=1")
    int updateNoDefaultByUser(@Param("userId") String userId);
    
    @Select("SELECT mark_id, user_name, phone,  area, city, province, user_address,address_type," + 
            "(SELECT GROUP_CONCAT(a.name SEPARATOR '') FROM db_base.t_area_info a WHERE a.num in (province,city,area) ORDER BY a.num) AS displayValue " + 
            "FROM t_user_address WHERE user_id=#{userId} AND server_status=1 AND default_or=1 LIMIT 1")
    @ResultMap("BaseResultMap")
    UserAddress selectDefByUser(@Param("userId") String userId);
}