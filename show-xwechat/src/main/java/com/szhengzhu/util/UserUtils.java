package com.szhengzhu.util;

import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
public class UserUtils {

    public static UserInfo getUserInfoByToken(HttpServletRequest request, ShowUserClient showUserClient) {
        String token = request.getHeader("Show-Token");
        ShowAssert.checkStrEmpty(token, StatusCode._4005);
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        UserInfo userInfo = userResult.getData();
        return  userInfo;
    }

    public static UserToken getUserTokenByToken(HttpServletRequest request, ShowUserClient showUserClient) {
        String token = request.getHeader("Show-Token");
        ShowAssert.checkStrEmpty(token, StatusCode._4005);
        Result<UserToken> tokenResult = showUserClient.getUserToken(token);
        UserToken userToken = tokenResult.getData();
        return userToken;
    }
}
