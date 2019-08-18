package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.UserAddressService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping("/address")
public class UserAddressController {
    
    @Resource
    private UserAddressService userAddressService;


    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<UserAddress>> pageAddress(@RequestBody PageParam<UserAddress> addressPage) {
        if (addressPage == null)
            return new Result<>(StatusCode._4004);
        return userAddressService.pageAddress(addressPage);
    }
    
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public Result<List<UserAddress>> listByUser(@PathVariable("userId") String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        return userAddressService.listByUser(userId);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody UserAddress address) {
        if (address == null || StringUtils.isEmpty(address.getUserName()) || StringUtils.isEmpty(address.getPhone())
                || StringUtils.isEmpty(address.getArea()) || StringUtils.isEmpty(address.getCity())
                || StringUtils.isEmpty(address.getUserAddress()) || StringUtils.isEmpty(address.getUserId()))
            return new Result<>(StatusCode._4004);
        return userAddressService.add(address);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modify(@RequestBody UserAddress address) {
        if (address == null || StringUtils.isEmpty(address.getMarkId()) || StringUtils.isEmpty(address.getUserId()))
            return new Result<>(StatusCode._4004);
        return userAddressService.modify(address);
    }
    
    @RequestMapping(value = "/def/{userId}", method = RequestMethod.GET)
    public Result<UserAddress> getDefByUser(@PathVariable("userId") String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        return userAddressService.getDefByUser(userId);
    }
    
    @RequestMapping(value = "/{addressId}", method = RequestMethod.GET)
    public Result<UserAddress> getInfo(@PathVariable("addressId") String addressId) {
        if (StringUtils.isEmpty(addressId))
            return new Result<>(StatusCode._4004);
        return userAddressService.getInfo(addressId);
    }
}
