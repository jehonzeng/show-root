package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.AccessoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "accessorys")
public class AccessoryController {
    
    @Resource
    private AccessoryService accessoryService;
    
    /**
     * 添加附属品
     * @param base
     * @return
     */
    @PostMapping(value = "/add")
    public AccessoryInfo save(@RequestBody @Validated AccessoryInfo base) {
        return accessoryService.addAccessory(base);
    }

    /**
     * 获取附属品列表
     * @param base
     * @return
     */
    @PostMapping(value = "/page")
    public PageGrid<AccessoryInfo> page(@RequestBody PageParam<AccessoryInfo> base) {
        return accessoryService.getAccessoryPage(base);
    }

    /**
     * 编辑附属品
     * @param base
     * @return
     */
    @PatchMapping(value = "/modify")
    public AccessoryInfo edit(@RequestBody @Validated AccessoryInfo base) {
        return accessoryService.editAccessory(base);
    }
    
    /**
     * 根据id获取附属品信息
     * @param markId
     * @return
     */
    @GetMapping(value = "/{markId}")
    public AccessoryInfo getAccessoryInfo(@PathVariable("markId") @NotBlank String markId) {
        return accessoryService.selectAccessoryById(markId);
    }

}
