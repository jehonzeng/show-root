package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.xwechat.vo.StoreModel;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = { "门店：StoreController" })
@RestController
@RequestMapping("/store")
public class StoreController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取某城市门店 ")
    @GetMapping(value = "/list")
    public Result<List<StoreModel>> listStore(@RequestParam("city") @NotBlank String city) {
        return showOrderingClient.listLjsStore(city);
    }
}
