package com.szhengzhu.config;

import feign.Feign;
import okhttp3.ConnectionPool;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
            okhttp3.OkHttpClient.Builder ClientBuilder = new okhttp3.OkHttpClient.Builder()
                    //读取超时
                    .readTimeout(30, TimeUnit.SECONDS)
                    //连接超时
                    .connectTimeout(10, TimeUnit.SECONDS)
                    //写入超时
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(10, 3, TimeUnit.MINUTES));
            return ClientBuilder.build();
    }
}
