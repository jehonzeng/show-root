package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Store;
import com.szhengzhu.bean.ordering.param.StoreParam;
import com.szhengzhu.bean.ordering.vo.StoreMapVo;
import com.szhengzhu.bean.xwechat.vo.StoreModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.StoreService;
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
@RequestMapping("/store")
public class StoreController {

    @Resource
    private StoreService storeService;

    @PostMapping(value = "/page")
    public PageGrid<Store> page(@RequestBody PageParam<StoreParam> pageParam) {
        return storeService.page(pageParam);
    }

    @GetMapping(value = "/{storeId}")
    public Store getInfo(@PathVariable("storeId") @NotBlank String storeId) {
        return storeService.getInfo(storeId);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated Store store) {
        return new Result<>(storeService.add(store));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Store store) {
        storeService.modify(store);
    }

    @DeleteMapping(value = "/{storeId}")
    public void delete(@PathVariable("storeId") @NotBlank String storeId) {
        storeService.delete(storeId);
    }

    @GetMapping(value = "/employee/map/{employeeId}")
    public List<StoreMapVo> listByEmployee(@PathVariable("employeeId") @NotBlank String employeeId) {
        return storeService.listByEmployee(employeeId);
    }

    @GetMapping(value = "/x/list")
    public List<StoreModel> listLjsStore(@RequestParam("city") @NotBlank String city) {
        return storeService.listLjsStore(city);
    }
}
