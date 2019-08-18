package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.wechat.vo.VoucherBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.UserVoucherService;

@RestController
@RequestMapping(value = "/vouchers")
public class UserVoucherController {

    @Resource
    private UserVoucherService userVoucherService;
    
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public Result<List<VoucherBase>> listByUser(@PathVariable("userId") String userId) {
        return userVoucherService.listByUser(userId);
    }
}
