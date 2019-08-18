package com.szhengzhu.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.StringUtils;

public class ManageTokenInterceptor implements HandlerInterceptor {

    @Resource
    private ShowUserClient showUserClient;
    
    @Resource
    private Sender sender;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Show-Token");
        Result<UserToken> userResult = new Result<>();
        BeanFactory factory = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        if (!StringUtils.isEmpty(token)) {
            if (showUserClient == null) // 解决service为null无法注入问题
                showUserClient = factory.getBean(ShowUserClient.class);
            userResult = showUserClient.getUserToken(token);
        }
        boolean flag = token == null || !userResult.isSuccess()
                || (System.currentTimeMillis() - userResult.getData().getRefreshTime().getTime() > 2 * 60 * 60 * 1000);
        if (flag) {
            response.setStatus(HttpStatus.OK.value()); // 设置状态码
            response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
            response.setCharacterEncoding("UTF-8"); // 避免乱码
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            if (token == null || !userResult.isSuccess())
                response.getWriter().write(new Gson().toJson(new Result<>(StatusCode._4005)));
            else
                response.getWriter().write(new Gson().toJson(new Result<>(StatusCode._4013)));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        } else {
            if (System.currentTimeMillis() - userResult.getData().getRefreshTime().getTime() > 90 * 60 * 1000) {
                sender = factory.getBean(Sender.class);
                sender.refreshToken(token);  // 刷新token时间
            }
        }
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
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
