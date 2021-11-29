package com.szhengzhu.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import com.szhengzhu.core.ReceiveInfo;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private PendDishesMapper pendDishesMapper;

    @Resource
    private ReceiveDishesMapper receiveDishesMapper;

    @Resource
    private DishesStageMapper dishesStageMapper;

    @Resource
    private ReceiveRecordMapper receiveRecordMapper;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String redisKey = message.toString();
        String dateTime = DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss");
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        switch (redisKey.split("_")[0]) {
            case "pend":
                String pendId = redisKey.split("_")[1];
                log.info("过期key：" + redisKey + ",过期时间：" + dateTime);
                //根据主键查询待领取信息
                PendDishes pendDishes = pendDishesMapper.queryById(pendId);
                if (ObjectUtil.isEmpty(pendDishes)) {
                    return;
                }
                if (pendDishes.getStatus() == 1) {
                    if (pendDishes.getDays() == 7) {
                        //推送菜苗快要过期的信息给用户
                        sender.expiring(pendDishes.getUserId(), null);
                        log.info("key：pend_" + pendId + ",录入时间：" + dateTime);
                        //3 * 24 * 60 * 60
//                        DateUtil.second()
                        redis.set("pend_" + pendId, pendDishes, 120);
                    } else {
                        //添加领取菜品的记录
                        receiveRecordMapper.insert(ReceiveRecord.builder().markId(snowflake.nextIdStr()).userId(pendDishes.getUserId())
                                .description(ReceiveInfo.receiveExpire()).createTime(DateUtil.date()).build());
                        //更新待领取信息（状态改为已失效）
                        pendDishesMapper.update(PendDishes.builder().markId(pendId).status(0).modifyTime(DateUtil.date()).build());
                    }
                }
                break;
            case "receive":
                String receiveId = redisKey.split("_")[1];
                log.info("过期key：" + redisKey + ",过期时间：" + dateTime);
                //根据主键查询领取菜品详细信息
                ReceiveVo receiveVo = receiveDishesMapper.queryById(receiveId);
                if (ObjectUtil.isEmpty(receiveVo)) {
                    return;
                }
                if (receiveVo.getStatus() == 1) {
                    //查询菜品阶段加一的菜品阶段信息
                    DishesStage stage = dishesStageMapper.selectByStage(receiveVo.getDishesId(), receiveVo.getDishesStage().getSort() + 1);
                    if (ObjectUtil.isNotEmpty(stage)) {
                        //更新领取菜品记录表（菜品阶段）
                        receiveDishesMapper.update(ReceiveDishes.builder().markId(receiveId).stageId(stage.getMarkId()).modifyTime(DateUtil.date()).build());
                        log.info("key：receive_" + receiveId + ",录入时间：" + dateTime);
                        //stage.getDays() * 24 * 60 * 60
                        redis.set("receive_" + receiveId, receiveVo, 120);
                    } else {
                        //更新领取菜品记录表（菜品状态为已成熟）
                        receiveDishesMapper.update(ReceiveDishes.builder().markId(receiveId).status(2).modifyTime(DateUtil.date()).build());
                        //推送菜苗成熟的信息给用户
                        sender.mature(receiveVo);
                        log.info("key：expiring_" + receiveId + ",录入时间：" + dateTime);
                        //(receiveVo.getDishesInfo().getDays() - 3) * 24 * 60 * 60
                        redis.set("expiring_" + receiveId, receiveVo, 120);
                    }
                } else if (receiveVo.getStatus() == 2) {
                    //更新领取菜品记录表（菜品状态为已失效）
                    receiveDishesMapper.update(ReceiveDishes.builder().markId(receiveId).status(0).modifyTime(DateUtil.date()).build());
                    //动态添加领取菜品的记录
                    receiveRecordMapper.insert(ReceiveRecord.builder().markId(snowflake.nextIdStr()).userId(receiveVo.getUserId())
                            .description(ReceiveInfo.dishExpire(receiveVo.getDishesInfo().getDishesName())).createTime(DateUtil.date()).build());
                }
                break;
            case "expiring":
                String expiringId = redisKey.split("_")[1];
                log.info("过期key：" + redisKey + ",过期时间：" + dateTime);
                //根据主键查询领取菜品详细信息
                ReceiveVo receive = receiveDishesMapper.queryById(expiringId);
                if (ObjectUtil.isEmpty(receive)) {
                    return;
                }
                //推送菜苗券快要过期的信息给用户
                sender.expiring(receive.getUserId(), receive.getDishesInfo().getDishesName());
                log.info("key：receive_" + expiringId + ",录入时间：" + dateTime);
                //(receiveVo1.getDishesInfo().getDays() - 2) * 24 * 60 * 60
                redis.set("receive_" + expiringId, receive, 120);
                break;
        }
    }
}

