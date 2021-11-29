package com.szhengzhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Jehon Zeng
 */
@EnableSwagger2
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class XwechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwechatApplication.class, args);
    }

    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列大小
        executor.setQueueCapacity(1000);
        // 线程最大空闲时间
        executor.setKeepAliveSeconds(300);
        // 指定用于新创建的线程名称的前缀。
        executor.setThreadNamePrefix("fsx-Executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
