package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.vo.UserBase;

@FeignClient("show-user")
public interface ShowUserClient {
    
    @RequestMapping(value = "/roles/usersByRole", method = RequestMethod.GET)
    List<UserBase> getUsersByRole(@RequestParam("roleId") String roleId);
}
