package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.CookCertifiedService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping("/cooks")
public class CookCertifiedController {

    @Resource
    private CookCertifiedService cookCertifiedService;

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<CookCertified> modify(@RequestBody CookCertified cookCertified) {
        if (cookCertified == null || StringUtils.isEmpty(cookCertified.getMarkId()))
            return new Result<>(StatusCode._4004);
        return cookCertifiedService.modify(cookCertified);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<CookCertified>> page(@RequestBody PageParam<CookCertified> cookPage) {
        if (cookPage == null)
            return new Result<>(StatusCode._4004);
        return cookCertifiedService.page(cookPage);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<CookCertified> add(@RequestBody CookCertified cookCertified) {
        if (cookCertified == null || StringUtils.isEmpty(cookCertified.getShortName()))
            return new Result<>(StatusCode._4004);
        return cookCertifiedService.add(cookCertified);
    }

    @RequestMapping(value = "/sale/rank", method = RequestMethod.GET)
    public Result<List<Cooker>> listCookerRank(@RequestParam(value = "userId", required = false) String userId) {
        return cookCertifiedService.listCookerRank(userId);
    }

    @RequestMapping(value = "/sale/rank/goods/page", method = RequestMethod.POST)
    public Result<PageGrid<Cooker>> pageCookerRank(@RequestBody PageParam<String> cookerPage) {
        return cookCertifiedService.pageCookerRank(cookerPage);
    }

    @RequestMapping(value = "/detail/{cookerId}", method = RequestMethod.GET)
    public Result<?> getCookerDetail(@RequestParam("cookerId") String cookerId,
            @RequestParam(value = "userId", required = false) String userId) {
        if (StringUtils.isEmpty(cookerId))
            return new Result<>(StatusCode._4004);
        return cookCertifiedService.getCookerDetail(cookerId, userId);
    }

    @RequestMapping(value = "/follow/or", method = RequestMethod.PATCH)
    public Result<?> followOr(@RequestBody CookFollow cookFollow) {
        if (cookFollow == null || StringUtils.isEmpty(cookFollow.getCookId())
                || StringUtils.isEmpty(cookFollow.getUserId()) || cookFollow.getFollow() == null)
            return new Result<>(StatusCode._4004);
        return cookCertifiedService.followOr(cookFollow);
    }
}
