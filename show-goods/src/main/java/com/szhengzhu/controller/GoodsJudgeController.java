package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsJudgeService;

@RestController
@RequestMapping(value = "/judges")
public class GoodsJudgeController {

    @Resource
    private GoodsJudgeService goodsJudgeService;

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modifyGoodsJudes(@RequestBody GoodsJudge base) {
        return goodsJudgeService.modifyJudgeInfo(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsJudgeVo>> page(@RequestBody PageParam<GoodsJudge> base) {
        return goodsJudgeService.getPage(base);
    }
    
    @RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
    public Result<?> judgeInfo(@PathVariable("goodsId")String goodsId){
        return goodsJudgeService.getJudgeInfosByGoodsId(goodsId);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<GoodsJudge> add(@RequestBody GoodsJudge base) {
        return goodsJudgeService.addJudgeInfo(base);
    }
    
    @RequestMapping(value = "/fore/list", method = RequestMethod.GET)
    public Result<List<JudgeBase>> list(@RequestParam("goodsId") String goodsId, @RequestParam(value = "userId", required = false) String userId) {
        return goodsJudgeService.list(goodsId, userId);
    }

}
