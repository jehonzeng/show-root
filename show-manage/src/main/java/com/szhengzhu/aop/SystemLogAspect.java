package com.szhengzhu.aop;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.szhengzhu.annotation.SystemLog;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.base.LogInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.LoginBase;
import com.szhengzhu.core.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author terry shi
 */
@Aspect
@Component
public class SystemLogAspect {

    private static final ThreadLocal<Date> BEGIN_TIME_THREADLOCAL = new NamedThreadLocal<>(
            "ThreadLocal BeginTime");

    private static final ThreadLocal<LogInfo> LOG_THREADLOCAL = new NamedThreadLocal<>(
            "ThreadLocal LogInfo");

    private static final ThreadLocal<UserInfo> CURRENT_USER_LOCAL = new NamedThreadLocal<>(
            "ThreadLocal UserInfo");

    @Autowired(required = false)
    private HttpServletRequest request;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @Pointcut("@annotation(com.szhengzhu.annotation.SystemLog)")
    public void logAspect() {
    }

    @Before("logAspect()")
    public void doBefore(JoinPoint joinPoint) {
        Date beginTime = new Date();
        // 记录开始时间
        BEGIN_TIME_THREADLOCAL.set(beginTime);
        //成功登录后
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        if (StrUtil.isEmpty(userId)) {
            // 未登录处理
            LoginBase login = (LoginBase) session.getAttribute("login_base");
            userId = login.getMarkId();
        }
        Result<UserInfo> userResult = showUserClient.getUserById(userId);
        // 记录用户信息
        if (userResult.isSuccess()) { CURRENT_USER_LOCAL.set(userResult.getData()); }
    }

    @After("logAspect()")
    public void doAfter(JoinPoint joinPoint) {
        UserInfo user = CURRENT_USER_LOCAL.get();
        if (ObjectUtil.isNotNull(user)) {
            String title = "";
            // 日志类型(info:入库,error:错误)
            String type = "info";
            // 请求的IP
            String remoteAddr = request.getRemoteAddr();
            // 请求的URI
            String requestUri = request.getRequestURI().substring(1, request.getRequestURI().length());
            // 请求的方法类型(POST/GET)
            String method = request.getMethod();
            // 请求提交的参数
            Map<String, String[]> params = request.getParameterMap();
            title = getControllerMethod(joinPoint);
            LogInfo log = getInstance(user, title, type, remoteAddr, requestUri, method, params,
                    BEGIN_TIME_THREADLOCAL.get());
            LOG_THREADLOCAL.set(log);
        }
    }

    /**
     * 创建对象
     *
     * @param user
     * @param title
     * @param type
     * @param remoteAddr
     * @param method
     * @param params
     * @param beginTime
     * @return
     * @date 2019年10月31日
     */
    private LogInfo getInstance(UserInfo user, String title, String type, String remoteAddr,
                                String requestUri, String method, Map<String, String[]> params, Date beginTime) {
        Date endTime = new Date();
        LogInfo log = new LogInfo();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        log.setMarkId(snowflake.nextIdStr());
        log.setTitle(title);
        log.setType(type);
        log.setRemoteAddr(remoteAddr);
        log.setRequestUri(requestUri);
        log.setMethod(method);
        log.setMapToParams(params);
        log.setUserId(user.getMarkId());
        Date operateDate = BEGIN_TIME_THREADLOCAL.get();
        log.setAddTime(operateDate);
        log.setTimeout(DateUtil.formatBetween(endTime, beginTime, BetweenFormatter.Level.SECOND));
        log.setMapToParams(params);
        log.setOperator(user.getRealName());
        return log;
    }

    private String getControllerMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        String description = systemLog.desc();
        return description;
    }

    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LogInfo log = LOG_THREADLOCAL.get();
        if(ObjectUtil.isNotNull(log)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            log.setMarkId(snowflake.nextIdStr());
            log.setType("error");
            log.setException(e.toString());
            threadPoolTaskExecutor.execute(new SaveLog(log, showBaseClient));
            moveThreadLocal();
        }
    }

    @AfterReturning(returning="result",pointcut = "logAspect()")
    public void doAfterReturn(Object result) {
        LogInfo log = LOG_THREADLOCAL.get();
        if(ObjectUtil.isNotNull(log)) {
            log.setResultParams(new Gson().toJson(result));
            threadPoolTaskExecutor.execute(new SaveLog(log, showBaseClient));
        }
        // 统一删除
        moveThreadLocal();
        
    }

    private void moveThreadLocal() {
        BEGIN_TIME_THREADLOCAL.remove();
        CURRENT_USER_LOCAL.remove();
        LOG_THREADLOCAL.remove();
    }

    /**
     * 保存日志
     */
    private static class SaveLog implements Runnable {

        private LogInfo log;

        private ShowBaseClient showBaseClient;

        public SaveLog(LogInfo log, ShowBaseClient showBaseClient) {
            this.log = log;
            this.showBaseClient = showBaseClient;
        }

        @Override
        public void run() {
            showBaseClient.createLog(log);
        }
    }

}
