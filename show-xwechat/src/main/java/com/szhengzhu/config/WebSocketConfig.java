package com.szhengzhu.config;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.server.CartWsServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    
    @Resource
    public void setShowUserClient(ShowUserClient showUserClient) {
        CartWsServer.showUserClient = showUserClient;
    }
    
    @Resource
    public void setShowOrderingClient(ShowOrderingClient showOrderingClient) {
        CartWsServer.showOrderingClient = showOrderingClient;
    }
}
