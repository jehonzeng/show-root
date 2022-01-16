package com.szhengzhu.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.annotation.SystemLog;
import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.vo.StoreMapVo;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.LoginBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.SmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Slf4j
@Api(tags = "登录：LoginController")
@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private Redis redis;

    @Resource
    private ShowOrderingClient showOrderingClient;

    private static final String CODE_CACHE_PRE = "restaurant:code:send:";

    @ApiOperation(value = "通过手机号获取短信")
    @GetMapping(value = "/code")
    public void getCode(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        ShowAssert.checkTrue(!Validator.isMobile(phone), StatusCode._4000);
        int sendTimes = 6;
        String codeCacheKey = CODE_CACHE_PRE + phone;
        Object obj = redis.get(codeCacheKey);
        LoginBase login = ObjectUtil.isNull(obj) ? new LoginBase(phone) : JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
        ShowAssert.checkTrue(login.isOften(sendTimes), StatusCode._4001);
        Result<Employee> result = showOrderingClient.getEmployeeByPhone(phone);
        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._4017);
        login.setMarkId(result.getData().getMarkId());
        login.refresh(phone);
        redis.set(codeCacheKey, login, 3 * 60);
        log.info("{}: 验证码为 {}", login.getPhone(), login.getCode());
        SmsUtils.send(phone, login.getCode());
    }

    @SystemLog(desc = "登录系统")
    @ApiOperation(value = "用户登录")
    @GetMapping(value = "/login")
    public Result login(HttpSession session, HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String codeCacheKey = CODE_CACHE_PRE + phone;
        Object obj = redis.get(codeCacheKey);
        ShowAssert.checkTrue(ObjectUtil.isNull(obj), StatusCode._4003);
        LoginBase loginBase = JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
        ShowAssert.checkTrue(!loginBase.equals(phone, code), StatusCode._4002);
        redis.del(codeCacheKey);
        String employeeId = loginBase.getMarkId();
        Result<Employee> employeeResult = showOrderingClient.getEmployeeInfo(employeeId);
        Result<List<StoreMapVo>> storeResult = showOrderingClient.listStoreMapByEmployee(employeeId);
        ShowAssert.checkTrue(!storeResult.isSuccess(), StatusCode._4039);
        List<StoreMapVo> storeMaps = storeResult.getData();
        // 设置默认门店
        storeMaps.get(0).setDefaults(true);
        String defaultStore = storeMaps.get(0).getStoreId();
        Result<String> authResult = showOrderingClient.getIdentityAuth(employeeId, defaultStore);
        // 存入session
        session.setAttribute(Contacts.RESTAURANT_STORE, defaultStore);
        session.setAttribute(Contacts.RESTAURANT_USER, employeeId);
        session.removeAttribute("login_base");
        // 设置返回值
        Map<String, Object> result = new HashMap<>(4);
        result.put("name", employeeResult.getData() == null ? "" : employeeResult.getData().getName());
        result.put("stores", storeMaps);
        result.put("auth", authResult.getData());
        return new Result<>(result);
    }

    @SystemLog(desc = "安全退出系统")
    @ApiOperation(value = "后台主动退出登录")
    @GetMapping(value = "/logout")
    public Result logOut(HttpSession session) {
        session.removeAttribute(Contacts.RESTAURANT_USER);
        session.removeAttribute(Contacts.RESTAURANT_STORE);
        return new Result<>();
    }

    @ApiOperation(value = "切换门店")
    @GetMapping(value = "/v1/store/change")
    public Result changeStore(HttpSession session, HttpServletRequest request, @RequestParam("storeId") @NotBlank String storeId) {
        session.setAttribute(Contacts.RESTAURANT_STORE, storeId);
        String employeeId = (String) request.getSession().getAttribute(Contacts.RESTAURANT_USER);
        Result<String> authResult = showOrderingClient.getIdentityAuth(employeeId, storeId);
        Map<String, Object> result = new HashMap<>(4);
        result.put("auth", authResult.getData());
        return new Result<>(result);
    }

    @ApiOperation(value = "获取用户权限信息")
    @GetMapping(value = "/v1/info")
    public Result getInfo(HttpSession session) {
        String storeId = (String) session.getAttribute(Contacts.RESTAURANT_STORE);
        String employeeId = (String) session.getAttribute(Contacts.RESTAURANT_USER);
        ShowAssert.checkTrue(StrUtil.isEmpty(employeeId), StatusCode._4005);
        Result<Employee> employeeResult = showOrderingClient.getEmployeeInfo(employeeId);
        Result<List<StoreMapVo>> storeResult = showOrderingClient.listStoreMapByEmployee(employeeId);
        ShowAssert.checkTrue(!storeResult.isSuccess(), StatusCode._4039);
        List<StoreMapVo> storeMaps = storeResult.getData();
        storeMaps.forEach(storeMap -> {
            if (storeId.equals(storeMap.getStoreId())) {
                storeMap.setDefaults(true);
            }
        });
        Result<String> authResult = showOrderingClient.getIdentityAuth(employeeId, storeId);
        Map<String, Object> result = new HashMap<>(4);
        result.put("name", employeeResult.getData() == null ? "" : employeeResult.getData().getName());
        result.put("stores", storeMaps);
        result.put("auth", authResult.getData());
        return new Result<>(result);
    }
}
