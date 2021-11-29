package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Table;
import com.szhengzhu.bean.ordering.TableReservation;
import com.szhengzhu.bean.ordering.param.TableParam;
import com.szhengzhu.bean.ordering.vo.TableBaseVo;
import com.szhengzhu.bean.ordering.vo.TableVo;
import com.szhengzhu.bean.xwechat.param.TableOpenParam;
import com.szhengzhu.bean.xwechat.vo.TableModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.TableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableService tableService;

    @PostMapping(value = "/page")
    public PageGrid<TableVo> page(@RequestBody PageParam<TableParam> pageParam) {
        return tableService.page(pageParam);
    }

    @GetMapping(value = "/{tableId}")
    public Table getInfo(@PathVariable("tableId") @NotBlank String tableId) {
        return tableService.getInfo(tableId);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated Table table) {
        return new Result<>(tableService.add(table));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Table table) {
        tableService.modify(table);
    }

    @DeleteMapping(value = "/{tableId}")
    public void delete(@PathVariable("tableId") @NotBlank String tableId) {
        tableService.delete(tableId);
    }

    @GetMapping(value = "/info/x")
    public TableModel getLjsTableInfo(@RequestParam("tableId") @NotBlank String tableId) {
        return tableService.getLjsTableInfo(tableId);
    }

    @PostMapping(value = "/list/res")
    public List<TableBaseVo> listResByStore(@RequestBody(required = false) TableReservation tableReservation) {
        return tableService.listResByStore(tableReservation);
    }

    @GetMapping(value = "/change/res")
    public List<String> changeTable(@RequestParam("oldTableId") @NotBlank String oldTableId, @RequestParam("newTableId") @NotBlank String newTableId, @RequestParam("employeeId") String employeeId) {
        return tableService.changeTable(oldTableId, newTableId, employeeId);
    }

    @PostMapping(value = "/open")
    public void openTable(@RequestBody @Validated TableOpenParam param) {
        tableService.open(param);
    }

    @GetMapping(value = "/clear/res")
    public void clear(@RequestParam("tableId") @NotBlank String tableId) {
        tableService.clear(tableId);
    }

    @GetMapping(value = "/mannum/modify/x")
    public void xmodifyManNum(@RequestParam("tableId") @NotBlank String tableId, @RequestParam("manNum") @NotNull @Min(0) Integer manNum) {
        tableService.modifyManNum(tableId, manNum);
    }

    @GetMapping(value = "/{storeId}/{tableCode}")
    public Table getInfoByStroreCode(@PathVariable("storeId") @NotBlank String storeId, @PathVariable("tableCode") @NotBlank String tableCode) {
        return tableService.getInfoByStoreCode(storeId, tableCode);
    }

    @GetMapping(value = "/id/x")
    public Result<String> getTableId(@RequestParam("url") @NotBlank String url) {
        return new Result<>(tableService.getTableId(url));
    }
}
