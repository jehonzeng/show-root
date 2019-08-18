package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.user.UserToken;

public interface UserTokenMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);
    
    @Select("SELECT mark_id, user_id, refresh_time, token FROM t_user_token WHERE token=#{token}")
    @ResultMap("BaseResultMap")
    UserToken selectByToken(String token);
    
    @Update("UPDATE t_user_token SET refresh_time = NOW() WHERE token = #{token}")
    int refreshByToken(String token);
    
    @Select("SELECT mark_id, user_id, refresh_time, token FROM t_user_token WHERE user_id=#{userId}")
    @ResultMap("BaseResultMap")
    UserToken selectByUser(@Param("userId") String userId);
}