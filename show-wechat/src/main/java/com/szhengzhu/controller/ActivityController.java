package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.SeckillDetail;
import com.szhengzhu.bean.wechat.vo.TeambuyDetail;
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
 * @author Jehon Zeng
 */
@Validated
@Api(tags = {"活动专题：ActivityController"})
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取秒杀分页列表", notes = "获取秒杀分页列表")
    @PostMapping(value = "/seckill/page")
    public Result<PageGrid<Map<String, Object>>> pageSeckill(@RequestBody PageParam<String> pageParam) {
        return showActivityClient.pageForeSeckill(pageParam);
    }

    @ApiOperation(value = "获取秒杀详情", notes = "获取秒杀详细信息")
    @GetMapping(value = "/seckill/detail/{markId}")
    public Result<SeckillDetail> getSeckillDetail(HttpServletRequest request, @PathVariable("markId") String markId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showActivityClient.getSeckillDetail(markId, userToken.getUserId());
    }

    @ApiOperation(value = "获取秒杀商品库存", notes = "获取秒杀商品库存")
    @GetMapping(value = "/seckill/stock")
    public Result<Map<String, Object>> getSeckillStock(@RequestParam("markId") @NotBlank String markId,
                                                       @RequestParam(value = "addressId", required = false) String addressId) {
        return showActivityClient.getSeckillStock(markId, addressId);
    }

    @ApiOperation(value = "获取团购分页列表", notes = "获取团购分页列表")
    @PostMapping(value = "/teambuy/page")
    public Result<PageGrid<Map<String, Object>>> pageTeambuy(@RequestBody PageParam<String> pageParam) {
        return showActivityClient.pageForeTeambuy(pageParam);
    }

    @ApiOperation(value = "获取团购详情", notes = "获取团购详细信息")
    @GetMapping(value = "/teambuy/detail/{markId}")
    public Result<TeambuyDetail> getTeambuyDetail(HttpServletRequest request, @PathVariable("markId") @NotBlank String markId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showActivityClient.getTeambuyDetail(markId, userToken.getUserId());
    }

    @ApiOperation(value = "获取团购商品库存", notes = "获取团购商品库存")
    @GetMapping(value = "/teambuy/stock")
    public Result<Map<String, Object>> getTeambuyStock(@RequestParam("markId") @NotBlank String markId,
                                                       @RequestParam(value = "addressId", required = false) String addressId) {
        return showActivityClient.getTeambuyStock(markId, addressId);
    }
}
