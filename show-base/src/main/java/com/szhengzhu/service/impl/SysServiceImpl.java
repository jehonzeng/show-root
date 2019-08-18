package com.szhengzhu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.SysInfoMapper;
import com.szhengzhu.service.SysService;
import com.szhengzhu.util.StringUtils;

@Service("sysService")
public class SysServiceImpl implements SysService {

    @Resource
    private SysInfoMapper sysInfoMapper;

    @Override
    public Result<String> getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new Result<>(StatusCode._4004);
        }
        String dataJson = sysInfoMapper.selectByName(name);
        return new Result<>(dataJson);
    }

    @Override
    public Result<?> modifySys(SysInfo sysInfo) {
        if (sysInfo == null || StringUtils.isEmpty(sysInfo.getName()) || StringUtils.isEmpty(sysInfo.getDataJson())) {
            return new Result<>(StatusCode._4004);
        }
        int count = sysInfoMapper.countByName(sysInfo.getName());
        if (count > 0) 
            sysInfoMapper.updateByPrimaryKey(sysInfo);
         else 
            sysInfoMapper.insert(sysInfo);
        return new Result<>();
    }
    
    
}
