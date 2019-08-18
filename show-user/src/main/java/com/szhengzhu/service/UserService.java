package com.szhengzhu.service;

import java.util.List;
import java.util.Map;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface UserService {

    /**
     * 通过手机号获取管理员信息
     * 
     * @date 2019年2月21日 下午12:29:14
     * @param phone
     * @return
     */
    Result<UserInfo> getManager(String phone);
    
    /**
     * 添加用户信息
     * 
     * @date 2019年5月10日 上午11:42:19
     * @param userInfo
     * @return
     */
    Result<UserInfo> addUser(UserInfo userInfo);
    
    /**
     * 获取用户分页列表
     * 
     * @date 2019年2月25日 下午4:33:23
     * @param userPage
     * @return
     */
    Result<PageGrid<UserVo>> pageUser(PageParam<UserVo> userPage);
    
    /**
     * 获取不属于该角色的用户分页列表
     * 
     * @date 2019年4月8日 下午5:28:06
     * @param userPage
     * @return
     */
    Result<PageGrid<UserVo>> pageUserNotIn(PageParam<UserVo> userPage);
    
    /**
     * 获取用户详细信息
     * 
     * @date 2019年3月8日 下午3:10:51
     * @param markId
     * @return
     */
    Result<UserInfo> getUserById(String markId);
    
    /**
     * 通过openId获取用户信息
     * 
     * @date 2019年4月24日 下午12:35:23
     * @param openId
     * @return
     */
    Result<UserInfo> getUserByOpenId(String openId);
    
    /**
     * 通过token获取用户信息
     * 
     * @date 2019年4月24日 下午3:39:52
     * @param token
     * @return
     */
    Result<UserInfo> getUserByToken(String token);

    /**
     * 获取所有用户下拉列表
     * 
     * @date 2019年6月6日 下午4:49:09
     * @return
     */
    Result<List<Combobox>> getUserList();
    
    /**
     * 商城获取我的页面用户信息
     * 
     * @date 2019年6月27日 下午3:25:48
     * @param userId
     * @return
     */
    Result<Map<String, Object>> getMyInfo(String userId);
    
    /**
     * 通过手机号获取用户信息，如不存在则新建并返回
     * 
     * @date 2019年7月5日 下午4:19:08
     * @param phone
     * @return
     */
    Result<UserInfo> getByPhone(String phone);
    
}