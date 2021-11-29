package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckOrderCancel;
import com.szhengzhu.bean.activity.SceneGoods;
import com.szhengzhu.bean.activity.SceneInfo;
import com.szhengzhu.bean.activity.SceneItem;
import com.szhengzhu.bean.activity.SceneOrder;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.SceneGoodsMapper;
import com.szhengzhu.mapper.SceneInfoMapper;
import com.szhengzhu.mapper.SceneItemMapper;
import com.szhengzhu.mapper.SceneOrderMapper;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.SceneService;
import com.szhengzhu.util.ShowUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service("sceneService")
public class SceneServiceImpl implements SceneService {

    @Resource
    private SceneInfoMapper sceneInfoMapper;

    @Resource
    private SceneGoodsMapper sceneGoodsMapper;

    @Resource
    private SceneOrderMapper sceneOrderMapper;

    @Resource
    private SceneItemMapper sceneItemMapper;

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Override
    public PageGrid<SceneInfo> pageScene(PageParam<SceneInfo> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        PageInfo<SceneInfo> pageInfo = new PageInfo<>(sceneInfoMapper.selectByExampleSelective(page.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public SceneInfo getSceneInfo(String sceneId) {
        return sceneInfoMapper.selectByPrimaryKey(sceneId);
    }

    @Override
    public void addScene(SceneInfo sceneInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        sceneInfo.setMarkId(snowflake.nextIdStr());
        sceneInfo.setCreateTime(DateUtil.date());
        sceneInfoMapper.insertSelective(sceneInfo);
    }

    @Override
    public void modifyScene(SceneInfo sceneInfo) {
        sceneInfoMapper.updateByPrimaryKeySelective(sceneInfo);
        List<SceneGoods> goodsList = sceneGoodsMapper.selectByScenId(sceneInfo.getMarkId());
        String sceneKey = "activity:scene:info:" + sceneInfo.getMarkId();
        long expire = sceneInfo.getStopTime().getTime() - System.currentTimeMillis();
        redis.del(sceneKey);
        for (SceneGoods sceneGoods : goodsList) {
            redis.del("activity:scene:goods:info:" + sceneGoods.getMarkId());
            redis.del("activity:scene:goods:stock:" + sceneGoods.getMarkId());
        }
        if (Boolean.FALSE.equals(sceneInfo.getServerStatus()) || expire < 1) {
            return;
        }
        redis.set(sceneKey, sceneInfo, expire / 1000);
        goodsList = goodsList.stream().filter(goods -> Boolean.TRUE.equals(goods.getServerStatus())).collect(Collectors.toList());
        for (SceneGoods goods : goodsList) {
            BigDecimal salePrice = goods.getSalePrice();
            if (sceneInfo.getPromotionType() == 0) {
                salePrice = salePrice.subtract(sceneInfo.getDiscount());
            } else if (sceneInfo.getPromotionType() == 1) {
                salePrice = salePrice.multiply(sceneInfo.getDiscount());
            }
            goods.setBasePrice(goods.getSalePrice());
            goods.setSalePrice(salePrice);
            goods.setReceiveSize(null);
            String goodsKey = "activity:scene:goods:info:" + goods.getMarkId();
            String goodsStockKey = "activity:scene:goods:stock" + goods.getMarkId();
            for (int i = 0; i < goods.getStockSize(); i++) {
                redis.lSet(goodsStockKey, 1);
            }
            redis.set(goodsKey, goods, expire / 1000);
            redis.expire(goodsStockKey, expire / 1000);
        }
    }

    @Override
    public PageGrid<SceneGoods> pageGoods(PageParam<SceneGoods> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        PageInfo<SceneGoods> pageInfo = new PageInfo<>(sceneGoodsMapper.selectByExampleSelective(page.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public SceneGoods getGoodsInfo(String goodsId) {
        return sceneGoodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public void addGoods(SceneGoods goods) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        goods.setMarkId(snowflake.nextIdStr());
        goods.setReceiveSize(0);
        goods.setServerStatus(false);
        sceneGoodsMapper.insertSelective(goods);
    }

    @Override
    public void modifyGoods(SceneGoods goods) {
        sceneGoodsMapper.updateByPrimaryKeySelective(goods);
        // 删除已存在信息
        String goodsKey = "activity:scene:goods:info:" + goods.getMarkId();
        String goodsStockKey = "activity:scene:goods:stock" + goods.getMarkId();
        redis.del(goodsKey);
        redis.del(goodsStockKey);
        String sceneKey = "activity:scene:info:" + goods.getSceneId();
        SceneInfo sceneInfo = (SceneInfo) redis.get(sceneKey);
        if (Boolean.FALSE.equals(goods.getServerStatus()) || sceneInfo == null || Boolean.FALSE.equals(sceneInfo.getServerStatus())) {
            return;
        }
        long expire = sceneInfo.getStopTime().getTime() - System.currentTimeMillis();
        if (expire < 1) {
            return;
        }
        BigDecimal salePrice = goods.getSalePrice();
        if (sceneInfo.getPromotionType() == 0) {
            salePrice = salePrice.subtract(sceneInfo.getDiscount());
        } else if (sceneInfo.getPromotionType() == 1) {
            salePrice = salePrice.multiply(sceneInfo.getDiscount());
        }
        goods.setBasePrice(goods.getSalePrice());
        goods.setSalePrice(salePrice);
        goods.setReceiveSize(null);
        redis.set(goodsKey, goods);
        for (int i = 0; i < goods.getStockSize(); i++) {
            redis.lSet(goodsStockKey, 1);
        }
        redis.expire(goodsKey, expire / 1000);
        redis.expire(goodsStockKey, expire / 1000);
    }

    @CheckOrderCancel
    @Override
    public PageGrid<SceneOrder> pageOrder(PageParam<SceneOrder> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        List<SceneOrder> orderList = sceneOrderMapper.selectByExampleSelective(page.getData());
        for (SceneOrder order : orderList) {
            List<SceneItem> itemList = sceneItemMapper.selectByOrderId(order.getMarkId());
            order.setItemList(itemList);
        }
        PageInfo<SceneOrder> pageInfo = new PageInfo<>(orderList);
        return new PageGrid<>(pageInfo);
    }

    @CheckOrderCancel
    @Override
    public List<SceneGoods> listForeGoods() {
        List<SceneGoods> goodsList = new LinkedList<>();
        Set<String> cacheKeys = redis.keys("activity:scene:goods:info:*");
        if (cacheKeys != null) {
            for (String key : cacheKeys) {
                SceneGoods goods = (SceneGoods) redis.get(key);
                SceneInfo sceneInfo = (SceneInfo) redis.get("activity:scene:info:" + goods.getSceneId());
                if (sceneInfo.getStartTime().getTime() <= System.currentTimeMillis() && goods.getStockSize() > 0) {
                    goodsList.add(goods);
                }
            }
        }
        return goodsList;
    }

    @CheckOrderCancel
    @Override
    public Map<String, Object> createOrder(List<String> goodsIdList, String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<SceneGoods> goodsList = new LinkedList<>();
        String orderNo = ShowUtils.createOrderNo(Contacts.TYPE_OF_SCENE_ORDER, 1);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String orderId = snowflake.nextIdStr();
        List<SceneItem> itemList = new LinkedList<>();
        BigDecimal total = new BigDecimal("0.00");
        ReentrantLock lock = new ReentrantLock();
        int quantity = 1;
        for (String goodsId : goodsIdList) {
            String goodsKey = "activity:scene:goods:info:" + goodsId;
            String stockKey = "activity:scene:goods:stock:" + goodsId;
            SceneGoods goods = (SceneGoods) redis.get(goodsKey);
            lock.lock();
            try {
                Object value = redis.lPop(stockKey);
                if (value == null) {
                    continue;
                }
                goods.setStockSize(goods.getStockSize() - 1);
                redis.set(goodsKey, goods);
            } finally {
                lock.unlock();
            }
            total = total.add(goods.getSalePrice());
            goodsList.add(goods);
            sender.subSceneGoodsStock(goodsId, quantity);
            SceneItem item = SceneItem.builder().markId(snowflake.nextIdStr())
                    .orderId(orderId).goodsId(goodsId).goodsName(goods.getGoodsName()).quantity(quantity)
                    .serverStatus(false).build();
            itemList.add(item);
        }
        if (!goodsList.isEmpty()) {
            SceneOrder order = SceneOrder.builder().markId(orderId)
                    .orderNo(orderNo).userId(userId)
                    .orderTime(DateUtil.date()).payAmount(total)
                    .orderStatus(OrderStatus.NO_PAY).build();
            sceneOrderMapper.insertSelective(order);
            sceneItemMapper.insertBatch(itemList);
        }
        resultMap.put("orderNo", orderNo);
        resultMap.put("total", total);
        resultMap.put("goodsList", goodsList);
        return resultMap;
    }

    @CheckOrderCancel
    @Override
    public List<SceneItem> listUnReceiveGoods(String userId) {
        return sceneItemMapper.selectUnReceiveGoods(userId);
    }

    @CheckOrderCancel
    @Override
    public List<SceneItem> listReceiveGoods(String userId) {
        return sceneItemMapper.selectReceiveGoods(userId);
    }

    @Override
    public SceneOrder getOrderInfo(String orderNo) {
        SceneOrder order = sceneOrderMapper.selectByNo(orderNo, null);
        ShowAssert.checkNull(order, StatusCode._4014);
        return order;
    }

    @Override
    public void modifyOrderStatus(String orderNo, String orderStatus) {
        sceneOrderMapper.updateStatus(orderNo, orderStatus);
    }

    @Override
    public List<String> receiveGoods(List<String> idsList, String userId) {
        List<String> list = new LinkedList<>();
        for (String itemId : idsList) {
            int count = sceneItemMapper.selectCountById(itemId, userId);
            if (count == 0) {
                continue;
            }
            sceneItemMapper.receiveGoods(itemId);
            list.add(itemId);
        }
        if (!list.isEmpty()) {
            sender.receiveGoods(list);
        }
        return list;
    }
}
