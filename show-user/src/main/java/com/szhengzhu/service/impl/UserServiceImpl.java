package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.*;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.UserInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private Redis redis;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    @Override
    public UserInfo getManager(String phone) {
        UserInfo userInfo = userInfoMapper.selectManageByPhone(phone);
        ShowAssert.checkNull(userInfo, StatusCode._5000);
        return userInfo;
    }

    @Override
    public UserInfo getRestaurantManager(String phone) {
        UserInfo userInfo = userInfoMapper.selectRestaurantManageByPhone(phone);
        ShowAssert.checkNull(userInfo, StatusCode._5000);
        return userInfo;
    }

    @Cacheable(value = "user::manage::list")
    @Override
    public List<Map<String, String>> listManager() {
        return userInfoMapper.selectManager();
//        List<Map<String, String>> managerList = (List<Map<String, String>>) redis.get("user:manage:list");
//        if (managerList != null) {
//            return managerList);
//        }
//        Lock lock = new ReentrantLock();
//        lock.lock();
//        try {
//            managerList = (List<Map<String, String>>) redis.get("user:manage:list");
//            if (managerList != null) {
//                return managerList);
//            }
//            managerList = userInfoMapper.selectManager();
//            redis.set("user:manage:list", managerList, 60 * 60);
//            return managerList);
//        } finally {
//            lock.unlock();
//        }
    }

    @Override
    public UserInfo addUser(UserInfo userInfo) {
        // 以防为空值
        String unionId = StrUtil.isEmpty(userInfo.getUnionId()) ? "1" : userInfo.getUnionId();
        UserInfo user = userInfoMapper.selectByUnionId(unionId);
        if (ObjectUtil.isNull(user)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            user = userInfo;
            user.setMarkId(snowflake.nextIdStr());
            user.setCreateTime(DateUtil.date());
            userInfoMapper.insertSelective(user);
        }
        return user;
    }

    @Transient
    @Override
    public String addXUser(UserInfo userInfo) {
        // 以防为空值
        String unionId = StrUtil.isEmpty(userInfo.getUnionId()) ? "1" : userInfo.getUnionId();
        UserInfo user = userInfoMapper.selectByUnionId(unionId);
        if (ObjectUtil.isNotNull(user)) {
            userInfoMapper.deleteByPrimaryKey(user.getMarkId());
        } else {
            user = userInfo;
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            userInfo.setMarkId(snowflake.nextIdStr());
            user.setCreateTime(DateUtil.date());
        }
        user.setNickName(userInfo.getNickName());
        user.setHeaderImg(userInfo.getHeaderImg());
        user.setGender(userInfo.getGender());
        user.setCity(userInfo.getCity());
        user.setProvince(userInfo.getProvince());
        user.setCountry(userInfo.getCountry());
        user.setLanguage(userInfo.getLanguage());
        user.setXopenId(userInfo.getXopenId());
        userInfoMapper.insertSelective(user);
        return user.getMarkId();
    }

    @Override
    public void modifyUser(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        redis.del("user:manage:list");
//        sender.addMemberAccount(userInfo.getMarkId());
    }

    @Override
    public PageGrid<UserVo> pageUser(PageParam<UserVo> userPage) {
        PageMethod.startPage(userPage.getPageIndex(), userPage.getPageSize());
        PageMethod.orderBy(userPage.getSidx() + " " + userPage.getSort());
        PageInfo<UserVo> pageInfo = new PageInfo<>(userInfoMapper.selectByExampleSelective(userPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public PageGrid<UserVo> pageOutRole(PageParam<UserVo> userPage) {
        PageMethod.startPage(userPage.getPageIndex(), userPage.getPageSize());
        PageMethod.orderBy(userPage.getSidx() + " " + userPage.getSort());
        PageInfo<UserVo> pageInfo = new PageInfo<>(userInfoMapper.selectOutRoleByExampleSelective(userPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public UserInfo getUserById(String markId) {
        return userInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public UserInfo getInfoByOpenId(String openId) {
        return userInfoMapper.selectByOpenId(openId);
    }

    @Override
    public UserInfo getInfoByXopenId(String xopenId) {
        return userInfoMapper.selectByXopenId(xopenId);
    }

    @Override
    public UserInfo getInfoByUnionId(String unionId) {
        return userInfoMapper.selectByUnionId(unionId);
    }

    @Override
    public UserInfo getUserByToken(String token) {
        return userInfoMapper.selectByToken(token);
    }

    @Override
    public List<Combobox> getUserList() {
        return userInfoMapper.selectComboboxList();
    }

    @Override
    public UserInfoVo getMyInfo(String userId) {
        UserInfoVo userInfoVo = userInfoMapper.selectMyInfo(userId);
        Result<Integer> integralResult = showMemberClient.getIntegralTotalByUserId(userId);
        userInfoVo.setIntegral(integralResult.getData() == null ? 0 : integralResult.getData());
        return userInfoVo;
    }

    @Override
    public UserInfo getInfoByPhone(String phone) {
        UserInfo userInfo = userInfoMapper.selectByPhone(phone);
        if (ObjectUtil.isNull(userInfo)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
//            userInfo = ShowAssert.checkNull(userInfo, StatusCode._5000);tatus(0);
            userInfo = UserInfo.builder().markId(snowflake.nextIdStr()).createTime(DateUtil.date()).nickName("九色鹿").phone(phone).wechatStatus(0)
                    .build();
            userInfoMapper.insertSelective(userInfo);
        }
        return userInfo;
    }

    @Override
    public Boolean checkManage(String markId) {
        List<String> userIds = userInfoMapper.selectManageList();
        return userIds.contains(markId);
    }

    @Override
    public PageGrid<UserInfo> pageInStoreStaff(PageParam<StoreStaffVo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<UserInfo> list = userInfoMapper.selectInStoreByExampleSelective(base.getData());
        PageInfo<UserInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<UserInfo> pageOutStoreStaff(PageParam<StoreStaffVo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<UserInfo> list = userInfoMapper.selectOutStoreByExampleSelective(base.getData());
        PageInfo<UserInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public List<UserInfo> listOutMember() {
        return userInfoMapper.selectOutMember();
    }

    @Override
    public List<String> listWopenIdByUserId(List<String> userIds) {
        return userInfoMapper.selectWopenIdsByUserId(userIds);
    }

    @Override
    public List<UserInfo> selectFocusUser() {
        return userInfoMapper.selectFocusUser();
    }
}
