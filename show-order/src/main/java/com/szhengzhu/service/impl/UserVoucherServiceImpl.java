package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.wechat.vo.VoucherBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.UserVoucherMapper;
import com.szhengzhu.service.UserVoucherService;
import com.szhengzhu.util.StringUtils;

@Service("userVoucherService")
public class UserVoucherServiceImpl implements UserVoucherService {

    @Resource
    private UserVoucherMapper userVoucherMapper;
    
    @Override
    public Result<List<VoucherBase>> listByUser(String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        List<VoucherBase> vouchers = userVoucherMapper.selectByUser(userId);
        return new Result<>(vouchers);
    }
}
