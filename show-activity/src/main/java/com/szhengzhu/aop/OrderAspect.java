package com.szhengzhu.aop;

import com.szhengzhu.bean.activity.SceneItem;
import com.szhengzhu.bean.activity.SceneOrder;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.mapper.SceneGoodsMapper;
import com.szhengzhu.mapper.SceneItemMapper;
import com.szhengzhu.mapper.SceneOrderMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单异步操作类
 * 
 * @author Jehon Zeng
 */
@Aspect
@Component
public class OrderAspect {
    
    @Resource
    private SceneOrderMapper sceneOrderMapper;
    
    @Resource
    private SceneItemMapper sceneItemMapper;
    
    @Resource
    private SceneGoodsMapper sceneGoodsMapper;
    
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Before(value = "@annotation(com.szhengzhu.annotation.CheckOrderCancel) && within (com.szhengzhu.service.impl.SeckillServiceImpl)")
    public void orderAllCancel() {
        List<SceneOrder> orderList = sceneOrderMapper.selectExpiredOrder();
        for (SceneOrder order : orderList) {
            sceneOrderMapper.updateStatus(order.getOrderNo(), OrderStatus.CANCELLED);
            List<SceneItem> itemList = sceneItemMapper.selectByOrderId(order.getMarkId());
            for (SceneItem item: itemList) {
                String goodsStockKey = "activity:scene:goods:stock:" + item.getGoodsId();
                redisTemplate.opsForList().leftPush(goodsStockKey, 1);
                sceneGoodsMapper.addStock(item.getGoodsId(), item.getQuantity());
            }
        }
    }
}
