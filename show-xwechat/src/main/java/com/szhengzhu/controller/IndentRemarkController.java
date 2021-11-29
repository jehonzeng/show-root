package com.szhengzhu.controller;

import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.ordering.IndentRemark;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = "订单评论：IndentRemarkController")
@Validated
@RestController
@RequestMapping("/v1/indent/remark")
public class IndentRemarkController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "通过主键查询订单评论")
    @GetMapping(value = "/{markId}")
    public Result<IndentRemark> selectOne(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.selectOne(markId);
    }

    @ApiOperation(value = "分页查询订单评论数据")
    @PostMapping(value = "/query")
    public Result<PageGrid<IndentRemark>> query(@RequestBody PageParam<IndentRemark> param) {
        return showOrderingClient.query(param);
    }

    @ApiOperation(value = "添加订单评论数据")
    @PostMapping(value = "")
    public Result<Map<String, Object>> add(HttpServletRequest request, @RequestBody IndentRemark indentRemark) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        indentRemark.setUserId(userToken.getUserId());
        return showOrderingClient.addIndentRemark(indentRemark);
    }
}
