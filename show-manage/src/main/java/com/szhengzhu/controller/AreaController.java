package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Terry
 */
@Api(tags = { "区域管理:AreaController" })
@RestController
@RequestMapping("/v1/areas")
public class AreaController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "获取地址三级列表", notes = "获取地址三级列表")
    @GetMapping(value = "/list")
    public Result<List<AreaInfo>> listAll() {
        return showBaseClient.listArea();
    }
}
