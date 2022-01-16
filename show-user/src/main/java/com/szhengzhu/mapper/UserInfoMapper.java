package com.szhengzhu.mapper;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.StoreStaffVo;
import com.szhengzhu.bean.vo.UserInfoVo;
import com.szhengzhu.bean.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(@Param("markId") String markId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectManageByPhone(@Param("phone") String phone);

    UserInfo selectRestaurantManageByPhone(@Param("phone") String phone);

    UserInfo selectByPhone(@Param("phone") String phone);

    UserInfo selectByOpenId(@Param("openId") String openId);

    UserInfo selectByXopenId(@Param("xopenId") String xopenId);

    UserInfo selectByUnionId(@Param("unionId") String unionId);

    UserInfo selectByToken(@Param("token") String token);

    List<UserVo> selectByExampleSelective(UserVo userInfo);

    List<UserVo> selectOutRoleByExampleSelective(UserVo userInfo);

    @Select("SELECT IF(real_name IS NULL || real_name = '', nick_name, real_name) AS realName, IFNULL(phone,'') AS phone, wopen_id AS wopenId FROM t_user_info WHERE mark_id IN (SELECT ur.user_id FROM t_user_role ur left join t_role_info r on ur.role_id = r.mark_id WHERE r.role_code='MANAGE')")
    List<Map<String, String>> selectManager();

    @Select("SELECT mark_id AS code, nick_name AS name FROM t_user_info WHERE wechat_status=1")
    List<Combobox> selectComboboxList();

    @Select("SELECT mark_id AS userId, nick_name AS nickName, header_img AS headerImg FROM t_user_info u WHERE u.mark_id=#{userId}")
    UserInfoVo selectMyInfo(@Param("userId") String userId);

    @Select("SELECT ur.user_id FROM t_user_role ur left join t_role_info r on ur.role_id = r.mark_id WHERE r.role_code='MANAGE' ")
    List<String> selectManageList();

    @Update("UPDATE t_user_info SET wechat_status=#{wechatStatus} WHERE wopen_id=#{openId}")
    void updateWechatStatusByOpenId(@Param("openId") String openId, @Param("wechatStatus") int wechatStatus);

    List<UserInfo> selectInStoreByExampleSelective(StoreStaffVo data);

    List<UserInfo> selectOutStoreByExampleSelective(StoreStaffVo userInfo);

    @Select("SELECT ur.user_id FROM t_user_role ur left join t_role_info r on ur.role_id = r.mark_id WHERE r.role_code='MANAGE' AND ur.user_id not in (select user_id from t_manager_code)")
    List<String> selectOutManageCodeList();

    List<UserInfo> selectOutMember();

    List<String> selectWopenIdsByUserId(List<String> userIds);

    List<UserInfo> selectFocusUser();
}
