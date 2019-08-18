package com.szhengzhu.client;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;

@FeignClient("show-user")
public interface ShowUserClient {

    @RequestMapping(value = "/users/info/openid", method = RequestMethod.GET)
    Result<UserInfo> getUserByOpenId(@RequestParam("openId") String openId);
    
    @RequestMapping(value = "/users/info/token", method = RequestMethod.GET)
    Result<UserInfo> getUserByToken(@RequestParam("token") String token);
    
    @RequestMapping(value = "/users/token/add", method = RequestMethod.POST)
    Result<UserToken> addUserToken(@RequestParam("userId") String userId);
    
    @RequestMapping(value = "/users/token/{token}", method = RequestMethod.GET)
    Result<UserToken> getUserToken(@PathVariable("token") String token);
    
    @RequestMapping(value = "/users/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<UserInfo> addUser(@RequestBody UserInfo userInfo);
    
    @RequestMapping(value = "/users/my/info", method = RequestMethod.GET)
    Result<Map<String, Object>> getMyInfo(@RequestParam("userId") String userId);
    
    @RequestMapping(value = "/users/info/phone", method = RequestMethod.GET)
    Result<UserInfo> getUserByPhone(@RequestParam("phone") String phone);
    
    @RequestMapping(value = "/users/integral", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addIntegral(@RequestBody UserIntegral integral);
}
