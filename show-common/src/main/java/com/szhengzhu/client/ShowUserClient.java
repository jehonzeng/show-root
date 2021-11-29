package com.szhengzhu.client;

import com.szhengzhu.bean.user.*;
import com.szhengzhu.bean.vo.*;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ExceptionAdvice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@FeignClient(name = "show-user", fallback = ExceptionAdvice.class)
public interface ShowUserClient {

    /** 优惠码 */
    @GetMapping(value = "/managerCode/getByCode")
    Result<ManagerCode> getManagerCodeByCode(@RequestParam("code") String code);

    @PostMapping(value = "/managerCode/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ManagerCode>> pageManagerCode(@RequestBody PageParam<ManagerCode> pageParam);

    @GetMapping(value = "/managerCode/reflush")
    Result reflushManagerCode();

    @PatchMapping(value = "/managerCode/modify", consumes = Contacts.CONSUMES)
    Result modifyManagerCode(@RequestBody ManagerCode code);

    /** 合作商 */
    @PostMapping(value = "/partners/add", consumes = Contacts.CONSUMES)
    Result<PartnerInfo> addPartner(@RequestBody PartnerInfo base);

    @PatchMapping(value = "/partners/edit", consumes = Contacts.CONSUMES)
    Result<PartnerInfo> editPartner(@RequestBody PartnerInfo base);

    @PostMapping(value = "/partners/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PartnerInfo>> getPartnerPage(@RequestBody PageParam<PartnerInfo> base);

    @DeleteMapping(value = "/partners/{markId}")
    Result deletePartner(@PathVariable("markId") String markId);

    @GetMapping(value = "/partners/combobox")
    Result<List<Combobox>> listPartnerCombobox();

    /** 角色 */
    @PostMapping(value = "/roles/add", consumes = Contacts.CONSUMES)
    Result<RoleInfo> addRole(@RequestBody RoleInfo roleInfo);

    @PatchMapping(value = "/roles/modify", consumes = Contacts.CONSUMES)
    Result<RoleInfo> modifyRole(@RequestBody RoleInfo roleInfo);

    @GetMapping(value = "/roles/{markId}")
    Result<RoleInfo> getRoleInfo(@PathVariable(value = "markId") String markId);

    @PostMapping(value = "/roles/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<RoleInfo>> pageRole(@RequestBody PageParam<RoleInfo> rolePage);

    @PostMapping(value = "/roles/list", consumes = Contacts.CONSUMES)
    Result<List<RoleInfo>> listRole(@RequestBody RoleInfo roleInfo);

    @DeleteMapping(value = "/roles/roleuser")
    Result removeRoleUsers(@RequestParam("roleId") String roleId,
                           @RequestParam("userIds") String[] userIds);

    @PostMapping(value = "/roles/roleuser")
    Result addRoleUsers(@RequestParam("roleId") String roleId,
                        @RequestParam("userIds") String[] userIds);

    @GetMapping(value = "/roles/combobox")
    Result<List<Combobox>> combobox(@RequestParam(value = "roleCode") String roleCode);

    @GetMapping(value = "/roles/usersByRole")
    List<UserBase> getUsersByRole(@RequestParam("roleId") String roleId);

    /** 用户 */
    @GetMapping(value = "/users/{markId}")
    Result<UserInfo> getUserById(@PathVariable("markId") String markId);

    @GetMapping(value = "/users/my/{userId}")
    Result<UserInfoVo> getMyInfo(@PathVariable("userId") String userId);

    @GetMapping(value = "/users/w/{openId}")
    Result<UserInfo> getUserByOpenId(@PathVariable("openId") String openId);

    @GetMapping(value = "/users/t/{token}")
    Result<UserInfo> getUserByToken(@RequestParam("token") String token);


    @GetMapping(value = "/users/p/{phone}")
    Result<UserInfo> getUserByPhone(@PathVariable("phone") String phone);

    @GetMapping(value = "/users/manager")
    Result<UserInfo> getManager(@RequestParam("phone") String phone);

    @PostMapping(value = "/users/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<UserVo>> pageUser(@RequestBody PageParam<UserVo> userPage);

    @PostMapping(value = "/users/outrole/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<UserVo>> pageOutRoleUser(@RequestBody PageParam<UserVo> userPage);

    @PatchMapping(value = "/users/modify", consumes = Contacts.CONSUMES)
    Result modifyUser(@RequestBody UserInfo userInfo);

    @GetMapping(value = "/users/combobox")
    Result<List<Combobox>> getUserList();

    @PostMapping(value = "/users/add/x",  consumes = Contacts.CONSUMES)
    Result<String> addXUser(@RequestBody UserInfo userInfo);

    @GetMapping(value = "/users/manage/{markId}")
    Result<Boolean> checkManage(@PathVariable(value = "markId") String markId);

    @PostMapping(value = "/users/instaff/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<UserInfo>> pageInStoreStaff(@RequestBody PageParam<StoreStaffVo> base);

    @PostMapping(value = "/users/outstaff/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<UserInfo>> pageOutStoreStaff(@RequestBody PageParam<StoreStaffVo> base);

    @GetMapping(value = "/users/restaurant/manager")
    Result<UserInfo> getResManager(@RequestParam("phone") String phone);

    @PostMapping(value = "/users/add", consumes = Contacts.CONSUMES)
    Result<UserInfo> addUser(@RequestBody UserInfo userInfo);

    @GetMapping(value = "/users/manager/list")
    Result<List<Map<String, String>>> listManager();

    @PostMapping(value = "/users/openid/list")
    Result<List<String>> listWopenIdsByUserIds(@RequestBody List<String> userIds);

    @PostMapping(value = "/wechat/add", consumes = Contacts.CONSUMES)
    Result addWechat(@RequestBody WechatInfo wechatInfo);

    @GetMapping(value = "/users/{xopenId}/x")
    Result<UserInfo> getUserInfoByXopenId(@PathVariable("xopenId") String xopenId);

    @GetMapping(value = "/users/u/{unionId}")
    Result<UserInfo> getInfoByUnionId(@PathVariable("unionId") String unionId);

    @GetMapping(value = "/users/token/{token}")
    Result<UserToken> getUserToken(@PathVariable("token") String token);

    @GetMapping(value = "/users/token/add")
    Result<UserToken> addUserToken(@RequestParam("userId") String userId);

    @PostMapping(value = "/wechat/add/x",  consumes = Contacts.CONSUMES)
    Result addWechat(@RequestBody XwechatInfo xwechatInfo);
}
