package com.szhengzhu.service;

import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.core.Result;

public interface SysService {

    /**
     * 根据名称查找配置值
     * 
     * @date 2019年4月23日 下午5:49:09
     * @param name
     * @return
     */
    Result<String> getByName(String name);
    
    /**
     * 修改信息
     * 
     * @date 2019年4月23日 下午5:48:58
     * @param sysInfo
     * @return
     */
    Result<?> modifySys(SysInfo sysInfo);
}
