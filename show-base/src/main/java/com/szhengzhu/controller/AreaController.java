package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.AreaService;

@RestController
@RequestMapping(value = "areas")
public class AreaController {

    @Resource
    private AreaService areaService;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<AreaInfo>> listAll() {
        return areaService.listArea();
    }
    
    @RequestMapping(value = "/provinces", method = RequestMethod.GET)
    public Result<List<Combobox>> listProvince(){
        return areaService.listProvince();
    }
    
    @RequestMapping(value = "/listCityAndArea",method=RequestMethod.GET)
    public Result<List<AreaVo>> listCityAndArea(@RequestParam("province") String province){
        return areaService.listCityAndArea(province);
    }
}
