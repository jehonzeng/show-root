package com.szhengzhu.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.szhengzhu.aop.ManageTokenInterceptor;

@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ManageTokenInterceptor interceptor = new ManageTokenInterceptor();
        registry.addInterceptor(interceptor).addPathPatterns("/v1/**/*");
        registry.addInterceptor(interceptor).addPathPatterns("/v1/**/*.do");
        super.addInterceptors(registry);
    }

}
