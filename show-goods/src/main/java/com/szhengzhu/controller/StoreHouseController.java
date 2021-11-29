package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.StoreHouseService;
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
@RequestMapping(value = "houses")
public class StoreHouseController {

    @Resource
    private StoreHouseService storeHouseService;

    @PostMapping(value = "/add")
    public StoreHouseInfo addHouse(@RequestBody @Validated StoreHouseInfo storeHouseInfo) {
        return storeHouseService.addStoreHouse(storeHouseInfo);
    }

    @PatchMapping(value = "/edit")
    public StoreHouseInfo modifyHouse(@RequestBody @Validated StoreHouseInfo storeHouseInfo) {
        return storeHouseService.modifyStoreHouse(storeHouseInfo);
    }

    @PostMapping(value = "/page")
    public PageGrid<StoreHouseInfo> getPage(@RequestBody PageParam<StoreHouseInfo> base) {
        return storeHouseService.getPage(base);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox() {
        return storeHouseService.listCombobox();
    }

    @GetMapping(value = "/{markId}")
    public StoreHouseInfo getHouseInfo(@PathVariable("markId") @NotBlank String markId){
        return storeHouseService.getHouseInfo(markId);
    }
}
