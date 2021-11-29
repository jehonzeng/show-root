package com.szhengzhu.aop;

import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.redis.Redis;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Slf4j
@Aspect
@Component
public class NoRepeatSubmitAop {

    @Resource
    private Redis redis;

    @Around("execution(* com.szhengzhu..*Controller.*(..)) && @annotation(nrs)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String user = (String) request.getSession().getAttribute(Contacts.RESTAURANT_USER);
            String key = user + "-" + request.getServletPath();
            log.info("{} {}", key, request.getMethod());
            // 如果缓存中有这个url视为重复提交
            if (redis.get(key) == null) {
                Object o = pjp.proceed();
                redis.set(key, 0, 3);
                return o;
            } else {
                log.info("重复提交接口：{}", key);
                return new Result<String>(StatusCode._5000);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("验证重复提交时出现未知异常!");
            return new Result<String>(StatusCode._5000);
        }

    }
}
