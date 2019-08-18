package com.szhengzhu.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.IntegralVo;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient("show-user")
public interface ShowUserClient {

    @RequestMapping(value = "/users/manager", method = RequestMethod.GET)
    Result<UserInfo> getManager(@RequestParam("phone") String phone);

    @RequestMapping(value = "/users/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<UserVo>> pageUser(@RequestBody PageParam<UserVo> userPage);

    @RequestMapping(value = "/users/page/notin", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<UserVo>> pageUserNotIn(@RequestBody PageParam<UserVo> userPage);

    @RequestMapping(value = "/users/{markId}", method = RequestMethod.GET)
    Result<UserInfo> getUserById(@PathVariable("markId") String markId);

    @RequestMapping(value = "/users/integraltotal/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<IntegralVo>> pageIntegralTotal(
            @RequestBody PageParam<Map<String, String>> integralPage);

    @RequestMapping(value = "/users/integral/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<UserIntegral>> pageIntegral(@RequestBody PageParam<UserIntegral> integralPage);

    @RequestMapping(value = "/roles/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<RoleInfo> addRole(@RequestBody RoleInfo roleInfo);

    @RequestMapping(value = "/roles/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<RoleInfo> modifyRole(@RequestBody RoleInfo roleInfo);

    @RequestMapping(value = "/roles/{markId}", method = RequestMethod.GET)
    Result<RoleInfo> getRoleInfo(@PathVariable(value = "markId") String markId);

    @RequestMapping(value = "/roles/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<RoleInfo>> pageRole(@RequestBody PageParam<RoleInfo> rolePage);

    @RequestMapping(value = "/roles/list", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<List<RoleInfo>> listRole(@RequestBody RoleInfo roleInfo);

    @RequestMapping(value = "/roles/roleuser", method = RequestMethod.DELETE)
    Result<?> removeRoleUsers(@RequestParam("roleId") String roleId,
            @RequestParam("userIds") String[] userIds);

    @RequestMapping(value = "/roles/roleuser", method = RequestMethod.POST)
    Result<?> addRoleUsers(@RequestParam("roleId") String roleId,
            @RequestParam("userIds") String[] userIds);
    
    @RequestMapping(value = "/roles/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> combobox(@RequestParam(value = "roleCode") String roleCode);

    @RequestMapping(value = "/users/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> getUserList();

    @RequestMapping(value = "/partners/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addPartner(@RequestBody PartnerInfo base);

    @RequestMapping(value = "/partners/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> editPartner(@RequestBody PartnerInfo base);

    @RequestMapping(value = "/partners/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<PartnerInfo>> getPartnerPage(@RequestBody PageParam<PartnerInfo> base);

    @RequestMapping(value = "/partners/{markId}", method = RequestMethod.DELETE)
    Result<?> deletePartner(@PathVariable("markId") String markId);
}
