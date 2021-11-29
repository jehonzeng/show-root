package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author terry shi
 */
@Api(tags = {"后台首页展示信息：IndexController"})
@RestController
@RequestMapping(value = "/v1/index")
public class IndexController {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @Resource
    private ShowOrderClient showOrderClient;
    
    @ApiOperation(value = "后台管理首页信息展示",notes="后台管理首页信息展示")
    @GetMapping(value = "/info")
    public Result<?> getIndexInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("feedback", showBaseClient.getIndexFeedbackCount().getData());
        map.put("orders",showOrderClient.getIndexStatusCount().getData());
        return new Result<>(map);
    }
}
