package com.szhengzhu.service.impl;

import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.mapper.SysInfoMapper;
import com.szhengzhu.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service("sysService")
public class SysServiceImpl implements SysService {

    @Resource
    private SysInfoMapper sysInfoMapper;

    @Override
    public String getByName(String name) {
        return sysInfoMapper.selectByName(name);
    }

    @Override
    public void modifySys(SysInfo sysInfo) {
        int count = sysInfoMapper.countByName(sysInfo.getName());
        if (count > 0) {
            sysInfoMapper.updateByPrimaryKey(sysInfo);
        } else {
            sysInfoMapper.insert(sysInfo);
        }
    }
}
