package com.szhengzhu.client;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient( name = "show-activity")
public interface ShowActivityClient {

    @RequestMapping(value = "/teambuy/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<TeambuyInfo>> pageTeambuy(@RequestBody PageParam<TeambuyInfo> teambuyPage);
    
    @RequestMapping(value = "/teambuy/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<TeambuyInfo> addTeambuy(@RequestBody TeambuyInfo teambuyInfo);
    
    @RequestMapping(value = "/teambuy/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<TeambuyInfo> modifyTeambuy(@RequestBody TeambuyInfo teambuyInfo);
    
    @RequestMapping(value = "/seckill/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SeckillInfo>> pageSeckill(@RequestBody PageParam<SeckillInfo> seckillPage);
    
    @RequestMapping(value = "/seckill/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<SeckillInfo> addSeckill(@RequestBody SeckillInfo seckillInfo);
    
    @RequestMapping(value = "/seckill/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<SeckillInfo> modifySeckill(@RequestBody SeckillInfo seckillInfo);
    
}
