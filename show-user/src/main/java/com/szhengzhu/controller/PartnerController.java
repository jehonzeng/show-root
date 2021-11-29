package com.szhengzhu.controller;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.PartnerService;
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
@RequestMapping(value = "partners")
public class PartnerController {

    @Resource
    private PartnerService partnerService;

    @PostMapping(value = "/add")
    public PartnerInfo add(@RequestBody @Validated PartnerInfo base) {
        return partnerService.addPartner(base);
    }

    @PatchMapping(value = "/edit")
    public PartnerInfo modify(@RequestBody @Validated PartnerInfo base) {
        return partnerService.modify(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<PartnerInfo> page(@RequestBody PageParam<PartnerInfo> base) {
        return partnerService.getPartnerPage(base);
    }

    @DeleteMapping(value = "/{markId}")
    public void delete(@PathVariable("markId") @NotBlank String markId) {
        partnerService.deletePartner(markId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listPartnerCombobox() {
        return partnerService.listCombobox();
    }
}
