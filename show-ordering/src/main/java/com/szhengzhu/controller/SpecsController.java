package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Specs;
import com.szhengzhu.bean.ordering.SpecsItem;
import com.szhengzhu.bean.ordering.vo.SpecsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.SpecsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/specs")
public class SpecsController {

    @Resource
    private SpecsService specsService;

    @PostMapping(value = "/page")
    public PageGrid<Specs> page(@RequestBody PageParam<Specs> pageParam) {
        return specsService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Specs specs) {
        specsService.add(specs);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Specs specs) {
        specsService.modify(specs);
    }

    @PatchMapping(value = "/batch/status/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] specsIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        specsService.modifyStatus(specsIds, status);
    }

    @GetMapping(value = "/combobox")
    public List<SpecsVo> getCombobox(@RequestParam("storeId") @NotBlank String storeId) {
        return specsService.getCombobox(storeId);
    }

    @GetMapping(value = "/combobox/cate")
    public List<SpecsVo> getComboboxByCateId(@RequestParam(required = false) String[] cateIds, @RequestParam("storeId") String storeId) {
        return specsService.getComboboxByCateId(cateIds, storeId);
    }

    @GetMapping(value = "/list/by")
    public List<Map<String, String>> list(@RequestParam(value = "name", required = false) String name) {
        return specsService.list(name);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<SpecsItem> pageItem(@RequestBody PageParam<SpecsItem> pageParam) {
        return specsService.pageItem(pageParam);
    }

    @PostMapping(value = "/item/add")
    public void addItem(@RequestBody @Validated SpecsItem specsItem) {
        specsService.addItem(specsItem);
    }

    @PatchMapping(value = "/item/modify")
    public void modifyItem(@RequestBody @Validated SpecsItem specsItem) {
        specsService.modifyItem(specsItem);
    }

    @PatchMapping(value = "/item/batch/status/{status}")
    public void modifyItemStatus(@RequestBody @NotEmpty String[] itemIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        specsService.modifyItemStatus(itemIds, status);
    }
}
