package com.szhengzhu.controller;

import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.UserAddressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户地址类
 *
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/address")
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;

    @PostMapping(value = "/page")
    public PageGrid<UserAddress> pageAddress(@RequestBody PageParam<UserAddress> addressPage) {
        return userAddressService.pageAddress(addressPage);
    }

    @GetMapping(value = "/list/{userId}")
    public List<UserAddress> listByUser(@PathVariable("userId") @NotBlank String userId) {
        return userAddressService.listByUser(userId);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated UserAddress address) {
        userAddressService.add(address);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated UserAddress address) {
        userAddressService.modify(address);
    }

    @GetMapping(value = "/def/{userId}")
    public UserAddress getDefByUser(@PathVariable("userId") @NotBlank String userId) {
        return userAddressService.getDefByUser(userId);
    }

    @GetMapping(value = "/{addressId}")
    public UserAddress getInfo(@PathVariable("addressId") @NotBlank String addressId) {
        return userAddressService.getInfo(addressId);
    }
}
