package com.szhengzhu.rabbitmq;

import com.szhengzhu.bean.activity.ParticipantRelation;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Sender {

    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @Autowired
    private MappingJackson2MessageConverter converter;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(converter);
    }

    public void sendCoupon(String templateId, String userId) {
        templateId += "&&" + userId;
        rabbitTemplate.convertAndSend(RabbitQueue.SEND_COUPON, templateId);
    }
    
    public void receiveAuto(ParticipantRelation base) {
        rabbitTemplate.convertAndSend(RabbitQueue.RECEIVE_GIFT, base);
    }
    
    public void sendVoucher(String voucherId, String userId) {
        voucherId += "&&" + userId;
        rabbitTemplate.convertAndSend(RabbitQueue.SEND_VOUCHER, voucherId);
    }
    
    public void subSceneGoodsStock(String goodsId, int quantity) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("quantity", quantity);
        rabbitTemplate.convertAndSend(RabbitQueue.SUB_SCENE_GOOD_STOCK, map);
    }
    
    public void receiveGoods(List<String> itemIdList) {
        rabbitTemplate.convertAndSend(RabbitQueue.RECEIVE_GOODS, itemIdList);
    }
    
    public void scanWinner(String openId, String productType, String productId) {
        Map<String, String> map = new HashMap<>(8);
        map.put("openId", openId);
        map.put("productType", productType);
        map.put("productId", productId);
        rabbitTemplate.convertAndSend(RabbitQueue.SCAN_WINNER, map);
    }
}
