package com.szhengzhu;

import com.szhengzhu.context.HandlerProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 */
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableScheduling
public class ManageApplication {

    @Bean
    public HandlerProcessor getProcessor() {
        return new HandlerProcessor();
    }
  
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
