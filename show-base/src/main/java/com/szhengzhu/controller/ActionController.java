package com.szhengzhu.controller;

import com.szhengzhu.bean.base.ActionInfo;
import com.szhengzhu.bean.base.ActionItem;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ActionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping("action")
public class ActionController {

    @Resource
    private ActionService actionService;

    @GetMapping(value = "/item/list")
    public List<ActionItem> listItemByCode(@RequestParam("code") @NotBlank String code) {
        return actionService.listItemByCode(code);
    }

    @PostMapping(value = "/page")
    public PageGrid<ActionInfo> pageAction(@RequestBody PageParam<ActionInfo> page) {
        return actionService.pageAction(page);
    }

    @PostMapping(value = "/add")
    public void addAction(@RequestBody @Validated ActionInfo actionInfo) {
        actionService.addAction(actionInfo);
    }

    @PatchMapping(value = "/modify")
    public void modifyAction(@RequestBody @Validated ActionInfo actionInfo) {
        actionService.modifyAction(actionInfo);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<ActionItem> pageItem(@RequestBody PageParam<ActionItem> page) {
        return actionService.pageItem(page);
    }

    @GetMapping(value = "/item/{markId}")
    public ActionItem getItemInfo(@PathVariable("markId") @NotBlank String markId) {
        return actionService.getItemInfo(markId);
    }

    @PostMapping(value = "/item/add")
    public void addItem(@RequestBody @Validated ActionItem item) {
        actionService.addItem(item);
    }

    @PatchMapping(value = "/item/modify")
    public void modifyItem(@RequestBody @Validated ActionItem item) {
        actionService.modifyItem(item);
    }

    @DeleteMapping(value = "/item/{markId}")
    public void deleteItem(@PathVariable("markId") @NotBlank String markId) {
        actionService.deleteItem(markId);
    }
}
