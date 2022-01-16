package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.CounselInfo;
import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.bean.vo.NewsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Api(tags = {"官网：WebsiteController"})
@RestController
@RequestMapping(value = "web")
public class WebsiteController {

    @Resource
    private ShowBaseClient showBaseClient;

    @PostMapping(value = "/contactInfo")
    public Result<CounselInfo> addCounsel(@RequestBody CounselInfo base) {
        return showBaseClient.addCounsel(base);
    }

    @GetMapping(value = "/goodsList")
    public Result<List<ProductInfo>> getGoodsList() {
        return showBaseClient.getGoodsList();
    }

    @GetMapping(value = "/newsList")
    public Result<PageGrid<ProductInfo>> getNewList(@RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize) {
        return showBaseClient.getNewList(pageNo, pageSize);
    }

    @GetMapping(value = "/newsInfo")
    public Result<NewsVo> getNewsInfo(@RequestParam("markId") String markId) {
        return showBaseClient.getNewsInfo(markId);
    }

}
