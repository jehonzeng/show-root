package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Identity;
import com.szhengzhu.bean.vo.Combobox;
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
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "角色：IdenetityController")
@RestController
@RequestMapping("/v1/identity")
public class IdentityController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取角色分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Identity>> page(HttpServletRequest req, @RequestBody PageParam<Identity> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Identity param = ObjectUtil.isNull(pageParam.getData()) ? new Identity() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageIdentity(pageParam);
    }

    @ApiOperation(value = "获取角色详细信息")
    @GetMapping(value = "/{identityId}")
    public Result<Identity> getInfo(@PathVariable("identityId") @NotBlank String identityId) {
        return showOrderingClient.getIdentityInfo(identityId);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping(value = "")
    public Result add(HttpServletRequest req, @RequestBody @Validated Identity identity) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        identity.setStoreId(storeId);
        return showOrderingClient.addIdentity(identity);
    }

    @ApiOperation(value = "修改角色")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Identity identity) {
        return showOrderingClient.modifyIdentity(identity);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "/{identityId}")
    public Result delete(@PathVariable("identityId") @NotBlank String identityId) {
        return showOrderingClient.deleteIdentity(identityId);
    }

    @ApiOperation(value = "获取角色键值对列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.listIdentityCombobox(storeId);
    }

//    @ApiOperation(value = "添加/删除角色用户")
//    @PostMapping(value = "/employee/{identityId}")
//    public Result<Object> optEmployee(@RequestBody String[] employeeIds, @PathVariable("identityId") String identityId) {
//        if (StringUtils.isEmpty(identityId)) {
//            return new Result<>(StatusCode._4004);
//        }
//        return null;
//    }

    @ApiOperation(value = "获取角色用户id数组")
    @GetMapping(value = "/employee/{identityId}")
    public Result<String[]> listEmployee(@PathVariable("identityId") @NotBlank String identityId) {
        return showOrderingClient.listIdentityEmployee(identityId);
    }
}
