package com.szhengzhu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.UserInfoMapper;
import com.szhengzhu.service.UserService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public Result<UserInfo> getManager(String phone) {
        UserInfo userInfo = userInfoMapper.selectByPhone(phone);
        if (userInfo == null)
            return new Result<>(StatusCode._5000);
        return new Result<>(userInfo);
    }
    
    @Override
    public Result<UserInfo> addUser(UserInfo userInfo) {
        if (userInfo == null || StringUtils.isEmpty(userInfo.getNickName())) 
            return new Result<>(StatusCode._4004);
        userInfo.setMarkId(IdGenerator.getInstance().nexId());
        userInfo.setCreateTime(TimeUtils.today());
        userInfoMapper.insertSelective(userInfo);
        return new Result<>(userInfo);
    }

    @Override
    public Result<PageGrid<UserVo>> pageUser(PageParam<UserVo> userPage) {
        PageHelper.startPage(userPage.getPageIndex(), userPage.getPageSize());
        PageHelper.orderBy(userPage.getSidx() + " " + userPage.getSort());
        PageInfo<UserVo> pageInfo = new PageInfo<>(userInfoMapper.selectByExampleSelective(userPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }
    
    @Override
    public Result<PageGrid<UserVo>> pageUserNotIn(PageParam<UserVo> userPage) {
        PageHelper.startPage(userPage.getPageIndex(), userPage.getPageSize());
        PageHelper.orderBy(userPage.getSidx() + " " + userPage.getSort());
        PageInfo<UserVo> pageInfo = new PageInfo<>(userInfoMapper.selectNotInByExampleSelective(userPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<UserInfo> getUserById(String markId) {
        if (StringUtils.isEmpty(markId))
            return new Result<>(StatusCode._4004);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(userInfo);
    }

    @Override
    public Result<UserInfo> getUserByOpenId(String openId) {
        if (StringUtils.isEmpty(openId))
            return new Result<>(StatusCode._4004);
        UserInfo userInfo = userInfoMapper.selectByOpenId(openId);
        return new Result<>(userInfo);
    }

    @Override
    public Result<UserInfo> getUserByToken(String token) {
        if (StringUtils.isEmpty(token))
            return new Result<>(StatusCode._4004);
        UserInfo userInfo = userInfoMapper.selectByToken(token);
        return new Result<>(userInfo);
    }

    @Override
    public Result<List<Combobox>> getUserList() {
        List<Combobox> list= userInfoMapper.selectComboboxList();
        return new Result<>(list);
    }

    @Override
    public Result<Map<String, Object>> getMyInfo(String userId) {
        Map<String, Object> base = userInfoMapper.selectMyInfo(userId);
        return new Result<>(base);
    }

    @Override
    public Result<UserInfo> getByPhone(String phone) {
        UserInfo userInfo = userInfoMapper.selectByPhone(phone);
        if (userInfo == null) {
            userInfo = new UserInfo();
            IdGenerator generator = IdGenerator.getInstance();
            userInfo.setMarkId(generator.nexId());
            userInfo.setCreateTime(TimeUtils.today());
            userInfo.setNickName("九色鹿");
            userInfo.setPhone(phone);
            userInfo.setWechatStatus(0);
            userInfoMapper.insertSelective(userInfo);
        }
        return new Result<>(userInfo);
    }
}
