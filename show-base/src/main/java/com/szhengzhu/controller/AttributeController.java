package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.AttributeService;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Resource
    private AttributeService attributeService;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<AttributeInfo> addAttribute(@RequestBody AttributeInfo attributeInfo) {
        return attributeService.saveAttribute(attributeInfo);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<AttributeInfo> modifyAttribute(@RequestBody AttributeInfo attributeInfo) {
        return attributeService.updateAttribute(attributeInfo);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<AttributeInfo> getAttributeInfo(@PathVariable("markId") String markId) {
        return attributeService.getAttributeById(markId);
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<AttributeInfo>> pageAttribute(@RequestBody PageParam<AttributeInfo> attrPage) {
        return attributeService.pageAttribute(attrPage);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox(@RequestParam("type") String type) {
        return attributeService.listCombobox(type);
    }
}
