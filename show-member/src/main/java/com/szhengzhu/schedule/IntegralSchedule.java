package com.szhengzhu.schedule;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.member.IntegralExpire;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.mapper.IntegralDetailMapper;
import com.szhengzhu.mapper.IntegralExpireMapper;
import com.szhengzhu.rabbitmq.RabbitQueue;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.redis.RedisKey;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Component
public class IntegralSchedule {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private IntegralDetailMapper integralDetailMapper;

    @Resource
    private IntegralExpireMapper integralExpireMapper;

    /**
     * 积分过期推送和积分过期删除
     */
    @Scheduled(cron = "0 40 9 ? * *")
    public void autoIntegralExpire() {
        boolean result = redis.lock(RabbitQueue.INTEGRAL_EXPIRE, 60);
        if (!result) {
            return;
        }
        String redisKey = RabbitQueue.INTEGRAL_EXPIRE + "_" + RedisKey.SCHEDULE_INFO;
        boolean num = redis.hasKey(redisKey);
        if (!num) {
            redis.set(redisKey, 0, Contacts.SCHEDULE_EXPIRE);
            IntegralExpire integralExpire = integralExpireMapper.queryInfo();
            if (ObjectUtil.isNotEmpty(integralExpire)) {
                Integer expireTime = integralExpire.getExpireTime();
                Integer pushDays = integralExpire.getPushDays();
                //查询用户过期积分
                List<IntegralDetail> integralDetail = integralDetailMapper.selectIntegralExpire(expireTime);
                for (IntegralDetail detail : integralDetail) {
                    integralDetailMapper.updateByPrimaryKeySelective(IntegralDetail.builder().markId(detail.getMarkId()).status(-1).build());
                    sender.checkAccount(detail.getUserId(), true);
                }
                //查询用户快要过期积分
                List<Map<String, String>> mapList = integralDetailMapper.selectPushUser(expireTime, pushDays, null);
                if (ObjectUtil.isNotEmpty(mapList)) {
                    for (Map<String, String> str : mapList) {
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", str.get("userId"));
                        map.put("days", pushDays.toString());
                        map.put("integral", str.get("integral"));
                        map.put("integralTotal", String.valueOf(integralDetailMapper.selectTotalByUser(str.get("userId"))));
                        sender.integralExpire(map);
                    }
                }
            }
            redis.unlock(RabbitQueue.INTEGRAL_EXPIRE);
        }
    }
}
