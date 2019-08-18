package com.szhengzhu.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;

public class ManageTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String user = (String) req.getSession().getAttribute(Commons.SESSION);
        if (user == null) {
            Result<String> result = new Result<String>(StatusCode._4005);
            res.setStatus(HttpStatus.UNAUTHORIZED.value()); // 设置状态码
            res.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
            res.setCharacterEncoding("UTF-8"); // 避免乱码
            res.setHeader("Cache-Control", "no-cache, must-revalidate");
            res.getWriter().write(new Gson().toJson(result));
            res.getWriter().flush();
            res.getWriter().close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
