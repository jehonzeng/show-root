package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.IndentRemark;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Api(tags = "订单评论：IndentRemarkController")
@Validated
@RestController
@RequestMapping("/v1/remark")
public class IndentRemarkController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "通过主键查询订单评论")
    @GetMapping(value = "/{markId}")
    public Result<IndentRemark> selectOne(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.selectOne(markId);
    }

    @ApiOperation(value = "查询订单评论数据并分页(查询评论列表）")
    @PostMapping(value = "/query")
    public Result<PageGrid<IndentRemark>> query(@RequestBody PageParam<IndentRemark> param) {
        return showOrderingClient.query(param);
    }
}
