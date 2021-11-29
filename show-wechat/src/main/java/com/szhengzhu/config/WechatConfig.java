package com.szhengzhu.config;

import com.szhengzhu.redis.Redis;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.token.Token;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Jehon Zeng
 */
@Data
@Component
public class WechatConfig {

    @Resource
    private Redis redis;

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
    
    @Value("${wechat.wechat_server}")
    private String wechatServer;
    
    @Value("${wechat.wechat_pay_back}")
    private String wechatPayBack;
    
    @Value("${wechat.web_pay_back}")
    private String webPayBack;
    
    @Value("${wechat.xappid}")
    private String xappid;
    
    @Value("${wechat.xuserpage}")
    private String xuserPage;
    
    @Value("${wechat.xtablepage}")
    private String xtablePage;

    @Value("${wechat.xmatchpage}")
    private String xmatchPage;

    private String tokenCacheKey = "wechat:token";

    private String ticketCacheKey = "wechat:ticket";

    private String accessToken;

    private String accessTicket;

    /** 过期时间 */
    private int expiresIn = 7000;

    @PostConstruct
    public void init() {
        refreshToken();
    }

    public String getToken() {
        checkToken();
        accessToken = (String) redis.get(tokenCacheKey);
        return accessToken;
    }

    private void checkToken() {
        accessToken = (String) redis.get(tokenCacheKey);
        if (accessToken == null) {
            refreshToken();
        }
    }

    public String refreshToken() {
        token = TokenAPI.token(appId, secret);
        ticket = TicketAPI.ticketGetticket(token.getAccess_token());
        redis.set(tokenCacheKey, token.getAccess_token(), expiresIn);
        redis.set(ticketCacheKey, ticket.getTicket(), expiresIn);
        return token.getAccess_token();
    }

    public String getTicket() {
        checkToken();
        accessTicket = (String) redis.get(ticketCacheKey);
        return accessTicket;
    }

//    public String getToken() {
//        checkToken();
//        return token.getAccess_token();
//    }
//
//    public String refreshToken() {
//        token = TokenAPI.token(appId, secret);
//        inTime = new Date();
//        ticket = TicketAPI.ticketGetticket(token.getAccess_token());
//        return token.getAccess_token();
//    }
//
//    public String getTicket() {
//        checkToken();
//        return ticket.getTicket();
//    }
//
//    private void checkToken() {
//        Date today = new Date();
//        while (token == null || !token.isSuccess()
//                || inTime.getTime() < today.getTime() - 7000 * 1000) {
//            refreshToken();
//        }
//    }
}
