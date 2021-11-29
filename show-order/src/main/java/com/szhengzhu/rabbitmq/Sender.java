package com.szhengzhu.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 信息生产
 *
 * @author Administrator
 * @date 2019年2月21日
 */
@Slf4j
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

    /**
     * 用户下单成功修改商品真实库存
     *
     * @param orderNo
     * @date 2019年8月8日 上午10:00:09
     */
    public void subTotalStock(String orderNo) {
        rabbitTemplate.convertAndSend(RabbitQueue.SUB_TOTAL_STOCK, orderNo);
    }

    /**
     * 取消订单恢复库存
     *
     * @param orderNo
     * @date 2019年8月6日 下午3:37:39
     */
    public void addCurrentStock(String orderNo) {
        rabbitTemplate.convertAndSend(RabbitQueue.ADD_CURRENT_STOCK, orderNo);
    }

    /**
     * 商城用户下单减库存
     *
     * @param orderNo
     * @date 2019年8月8日 上午9:54:39
     */
    public void subCurrentStock(String orderNo) {
        rabbitTemplate.convertAndSend(RabbitQueue.SUB_CURRENT_STOCK, orderNo);
    }

    /**
     * 用户下单支付成功，系统自动给管理员发送下单信息
     *
     * @date 2019年8月26日 下午2:52:57
     * @param orderNo
     */
//    public void sendManageMessage(String orderNo) {
//        log.info("send:send-manage-message:{}", orderNo);
//        rabbitTemplate.convertAndSend(RabbitQueue.SEND_MANAGE_MESSAGE, orderNo);
//    }
//
//    public void subSeckillStock(Map<String, String> map) {
//        rabbitTemplate.convertAndSend("sub-seckill-stock", map);
//    }
//
//    public void addSeckillStock(Map<String, String> map) {
//        rabbitTemplate.convertAndSend("add-seckill-stock", map);
//    }
//
//    public void subTeambuyStock(Map<String, String> map) {
//        rabbitTemplate.convertAndSend("sub-teambuy-stock", map);
//    }
//
//    public void addTeambuyStock(Map<String, String> map) {
//        rabbitTemplate.convertAndSend("add-teambuy-stock", map);
//    }
//
//    public void subGoodsCurrentStock(Map<String, String> map) {
//        rabbitTemplate.convertAndSend("sub-current-stock", map);
//    }
//
//    public void addGoodsCurrentStock(Map<String, String> map) {
//        rabbitTemplate.convertAndSend("add-current-stock", map);
//    }
}
