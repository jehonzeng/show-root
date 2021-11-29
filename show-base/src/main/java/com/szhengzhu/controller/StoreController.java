package com.szhengzhu.controller;

import com.szhengzhu.bean.base.StoreInfo;
import com.szhengzhu.bean.base.StoreItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.StoreService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping("/stores")
public class StoreController {

    @Resource
    private StoreService storeService;

    @PostMapping(value = "/page")
    public PageGrid<StoreInfo> pageStore(@RequestBody PageParam<StoreInfo> base) {
        return storeService.getStorePage(base);
    }

    @PostMapping(value = "/add")
    public StoreInfo addStore(@RequestBody @Validated StoreInfo base) {
        return storeService.addStore(base);
    }

    @PatchMapping(value = "/edit")
    public StoreInfo editStore(@RequestBody @Validated StoreInfo base) {
        return storeService.editStore(base);
    }

    @GetMapping(value = "/list")
    public List<Combobox> listStore() {
        return storeService.getComboboxList();
    }

    @PostMapping(value = "/item/batch")
    public void addStoreStaff(@RequestBody BatchVo base) {
        storeService.addStoreStaff(base);
    }

    @PostMapping(value = "/item/delete")
    public void deleteStoreStaff(@RequestBody StoreItem base) {
        storeService.deleteStoreStaff(base);
    }

    @GetMapping(value = "/item/id")
    public String getStoreByStaff(@RequestParam("userId") @NotBlank String userId) {
        return storeService.getStoreByStaff(userId);
    }
}
