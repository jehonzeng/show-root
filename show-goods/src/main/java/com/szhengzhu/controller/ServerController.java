package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.ServeService;

/**
 * 商品服务支持信息管理
 * 
 * @author Administrator
 * @date 2019年4月1日
 */
@RestController
@RequestMapping(value = "serves")
public class ServerController {

    @Resource
    private ServeService serveService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addServer(@RequestBody ServerSupport base) {
        return serveService.saveServer(base);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public Result<?> modifyServer(@RequestBody ServerSupport base) {
        return serveService.modifyServer(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<?>> serverPage(@RequestBody PageParam<ServerSupport> base) {
        return serveService.getPage(base);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> listServer() {
        return serveService.listServer();
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getServeById(@PathVariable("markId") String markId){
        return serveService.getServeById(markId);
    }
    

}