package com.szhengzhu.rabbitmq;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.service.GoodsStockService;
import com.szhengzhu.service.GoodsVoucherService;
import com.szhengzhu.service.MealService;
import com.szhengzhu.service.ShoppingCartService;

/**
 * 信息消费者
 * 
 * @author Administrator
 * @date 2019年2月21日
 */
@Component
public class Recevied {

    @Autowired
    private GoodsStockService goodsStockService;

    @Autowired
    private GoodsVoucherService voucherService;

    @Autowired
    private MealService mealService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @SuppressWarnings("unchecked")
    @RabbitHandler
    @RabbitListener(queues = "product-stock", containerFactory = "containerFactory")
    public void productStock(Map<String, Object> mqParam, Message msg, Channel channel) throws IOException {
        onMessage(msg, channel);
        int corePoolSize = 10;
        int maximumPoolSize = Integer.MAX_VALUE;
        long keepActiveTime = 200;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(15);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,
                workQueue);
        String addressId = (String) mqParam.get("addressId");
        System.out.println("product-stock");
        List<OrderItem> itemList = (List<OrderItem>) mqParam.get("itemList");
        for (int index = 0, size = itemList.size(); index < size; index++) {
            OrderItem item = itemList.get(index);
            int productType = item.getProductType();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    if (productType == 0)
                        System.out.println();
                    else if (productType == 1)
                        System.out.println();
                    else if (productType == 2)
                        System.out.println();
                }
            });
        }
        threadPool.shutdown();
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
