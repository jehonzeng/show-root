package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.TableStatus;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.TableStatusService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping("/table/status")
public class TableStatusController {

    @Resource
    private TableStatusService tableStatusService;

    @PostMapping(value = "/page")
    public PageGrid<TableStatus> page(@RequestBody PageParam<TableStatus> pageParam) {
        return tableStatusService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public TableStatus add(@RequestBody @Validated TableStatus tableStatus) {
        return tableStatusService.add(tableStatus);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated TableStatus tableStatus) {
        tableStatusService.modify(tableStatus);
    }

    @DeleteMapping(value = "/{statusId}")
    public void delete(@PathVariable("statusId") @NotBlank String statusId) {
        tableStatusService.delete(statusId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox(@RequestParam("storeId") @NotBlank String storeId) {
        return tableStatusService.listCombobox(storeId);
    }
}
