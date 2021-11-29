package com.szhengzhu.controller;

import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.AttributeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Resource
    private AttributeService attributeService;

    @PostMapping(value = "/add")
    public AttributeInfo addAttribute(@RequestBody @Validated AttributeInfo attributeInfo) {
        return attributeService.saveAttribute(attributeInfo);
    }

    @PatchMapping(value = "/modify")
    public AttributeInfo modifyAttribute(@RequestBody @Validated AttributeInfo attributeInfo) {
        return attributeService.updateAttribute(attributeInfo);
    }

    @GetMapping(value = "/{markId}")
    public AttributeInfo getAttributeInfo(@PathVariable("markId") @NotBlank String markId) {
        return attributeService.getAttributeById(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<AttributeInfo> pageAttribute(@RequestBody PageParam<AttributeInfo> attrPage) {
        return attributeService.pageAttribute(attrPage);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox(@RequestParam("type") @NotBlank String type) {
        return attributeService.listCombobox(type);
    }

    @GetMapping(value = "/getCode")
    public Result<String> getCodeByName(@RequestParam("name") @NotBlank String name) {
        return new Result<>(attributeService.getCodeByName(name));
    }
}
