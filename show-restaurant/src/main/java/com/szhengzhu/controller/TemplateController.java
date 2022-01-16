package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.TicketTemplate;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"券模板：TemplateController"})
@RestController
@RequestMapping(value = "/v1/template")
public class TemplateController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "设置券模板")
    @PostMapping(value = "")
    public Result addTemplate(HttpSession session, @RequestBody @Validated TicketTemplate base) {
        String userId = (String) session.getAttribute(Contacts.RESTAURANT_USER);
        base.setCreator(userId);
        return showOrderingClient.addTemplate(base);
    }

    @ApiOperation(value = "获取券模板信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<TicketTemplate>> pageTemplate(@RequestBody PageParam<TicketTemplate> base) {
        return showOrderingClient.getTemplatePage(base);
    }

    @ApiOperation(value = "编辑券模板")
    @PatchMapping(value = "")
    public Result modifyTemplate(@RequestBody @Validated TicketTemplate base) {
        return showOrderingClient.modifyTemplate(base);
    }

    @ApiOperation(value = "获取编辑的模板信息")
    @GetMapping(value = "/{markId}")
    public Result<TicketTemplate> getTemplate(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.getTemplateInfo(markId);
    }

    @ApiOperation(value = "删除模板信息")
    @DeleteMapping(value = "/{markId}")
    public Result deleteTemplate(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.deleteTemplateInfo(markId);
    }

    @ApiOperation(value = "批量修改券模板状态(0：无效 ，1：有效)")
    @PatchMapping(value = "/batch/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] templateIds, @PathVariable("status") @NotNull Integer status) {
        return showOrderingClient.modifyTemplateStatus(templateIds, status);
    }

    @ApiOperation(value = "批量删除券模板")
    @PatchMapping(value = "/batch/delete")
    public Result deleteBatch(@RequestBody @NotEmpty String[] templateIds) {
        return showOrderingClient.modifyTemplateStatus(templateIds, -1);
    }

    @ApiOperation(value = "获取券列表")
    @GetMapping(value = "/combobox")
    public Result<List<Map<String, String>>> listTemplate() {
        return showOrderingClient.getTemplateCombobox();
    }
}
