package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Tag;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.TagService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @PostMapping(value = "/page")
    public PageGrid<Tag> page(@RequestBody PageParam<Tag> pageParam) {
        return tagService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Tag tag) {
        tagService.add(tag);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Tag tag) {
        tagService.modify(tag);
    }

    @PatchMapping(value = "/batch/status/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] tagIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        tagService.modifyStatus(tagIds, status);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> getCombobox(@RequestParam("storeId") @NotBlank String storeId) {
        return tagService.getCombobox(storeId);
    }

    @PostMapping(value = "/commodity/batch/add/{tagId}")
    public void addTagCommodity(@RequestBody @NotEmpty String[] commodityIds, @PathVariable("tagId") @NotBlank String tagId) {
        tagService.addTagCommodity(commodityIds, tagId);
    }

    @DeleteMapping(value = "/commodity/batch/delete/{tagId}")
    public void deleteTagCommodity(@RequestBody @NotEmpty String[] commodityIds, @PathVariable("tagId") @NotBlank String tagId) {
        tagService.deleteTagCommodity(commodityIds, tagId);
    }
}
