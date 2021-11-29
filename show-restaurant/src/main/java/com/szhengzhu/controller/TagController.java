package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.ordering.Tag;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "标签：TagController")
@RestController
@RequestMapping("/v1/tag")
public class TagController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取标签信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Tag>> page(HttpServletRequest req, @RequestBody PageParam<Tag> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Tag param = ObjectUtil.isNull(pageParam.getData()) ? new Tag() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageTag(pageParam);
    }

    @ApiOperation(value = "添加标签信息")
    @PostMapping(value = "")
    public Result add(HttpServletRequest req, @RequestBody @Validated Tag tag) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        tag.setStoreId(storeId);
        return showOrderingClient.addTag(tag);
    }

    @ApiOperation(value = "修改标签信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Tag tag) {
        return showOrderingClient.modifyTag(tag);
    }

    @ApiOperation(value = "批量修改标签状态")
    @PatchMapping(value = "/batch/status/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] tagIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        return showOrderingClient.modifyTagStatus(tagIds, status);
    }

    @ApiOperation(value = "批量删除标签信息")
    @DeleteMapping(value = "/batch")
    public Result<?> delete(@RequestBody @NotEmpty String[] tagIds) {
        return showOrderingClient.modifyTagStatus(tagIds, -1);
    }

    @ApiOperation(value = "获取已启用标签键值对列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> getCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.getTagCombobox(storeId);
    }

    @ApiOperation(value = "批量添加标签商品关联关系")
    @PostMapping(value = "/commodity/batch/{tagId}")
    public Result addTagCommodity(@RequestBody @NotEmpty String[] commodityIds, @RequestParam("tagId") @NotBlank String tagId) {
        return showOrderingClient.addTagCommodity(commodityIds, tagId);
    }

    @ApiOperation(value = "批量删除标签商品关联关系")
    @DeleteMapping(value = "/commodity/batch/{tagId}")
    public Result deleteTagCommodity(@RequestBody @NotEmpty String[] commodityIds, @PathVariable("tagId") @NotBlank String tagId) {
        return showOrderingClient.deleteTagCommodity(commodityIds, tagId);
    }
}
