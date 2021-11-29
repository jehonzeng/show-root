package com.szhengzhu.controller;

import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.wechat.vo.NavBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.NavService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/navs")
public class NavController {

    @Resource
    private NavService navService;

    @PostMapping(value = "/add")
    public NavInfo add(@RequestBody @Validated NavInfo base) {
        return navService.save(base);
    }

    @PatchMapping(value = "/modify")
    public NavInfo modify(@RequestBody @Validated NavInfo base) {
        return navService.update(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<NavInfo> page(@RequestBody PageParam<NavInfo> base) {
        return navService.page(base);
    }

    @PostMapping(value = "/item")
    public NavItem addItem(@RequestBody @Validated NavItem base) {
        return navService.saveItem(base);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<NavItem> itemPage(@RequestBody PageParam<NavItem> base) {
        return navService.getItemPage(base);
    }

    @PatchMapping(value = "/item")
    public NavItem modifyItem(@RequestBody @Validated NavItem base) {
        return navService.modifyItem(base);
    }

    @GetMapping(value = "/item/{markId}")
    public NavItem getItem(@PathVariable("markId") @NotBlank String markId) {
        return navService.getItemById(markId);
    }

    @GetMapping(value = "/fore/list")
    public List<NavBase> listNavAndItem() {
        return navService.listNavAndItem();
    }

    @DeleteMapping(value = "/item/{markId}")
    public void deleteItem(@PathVariable("markId") @NotBlank String markId) {
        navService.deleteItem(markId);
    }
}
