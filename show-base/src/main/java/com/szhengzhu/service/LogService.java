package com.szhengzhu.service;

import com.szhengzhu.bean.base.LogInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface LogService {

    /**
     * 创建日志
     *
     * @param log
     * @return
     */
    LogInfo createLog(LogInfo log);

    /**
     * 获取登录日志
     *
     * @param base
     * @return
     */
    PageGrid<Object> getLoginLogPage(PageParam<LogInfo> base);

}
