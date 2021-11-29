package com.szhengzhu.controller;

import com.szhengzhu.bean.base.LogInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.LogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "logs")
public class LogController {

    @Resource
    private LogService logService;

    @PostMapping(value = "/add")
    public LogInfo createLog(@RequestBody LogInfo base) {
        return logService.createLog(base);
    }

    @PostMapping(value = "/login/page")
    public PageGrid<Object> getLoginLogPage(@RequestBody PageParam<LogInfo> base) {
        return logService.getLoginLogPage(base);
    }

}
