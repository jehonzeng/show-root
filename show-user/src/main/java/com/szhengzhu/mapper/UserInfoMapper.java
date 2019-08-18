package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserVo;

public interface UserInfoMapper {
    
    String sql = "SELECT mark_id, nick_name, phone, header_img, gender, city, province, "
                + "country, language, user_level, wopen_id, xopen_id, union_id, wechat_status "
                + "FROM t_user_info ";

    int deleteByPrimaryKey(String markId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    @Select(sql + "WHERE phone = #{phone}")
    @ResultMap("BaseResultMap")
    UserInfo selectByPhone(@Param("phone") String phone);
    
    List<UserVo> selectByExampleSelective(UserVo userInfo);
    
    List<UserVo> selectNotInByExampleSelective(UserVo userInfo);
    
    @Select(sql + " WHERE wopen_id=#{openId}")
    @ResultMap("BaseResultMap")
    UserInfo selectByOpenId(@Param("openId") String openId);
    
    @Select(sql + " WHERE mark_id IN (SELECT user_id FROM t_user_token WHERE token=#{token})")
    @ResultMap("BaseResultMap")
    UserInfo selectByToken(@Param("token") String token);

    @Select("SELECT mark_id AS code,nick_name AS name FROM t_user_info WHERE wechat_status  = 1")
    List<Combobox> selectComboboxList();
    
    @Select("SELECT nick_name AS nickName, header_img AS headerImg,IFNULL(SUM(i.integral_limit), 0) AS integral FROM t_user_info u LEFT JOIN t_user_integral i ON u.mark_id=i.user_id WHERE u.mark_id=#{userId}")
    Map<String, Object> selectMyInfo(@Param("userId") String userId);
 }