package com.szhengzhu.service;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.StoreStaffVo;
import com.szhengzhu.bean.vo.UserInfoVo;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
public interface UserService {

    /**
     * 通过手机号获取管理员信息
     *
     * @date 2019年2月21日 下午12:29:14
     * @param phone
     * @return
     */
    UserInfo getManager(String phone);

    /**
     * 通过手机号获取线下店管理员信息
     *
     * @param phone
     * @return
     */
    UserInfo getRestaurantManager(String phone);

    /**
     * 后台获取管理人员手机号
     *
     * @date 2019年8月7日 下午5:02:11
     * @return
     */
    List<Map<String, String>> listManager();

    /**
     * 添加用户信息
     *
     * @date 2019年5月10日 上午11:42:19
     * @param userInfo
     * @return
     */
    UserInfo addUser(UserInfo userInfo);

    /**
     * 添加用户信息
     *
     * @date 2019年5月10日 上午11:42:19
     * @param userInfo
     * @return
     */
    String addXUser(UserInfo userInfo);

    /**
     * 修改用户信息
     *
     * @date 2019年9月11日 下午5:21:11
     * @param userInfo
     * @return
     */
    void modifyUser(UserInfo userInfo);

    /**
     * 获取用户分页列表
     *
     * @date 2019年2月25日 下午4:33:23
     * @param userPage
     * @return
     */
    PageGrid<UserVo> pageUser(PageParam<UserVo> userPage);

    /**
     * 获取不属于该角色的用户分页列表
     *
     * @date 2019年4月8日 下午5:28:06
     * @param userPage
     * @return
     */
    PageGrid<UserVo> pageOutRole(PageParam<UserVo> userPage);

    /**
     * 获取用户详细信息
     *
     * @date 2019年3月8日 下午3:10:51
     * @param markId
     * @return
     */
    UserInfo getUserById(String markId);

    /**
     * 通过openId获取用户信息
     *
     * @date 2019年4月24日 下午12:35:23
     * @param openId
     * @return
     */
    UserInfo getInfoByOpenId(String openId);

    /**
     * 通过小程序openid获取用户信息
     *
     * @param xopenId
     * @return
     */
    UserInfo getInfoByXopenId(String xopenId);

    /**
     * 通过用户unionid获取用户信息
     *
     * @param unionId
     * @return
     */
    UserInfo getInfoByUnionId(String unionId);

    /**
     * 通过token获取用户信息
     *
     * @date 2019年4月24日 下午3:39:52
     * @param token
     * @return
     */
    UserInfo getUserByToken(String token);

    /**
     * 获取所有用户下拉列表
     *
     * @date 2019年6月6日 下午4:49:09
     * @return
     */
    List<Combobox> getUserList();

    /**
     * 商城获取我的页面用户信息
     *
     * @date 2019年6月27日 下午3:25:48
     * @param userId
     * @return
     */
    UserInfoVo getMyInfo(String userId);

    /**
     * 通过手机号获取用户信息，如不存在则新建并返回
     *
     * @date 2019年7月5日 下午4:19:08
     * @param phone
     * @return
     */
    UserInfo getInfoByPhone(String phone);

    /**
     * 根据id验证内部人员信息
     *
     * @param markId
     * @return
     * @date 2019年9月9日
     */
    Boolean checkManage(String markId);

    /**
     * 获取门店人员分页信息
     *
     * @param base
     * @return
     */
    PageGrid<UserInfo> pageInStoreStaff(PageParam<StoreStaffVo> base);

    /**
     * 获取不在该门店人员分页信息
     *
     * @param base
     * @return
     */
    PageGrid<UserInfo> pageOutStoreStaff(PageParam<StoreStaffVo> base);

    /**
     * 获取未添加会员信息的用户
     *
     * @return
     */
    List<UserInfo> listOutMember();

    /**
     * 根据userId列表获取微信openId列表
     *
     * @param userIds
     * @return
     */
    List<String> listWopenIdByUserId(List<String> userIds);

    List<UserInfo> selectFocusUser();
}
