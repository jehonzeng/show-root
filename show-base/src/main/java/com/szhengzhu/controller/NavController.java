package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.wechat.vo.NavVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.NavService;

@RestController
@RequestMapping(value = "/navs")
public class NavController {

    @Resource
    private NavService navService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<NavInfo> add(@RequestBody NavInfo base) {
        return navService.save(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<NavInfo> modify(@RequestBody NavInfo base) {
        return navService.update(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<NavInfo>> page(@RequestBody PageParam<NavInfo> base) {
        return navService.page(base);
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public Result<NavItem> addItem(@RequestBody NavItem base) {
        return navService.saveItem(base);
    }

    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<NavItem>> itemPage(@RequestBody PageParam<NavItem> base) {
        return navService.getItemPage(base);
    }

    @RequestMapping(value = "/item", method = RequestMethod.PATCH)
    public Result<NavItem> modifyItem(@RequestBody NavItem base) {
        return navService.modifyItem(base);
    }

    @RequestMapping(value = "/item/{markId}", method = RequestMethod.GET)
    public Result<NavItem> getItem(@PathVariable("markId") String markId) {
        return navService.getItemById(markId);
    }
    
    @RequestMapping(value = "/fore/list", method = RequestMethod.GET)
    public Result<List<NavVo>> listNavAndItem() {
        return navService.listNavAndItem();
    }
    
    @RequestMapping(value = "/item/{markId}", method = RequestMethod.DELETE)
    public Result<?> deleteItem(@PathVariable("markId") String markId) {
        return navService.deleteItem(markId);
    }
}
