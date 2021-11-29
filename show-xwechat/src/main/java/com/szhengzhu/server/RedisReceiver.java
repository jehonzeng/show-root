package com.szhengzhu.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szhengzhu.bean.CartServerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class RedisReceiver {

    public void receiveCartMessage(String message) {
//        log.info(message);
        message = message.substring(1, message.length() - 1).replace("\\", "");
        // 在正则表达式中"\\\\"表示一个"\"
        JSONObject json = JSON.parseObject(message);
        CartServerData data = JSON.toJavaObject(json, CartServerData.class);
        CartWsServer.sendTableCartMsg(data);
    }
}
