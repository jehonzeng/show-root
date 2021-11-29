package com.szhengzhu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.szhengzhu.bean.activity.SceneGoods;
import com.szhengzhu.bean.activity.SceneItem;
import com.szhengzhu.mapper.SceneGoodsMapper;
import com.szhengzhu.mapper.SceneItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
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
    private SceneGoodsMapper sceneGoodsMapper;
    
    @Resource
    private SceneItemMapper sceneItemMapper;
    
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    
    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.SUB_SCENE_GOOD_STOCK, containerFactory = "containerFactory")
    public void subSceneGoodsStock(Map<String, Object> map, Message msg, Channel channel) throws IOException {
        try {
            String goodsId = (String) map.get("goodsId");
            int quantity = (int) map.get("quantity");
            sceneGoodsMapper.subStock(goodsId, quantity);
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
    @RabbitListener(queues = RabbitQueue.RECEIVE_GOODS, containerFactory = "containerFactory")
    public void receiveGoods(List<String> itemIdList, Message msg, Channel channel) throws IOException {
        try {
            for (String markId : itemIdList) {
                SceneItem item = sceneItemMapper.selectByPrimaryKey(markId);
                sceneGoodsMapper.receive(item.getGoodsId(), item.getQuantity());
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
    @RabbitListener(queues = RabbitQueue.ADD_SCENE_GOODS_STOCK, containerFactory = "containerFactory")
    public void addSceneGoodsStock(String orderId, Message msg, Channel channel) throws IOException {
        try {
            List<SceneItem> itemList = sceneItemMapper.selectByOrderId(orderId);
            for (SceneItem item : itemList) {
                String goodsId = item.getGoodsId();
                String goodsKey = "activity:scene:goods:" + goodsId;
                SceneGoods goods = (SceneGoods) redisTemplate.opsForValue().get(goodsKey);
                goods.setStockSize(goods.getStockSize() + item.getQuantity());
                redisTemplate.opsForValue().set(goodsKey, goods);
                for (int i = 0; i < item.getQuantity(); i++) {
                    redisTemplate.opsForList().leftPush("activity:scene:stock:" + goodsId, 1);
                }
                sceneGoodsMapper.addStock(goodsId, item.getQuantity());
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
    
    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }

}
