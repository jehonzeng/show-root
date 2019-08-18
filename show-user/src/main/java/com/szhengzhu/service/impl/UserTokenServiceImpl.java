package com.szhengzhu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.Result;
import com.szhengzhu.mapper.UserTokenMapper;
import com.szhengzhu.service.UserTokenService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {
    
    @Resource
    private UserTokenMapper userTokenMapper;
    
    @Override
    public Result<UserToken> addUserToken(String userId) {
        UserToken token = userTokenMapper.selectByUser(userId);
        if (token != null) {
            token.setRefreshTime(TimeUtils.today());
            userTokenMapper.updateByPrimaryKeySelective(token);
            return new Result<>(token);
        }
        UserToken userToken = new UserToken();
        userToken.setMarkId(IdGenerator.getInstance().nexId());
        userToken.setUserId(userId);
        userToken.setRefreshTime(TimeUtils.today());
        userToken.setToken(StringUtils.getRandomId());
        userTokenMapper.insert(userToken);
        return new Result<>(userToken);
    }

    @Override
    public Result<UserToken> getByToken(String token) {
        UserToken userToken = userTokenMapper.selectByToken(token);
        return new Result<>(userToken);
    }

    @Override
    public Result<?> refreshToken(String token) {
        userTokenMapper.refreshByToken(token);
        return new Result<>();
    }
}
