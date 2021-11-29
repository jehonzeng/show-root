package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.LogInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource
    private Redis redis;

    @Override
    public LogInfo createLog(LogInfo logInfo) {
        // 有效存储时间为7天
        long expired = 7L * 24 * 60 * 60;
        String key = "base:" + DateUtil.today() + ":log";
        redis.lSet(key, logInfo, expired);
        return logInfo;
    }

    @Override
    public PageGrid<Object> getLoginLogPage(PageParam<LogInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        LogInfo logInfo = base.getData();
        String time = ObjectUtil.isNull(logInfo.getAddTime()) ? DateUtil.today()
                : DateUtil.format(logInfo.getAddTime(), "yyyy-MM-dd");
        String key = "base:" + time + ":log";
        long start = (base.getPageIndex() - 1) * base.getPageSize();
        long end = start + base.getPageSize() - 1;
        List<Object> list = redis.lGet(key, start, end);
        PageInfo<Object> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }
}
