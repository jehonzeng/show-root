package com.szhengzhu;

import com.szhengzhu.context.HandlerProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Jehon Zeng
 */
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class WechatApplication {

    @Bean
    public HandlerProcessor getProcessor() {
        return new HandlerProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }
}
