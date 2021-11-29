package com.szhengzhu.config;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.token.Token;

/**
 * @author Jehon Zeng
 */
@Component
public class WechatConfig {

    private Date inTime;
    
    private Token token;
    
    private Ticket ticket;
    
    @Value("${wechat.appid}")
    private String appId;
    
    @Value("${wechat.secret}")
    private String secret;
    
    @Value("${wechat.mach_id}")
    private String machId;
    
    @Value("${wechat.key}")
    private String key;
    
    @Value("${wechat.noncestr}")
    private String nonceStr;

    public String getToken() {
        checkToken();
        return token.getAccess_token();
    }

    @PostConstruct
    public void init() {
        refreshToken();
    }

    public String refreshToken() {
        token = TokenAPI.token(appId, secret);
        inTime = new Date();
        ticket = TicketAPI.ticketGetticket(token.getAccess_token());
        return token.getAccess_token();
    }

    public String getTicket() {
        checkToken();
        return ticket.getTicket();
    }

    private void checkToken() {
        Date today = new Date();
        while (token == null || !token.isSuccess()
                || inTime.getTime() < today.getTime() - 7000 * 1000) {
            refreshToken();
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMachId() {
        return machId;
    }

    public void setMachId(String machId) {
        this.machId = machId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

}
