package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.GoodsJudgeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/judges")
public class GoodsJudgeController {

    @Resource
    private GoodsJudgeService goodsJudgeService;

    @PatchMapping(value = "/modify")
    public GoodsJudge modifyGoodsJudes(@RequestBody GoodsJudge base) {
        return goodsJudgeService.modifyJudgeInfo(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<GoodsJudgeVo> page(@RequestBody PageParam<GoodsJudge> base) {
        return goodsJudgeService.getPage(base);
    }
    
    @GetMapping(value = "/{goodsId}")
    public List<GoodsJudge> judgeInfo(@PathVariable("goodsId") @NotBlank String goodsId){
        return goodsJudgeService.getJudgeInfosByGoodsId(goodsId);
    }
    
    @PostMapping(value = "/add")
    public GoodsJudge add(@RequestBody @Validated GoodsJudge base) {
        return goodsJudgeService.addJudgeInfo(base);
    }
    
    @GetMapping(value = "/fore/list")
    public List<JudgeBase> list(@RequestParam("goodsId") @NotBlank String goodsId, @RequestParam(value = "userId", required = false) String userId) {
        return goodsJudgeService.list(goodsId, userId);
    }

}
