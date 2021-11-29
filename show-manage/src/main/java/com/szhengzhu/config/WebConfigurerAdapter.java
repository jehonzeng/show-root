package com.szhengzhu.config;

import com.szhengzhu.aop.ManageTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ManageTokenInterceptor interceptor = new ManageTokenInterceptor();
        registry.addInterceptor(interceptor).addPathPatterns("/v1/**/*");
        registry.addInterceptor(interceptor).addPathPatterns("/v1/**/*.do");
    }
}
