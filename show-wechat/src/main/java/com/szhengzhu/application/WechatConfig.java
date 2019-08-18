package com.szhengzhu.application;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.token.Token;

@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private Date inTime;
    private Token token;
    private Ticket ticket;
    private String appid;
    private String secret;
    private String mach_id;
    private String key;
    private String noncestr;
    private String pay_back;

    public String getToken() {
        checkToken();
        return token.getAccess_token();
    }

    @PostConstruct
    public void init() {
        reflashToken();
    }

    public void reflashToken() {
        token = TokenAPI.token(appid, secret);
        inTime = new Date();
        ticket = TicketAPI.ticketGetticket(token.getAccess_token());
    }

    public String getTicket() {
        checkToken();
        return ticket.getTicket();
    }

    private void checkToken() {
        Date today = new Date();
        while (token == null || !token.isSuccess()
                || inTime.getTime() < today.getTime() - 7000 * 1000) {
            reflashToken();
        }
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMach_id() {
        return mach_id;
    }

    public void setMach_id(String mach_id) {
        this.mach_id = mach_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPay_back() {
        return pay_back;
    }

    public void setPay_back(String pay_back) {
        this.pay_back = pay_back;
    }
}
