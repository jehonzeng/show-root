package com.szhengzhu.crontab;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.mapper.SeckillInfoMapper;
import com.szhengzhu.mapper.TeambuyInfoMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ActivityRunner implements ApplicationRunner {

    @Resource
    private TeambuyInfoMapper teambuyInfoMapper;

    @Resource
    private SeckillInfoMapper seckillInfoMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) {

        // 以防再次启动添加多余的重复id
        redisTemplate.delete("activity:seckill:ids");
        List<String> seckillIds = seckillInfoMapper.selectIds();
        redisTemplate.opsForList().leftPushAll("activity:seckill:ids", seckillIds);
        
        redisTemplate.delete("activity:teambuy:ids");
        List<String> teambuyIds = teambuyInfoMapper.selectIds();
        redisTemplate.opsForList().leftPushAll("activity:teambuy:ids", teambuyIds);

        List<TeambuyInfo> teambuyList = teambuyInfoMapper.selectValidData();
        for (TeambuyInfo teambuyInfo : teambuyList) {
            redisTemplate.delete("activity:seckill:stock:" + teambuyInfo.getMarkId());
            for (int i = 0; i < teambuyInfo.getTotalStock(); i++) {
                redisTemplate.opsForList().leftPush("activity:teambuy:stock:" + teambuyInfo.getMarkId(), 1);
            }
            long timeout = teambuyInfo.getStopTime().getTime() - System.currentTimeMillis();
            redisTemplate.expire("activity:teambuy:stock:" + teambuyInfo.getMarkId(), timeout, TimeUnit.MILLISECONDS);
        }
        List<SeckillInfo> seckillList = seckillInfoMapper.selectValidData();
        for (SeckillInfo seckillInfo : seckillList) {
            redisTemplate.delete("activity:seckill:stock:" + seckillInfo.getMarkId());
            for (int i = 0; i < seckillInfo.getTotalStock(); i++) {
                redisTemplate.opsForList().leftPush("activity:seckill:stock:" + seckillInfo.getMarkId(), 1);
            }
            long timeout = seckillInfo.getStopTime().getTime() - System.currentTimeMillis();
            redisTemplate.expire("activity:seckill:stock:" + seckillInfo.getMarkId(), timeout, TimeUnit.MILLISECONDS);
        }

    }

}
