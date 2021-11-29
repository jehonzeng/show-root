package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.wechat.vo.SeckillDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.SeckillInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.SeckillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private Redis redis;

    @Resource
    private SeckillInfoMapper seckillMapper;

    private final ConcurrentHashMap<String, Boolean> lockIdMap = new ConcurrentHashMap<>();

    @Override
    public PageGrid<SeckillInfo> pageSeckill(PageParam<SeckillInfo> seckillPage) {
        PageMethod.startPage(seckillPage.getPageIndex(), seckillPage.getPageSize());
        PageMethod.orderBy(seckillPage.getSidx() + " " + seckillPage.getSort());
        PageInfo<SeckillInfo> pageInfo = new PageInfo<>(seckillMapper.selectByExampleSelective(seckillPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public SeckillInfo addInfo(SeckillInfo seckillInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        String markId = snowflake.nextIdStr();
        seckillInfo.setMarkId(markId);
        seckillInfo.setServerStatus(false);
        seckillMapper.insertSelective(seckillInfo);
        redis.lSet("activity:seckill:ids", markId);
        return seckillInfo;
    }

    @Override
    public void modifyInfo(SeckillInfo seckillInfo) {
        redis.del("activity:seckill:stock:" + seckillInfo.getMarkId());
        long timeout = seckillInfo.getStartTime().getTime() - System.currentTimeMillis();
        String cacheKey = "activity:seckill:stock:" + seckillInfo.getMarkId();
        for (int i = 0; i < seckillInfo.getTotalStock(); i++) {
            redis.lSet(cacheKey, 1);
        }
        redis.expire(cacheKey, timeout / 1000);
        seckillMapper.updateByPrimaryKeySelective(seckillInfo);
    }

    @Override
    public SeckillInfo getInfo(String markId) {
        return seckillMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<Map<String, Object>> pageInfo(PageParam<String> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("start_time asc");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(seckillMapper.selectForeList());
        return new PageGrid<>(pageInfo);
    }

    @Override
    public SeckillDetail getDetail(String markId) {
        List<Object> seckillIds = redis.lGet("activity:seckill:ids", 0, -1);
        ShowAssert.checkTrue(!seckillIds.contains(markId), StatusCode._4004);
        SeckillDetail seckillDetail;
        try {
            Object seckillDetailObj = redis.get("activity:seckill:detail:" + markId);
            if (seckillDetailObj != null) {
                seckillDetail = JSON.parseObject(JSON.toJSONString(seckillDetailObj), SeckillDetail.class);
                return seckillDetail;
            }
            // 防止数据库因请求量过大雪崩而做的拦截
            boolean lock = lockIdMap.put(markId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            seckillDetailObj = redis.get("activity:seckill:detail:" + markId);
            if (seckillDetailObj != null) {
                seckillDetail = JSON.parseObject(JSON.toJSONString(seckillDetailObj), SeckillDetail.class);
                return seckillDetail;
            }
            seckillDetail = seckillMapper.selectDetail(markId);
            redis.set("activity:seckill:detail:" + markId, seckillDetail, 2 * 60 * 60L);
            return seckillDetail;
        } finally {
            lockIdMap.remove(markId);
        }
    }

    @Override
    public void subStock(String markId) {
        seckillMapper.subStock(markId);
    }

    @Override
    public void addStock(String markId) {
        seckillMapper.addStock(markId);
        redis.lSet("activity:seckill:stock:" + markId, 1);
    }
}
