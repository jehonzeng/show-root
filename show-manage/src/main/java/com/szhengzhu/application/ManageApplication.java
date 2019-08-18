package com.szhengzhu.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "com.szhengzhu.client" })
@SpringBootApplication(scanBasePackages = {"com.szhengzhu.controller",
        "com.szhengzhu.service", "com.szhengzhu.application", "com.szhengzhu.aop",
        "com.szhengzhu.exception", "com.szhengzhu.schedule", "com.szhengzhu.rabbitmq" })
@EnableScheduling
public class ManageApplication {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                // 设置session时间2小时
                container.setSessionTimeout(7200);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
