package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.TicketTemplate;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.TemplateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/template")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated TicketTemplate base) {
        templateService.add(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<TicketTemplate> page(@RequestBody PageParam<TicketTemplate> base) {
        return templateService.page(base);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated TicketTemplate base) {
        templateService.modify(base);
    }

    @GetMapping(value = "/{markId}")
    public TicketTemplate getInfo(@PathVariable("markId") @NotBlank String markId) {
        return templateService.getInfo(markId);
    }

    @DeleteMapping(value = "/{markId}")
    public void delete(@PathVariable("markId") @NotBlank String markId) {
        templateService.delete(markId);
    }

    @PatchMapping(value = "/batch/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] templateIds, @PathVariable("status") @NotNull Integer status) {
        templateService.modifyStatus(templateIds, status);
    }

    @GetMapping(value = "/combobox")
    public List<Map<String, String>> combobox() {
        return templateService.combobox();
    }
}
