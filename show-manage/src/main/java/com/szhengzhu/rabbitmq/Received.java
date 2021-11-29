package com.szhengzhu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.szhengzhu.context.OrderContext;
import com.szhengzhu.context.ProductContext;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.handler.AbstractProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 信息消费者
 * 
 * @author Administrator
 * @date 2019年2月21日
 */
@Slf4j
@Component
public class Received {
    
    @Resource
    private OrderContext orderContext;
    
    @Resource
    private ProductContext productContext;

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.ADD_CURRENT_STOCK, containerFactory = "containerFactory")
    public void addCurrentStock(String orderNo, Message msg, Channel channel) throws IOException {
        try {
            log.info("add-current-stock:{}", orderNo);
            String type = Character.toString(orderNo.charAt(0));
            AbstractOrder handler = orderContext.getInstance(type);
            handler.addCurrentStock(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    /**
     * 商城用户下单减库存
     * 
     * @date 2019年8月8日 上午9:53:43
     * @param orderNo
     * @param msg
     * @param channel
     * @throws IOException 
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SUB_CURRENT_STOCK, containerFactory = "containerFactory")
    public void subCurrentStock(String orderNo, Message msg, Channel channel) throws IOException {
        try {
            log.info("sub-current-stock:" + orderNo);
            String type = Character.toString(orderNo.charAt(0));
            AbstractOrder handler = orderContext.getInstance(type);
            handler.subCurrentStock(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    /**
     * 用户下单支付成功修改真实库存
     * 
     * @date 2019年8月8日 上午9:54:10
     * @param orderNo
     * @param msg
     * @param channel
     * @throws IOException 
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SUB_TOTAL_STOCK, containerFactory = "containerFactory")
    public void subTotalStock(String orderNo, Message msg, Channel channel) throws IOException {
        try {
            log.info("sub-total-stock:" + orderNo);
            String type = Character.toString(orderNo.charAt(0));
            AbstractOrder handler = orderContext.getInstance(type);
            handler.subTotalStock(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    /**
     * 用户退款成功后修改库存
     * 
     * @date 2019年8月8日 上午10:31:16
     * @param orderNo
     * @param msg
     * @param channel
     * @throws IOException 
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.ADD_TOTAL_STOCK, containerFactory = "containerFactory")
    public void addTotalStock(String orderNo, Message msg, Channel channel) throws IOException {
        try {
            log.info("add-total-stock:" + orderNo);
            String type = Character.toString(orderNo.charAt(0));
            AbstractOrder handler = orderContext.getInstance(type);
            handler.addTotalStock(orderNo);
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
    @RabbitListener(queues = RabbitQueue.SCAN_WINNER, containerFactory = "containerFactory")
    public void scanWinner(Map<String, String> map, Message msg, Channel channel) throws IOException {
        try {
            String openId = map.get("openId");
            String productType = map.get("productType");
            String productId = map.get("productId");
            AbstractProduct handler = productContext.getInstance(productType);
            handler.scanWinner(productId, openId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }
}
