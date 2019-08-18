package com.szhengzhu.service;

import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.Result;

public interface UserTokenService {
    
    /**
     * 创建用户token
     * 
     * @date 2019年4月24日 下午4:14:09
     * @param userId
     * @return
     */
    Result<UserToken> addUserToken(String userId);

    /**
     * 通过token获取值
     * 
     * @date 2019年4月24日 下午4:02:49
     * @param token
     * @return
     */
    Result<UserToken> getByToken(String token);
    
    /**
     * 通过token刷新时间
     * 
     * @date 2019年4月24日 下午4:04:07
     * @param token
     * @return
     */
    Result<?> refreshToken(String token);
}
