package com.szhengzhu.controller;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.service.AreaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "areas")
public class AreaController {

    @Resource
    private AreaService areaService;

    @GetMapping(value = "/list")
    public List<AreaInfo> listAll() {
        return areaService.listArea();
    }

    @GetMapping(value = "/list/{version}")
    public Map<String, Object> listAll(@PathVariable("version") int version) {
        return areaService.listArea(version);
    }

    @GetMapping(value = "/provinces")
    public List<Combobox> listProvince() {
        return areaService.listProvince();
    }

    @GetMapping(value = "/listCityAndArea")
    public List<AreaVo> listCityAndArea(@RequestParam("province") @NotBlank String province) {
        return areaService.listCityAndArea(province);
    }
}
