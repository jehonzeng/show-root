package com.szhengzhu.service;

import com.szhengzhu.bean.base.SysInfo;

public interface SysService {

    /**
     * 根据名称查找配置值
     *
     * @date 2019年4月23日 下午5:49:09
     * @param name
     * @return
     */
    String getByName(String name);

    /**
     * 修改信息
     *
     * @date 2019年4月23日 下午5:48:58
     * @param sysInfo
     * @return
     */
    void modifySys(SysInfo sysInfo);
}
