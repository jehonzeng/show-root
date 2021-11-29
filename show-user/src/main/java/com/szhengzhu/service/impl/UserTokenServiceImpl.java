package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.mapper.UserTokenMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.UserTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jehon Zeng
 */
@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private UserTokenMapper userTokenMapper;

    @Resource
    private Redis redis;

    @Override
    public UserToken addUserToken(String userId) {
        UserToken token = userTokenMapper.selectByUser(userId);
        if (ObjectUtil.isNotNull(token)) {
            redis.del("user:user:token:" + token.getToken());
            token.setRefreshTime(DateUtil.date());
            userTokenMapper.updateByPrimaryKeySelective(token);
            return token;
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        token = UserToken.builder().markId(snowflake.nextIdStr()).userId(userId)
                .refreshTime(DateUtil.date()).token(IdUtil.simpleUUID())
                .build();
        userTokenMapper.insert(token);
        return token;
    }

    @Override
    public UserToken getByToken(String token) {
        String cacheKey = "user:user:token:" + token;
        Object obj = redis.get(cacheKey);
        if (ObjectUtil.isNotNull(obj)) {
            UserToken userToken = JSON.parseObject(JSON.toJSONString(obj), UserToken.class);
            return userToken;
        }
        UserToken userToken = userTokenMapper.selectByToken(token);
        redis.set(cacheKey, userToken, 2L * 60 * 60);
        return userToken;
    }
}
