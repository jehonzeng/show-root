package com.szhengzhu.controller;

import com.szhengzhu.bean.activity.GiftInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.GiftService;
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
@RequestMapping(value = "gifts")
public class GiftController {

    @Resource
    private GiftService giftService;

    @PostMapping(value = "/page")
    public PageGrid<GiftInfo> getGiftPage(@RequestBody PageParam<GiftInfo> base){
        return giftService.getGiftPage(base);
    }

    @PostMapping(value = "/add")
    public GiftInfo addGift(@RequestBody @Validated GiftInfo base){
        return giftService.addGift(base);
    }

    @PatchMapping(value = "/modify")
    public GiftInfo updateGift(@RequestBody @Validated GiftInfo base){
        return giftService.updateGift(base);
    }

    @GetMapping(value = "/{markId}")
    public GiftInfo getGiftInfo(@PathVariable("markId") @NotBlank String markId){
        return giftService.getGiftByMark(markId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> getGiftCombobox(){
        return giftService.getGiftCombobox();
    }
}
