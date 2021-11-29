package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.bean.member.Activity;
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
import javax.validation.constraints.NotBlank;

/**
 * @author jehon
 */
@Validated
@Api(tags = "活动：ActivityController")
@RestController
@RequestMapping("/v1/activity")
public class ActivityController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "添加活动")
    @PostMapping(value = "")
    public Result add(HttpServletRequest req, @RequestBody @Validated Activity activity) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        activity.setCreator(employeeId);
        return showMemberClient.addActivity(activity);
    }

    @ApiOperation(value = "修改活动")
    @PatchMapping(value = "")
    public Result modify(HttpServletRequest req, @RequestBody @Validated Activity activity) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        activity.setModifier(employeeId);
        return showMemberClient.modifyActivity(activity);
    }

    @ApiOperation(value = "获取活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Activity>> page(HttpServletRequest req, @RequestBody PageParam<Activity> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Activity param = ObjectUtil.isNull(pageParam.getData()) ? new Activity() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showMemberClient.pageActivity(pageParam);
    }

    @ApiOperation(value = "删除活动")
    @DeleteMapping(value = "/{markId}")
    public Result deleteByActivityId(@PathVariable("markId") @NotBlank String markId){
        return showMemberClient.deleteByActivityId(markId);
    }
}
