package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.core.Result;

@FeignClient("show-base")
public interface ShowBaseCilent {
    
    @RequestMapping(value = "/areas/listCityAndArea",method=RequestMethod.GET)
    Result<List<AreaVo>> listCityAndArea(@RequestParam("province") String province);
}
