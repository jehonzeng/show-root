package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.TableArea;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.TableAreaService;
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
@RequestMapping("/table/area")
public class TableAreaController {

    @Resource
    private TableAreaService tableAreaService;

    @PostMapping(value = "/page")
    public PageGrid<TableArea> page(@RequestBody PageParam<TableArea> pageParam) {
        return tableAreaService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated TableArea tableArea) {
        return new Result<>(tableAreaService.add(tableArea));
    }

    @PatchMapping(value = "/modify")
    public TableArea modify(@RequestBody @Validated TableArea tableArea) {
        return tableAreaService.modify(tableArea);
    }

    @DeleteMapping(value = "/{areaId}")
    public void delete(@PathVariable("areaId") @NotBlank String areaId) {
        tableAreaService.delete(areaId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox(@RequestParam("storeId") @NotBlank String storeId) {
        return tableAreaService.listCombobox(storeId);
    }
}
