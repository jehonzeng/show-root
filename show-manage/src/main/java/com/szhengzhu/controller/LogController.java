package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.bean.base.LogInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Terry
 */
@Api(tags = { "后台日志管理:LogController" })
@RestController
@RequestMapping(value = "/v1/logs")
public class LogController {

    @Resource
    private ShowBaseClient showBaseClient;

    @PostMapping(value="/login/page")
    public Result<PageGrid<Object>> getLoginLogPage(@RequestBody PageParam<LogInfo> base){
        return showBaseClient.getLoginLogPage(base);
    }
}
