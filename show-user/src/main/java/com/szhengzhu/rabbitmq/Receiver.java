package com.szhengzhu.rabbitmq;

import cn.hutool.core.util.ObjectUtil;
import com.rabbitmq.client.Channel;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.mapper.UserInfoMapper;
import com.szhengzhu.mapper.UserTokenMapper;
import com.szhengzhu.mapper.WechatInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 监听消息
 * 
 * @author Jehon Zeng
 */
@Slf4j
@Component
public class Receiver {

    @Resource
    private UserTokenMapper userTokenMapper;
    
    @Resource
    private UserInfoMapper userInfoMapper;
    
    @Resource
    private WechatInfoMapper wechatInfoMapper;
    
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.REFRESH_TOKEN, containerFactory = "containerFactory")
    public void refreshToken(String token, Message msg, Channel channel) throws IOException {
        try {
            String cacheKey = "user:user:token:" + token;
            userTokenMapper.refreshByToken(token);
            redisTemplate.delete(cacheKey);
            UserToken userToken = (UserToken) redisTemplate.opsForValue().get(cacheKey);
            if (ObjectUtil.isNull(userToken)) {
                userToken = userTokenMapper.selectByToken(token);
                redisTemplate.opsForValue().set(cacheKey, userToken);
                redisTemplate.expire(cacheKey, 2, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }
    
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.MODIFY_WECHAT_STATUS, containerFactory = "containerFactory")
    public void modifyWechatStatus(Map<String, Object> map, Message msg, Channel channel) throws IOException {
        try {
            String openId = (String) map.get("wopenId");
            int wechatStatus = (int) map.get("wechatStatus");
            log.info("wechat-status:{}", openId);
            userInfoMapper.updateWechatStatusByOpenId(openId, wechatStatus);
            wechatInfoMapper.updateWechatStatusByOpenId(openId, wechatStatus);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
