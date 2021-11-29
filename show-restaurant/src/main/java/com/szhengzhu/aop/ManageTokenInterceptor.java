package com.szhengzhu.aop;

import com.google.gson.Gson;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String user = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        if (user == null) {
            Result<String> result = new Result<>(StatusCode._4005);
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
            ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
