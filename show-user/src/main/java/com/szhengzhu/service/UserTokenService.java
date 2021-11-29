package com.szhengzhu.service;

import com.szhengzhu.bean.user.UserToken;

/**
 * @author Jehon Zeng
 */
public interface UserTokenService {
    
    /**
     * 创建用户token
     * 
     * @date 2019年4月24日 下午4:14:09
     * @param userId
     * @return
     */
    UserToken addUserToken(String userId);

    /**
     * 通过token获取值
     * 
     * @date 2019年4月24日 下午4:02:49
     * @param token
     * @return
     */
    UserToken getByToken(String token);
}
