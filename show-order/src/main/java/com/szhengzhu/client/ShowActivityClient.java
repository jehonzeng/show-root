package com.szhengzhu.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.core.Result;

@FeignClient("show-activity")
public interface ShowActivityClient {

    @RequestMapping(value = "/teambuy/info", method = RequestMethod.GET)
    Result<TeambuyInfo> getTeambuyInfo(@RequestParam("markId") String markId, @RequestParam("specIds") String specIds);
    
    @RequestMapping(value = "/seckill/{markId}", method = RequestMethod.GET)
    Result<SeckillInfo> getSeckillInfo(@PathVariable("markId") String markId);
}
