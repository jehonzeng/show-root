package com.szhengzhu.controller;

import com.szhengzhu.bean.member.PendDishes;
import com.szhengzhu.bean.member.ReceiveDishes;
import com.szhengzhu.bean.member.ReceiveRecord;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.ReceiveService;
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
@RequestMapping("/receive")
public class ReceiveController {

    @Resource
    private ReceiveService receiveService;

    @GetMapping(value = "/info/{markId}")
    public List<ReceiveVo> selectByDish(@PathVariable("markId") @NotBlank String markId) {
        return receiveService.selectByDish(markId);
    }

    @PatchMapping(value = "/pend/modify")
    public void modifyPend(@RequestBody PendDishes pendDishes) {
        receiveService.modifyPend(pendDishes);
    }

    @PostMapping(value = "/pend")
    public List<PendDishes> queryPend(@RequestBody PendDishes pendDishes) {
        return receiveService.queryPend(pendDishes);
    }

    @PostMapping(value = "/info")
    public PageGrid<ReceiveVo> queryReceive(PageParam<ReceiveDishes> param) {
        return receiveService.queryReceive(param);
    }

    @PostMapping(value = "/info/add")
    public void addReceive(@RequestBody @Validated ReceiveDishes receiveDishes) {
        receiveService.addReceive(receiveDishes);
    }

    @PatchMapping(value = "/info/modify")
    public void modifyReceive(@RequestBody ReceiveDishes receiveDishes) {
        receiveService.modifyReceive(receiveDishes);
    }

    @PostMapping(value = "/Record")
    public List<ReceiveRecord> queryRecord(@RequestBody ReceiveRecord receiveRecord) {
        return receiveService.queryRecord(receiveRecord);
    }

    @PatchMapping(value = "/Record/modify")
    public void modifyRecord(@RequestBody ReceiveRecord receiveRecord) {
        receiveService.modifyRecord(receiveRecord);
    }

    @PostMapping(value = "/ticket/{markId}")
    public void receiveTicket(@PathVariable("markId") @NotBlank String markId) {
        receiveService.receiveTicket(markId);
    }
}
