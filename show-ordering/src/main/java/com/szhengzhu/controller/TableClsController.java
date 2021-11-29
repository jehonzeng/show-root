package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.TableCls;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.TableClsService;
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
@RequestMapping("/table/cls")
public class TableClsController {

    @Resource
    private TableClsService tableClsService;

    @PostMapping(value = "/page")
    public PageGrid<TableCls> page(@RequestBody PageParam<TableCls> pageParam) {
        return tableClsService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated TableCls tableCls) {
        return new Result<>(tableClsService.add(tableCls));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated TableCls tableCls) {
        tableClsService.modify(tableCls);
    }

    @DeleteMapping(value = "/{clsId}")
    public void delete(@PathVariable("clsId") @NotBlank String clsId) {
        tableClsService.delete(clsId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox(@RequestParam("storeId") @NotBlank String storeId) {
        return tableClsService.listCombobox(storeId);
    }
}
