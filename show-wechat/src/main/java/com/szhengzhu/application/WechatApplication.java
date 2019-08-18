package com.szhengzhu.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "com.szhengzhu.client" })
@SpringBootApplication(scanBasePackages = { "com.szhengzhu.application", "com.szhengzhu.rabbitmq",
        "com.szhengzhu.controller", "com.szhengzhu.aop", "com.szhengzhu.exception" })
public class WechatApplication {
    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }
}
