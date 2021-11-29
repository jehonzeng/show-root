package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.wechat.vo.TeambuyDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.TeambuyInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.TeambuyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
@Service("teambuyService")
public class TeambuyServiceImpl implements TeambuyService {

    @Resource
    private Redis redis;

    @Resource
    private TeambuyInfoMapper teambuyMapper;

    private final ConcurrentHashMap<String, Boolean> lockIdMap = new ConcurrentHashMap<>();

    @Override
    public PageGrid<TeambuyInfo> pageTeambuy(PageParam<TeambuyInfo> teambuyPage) {
        PageMethod.startPage(teambuyPage.getPageIndex(), teambuyPage.getPageSize());
        PageMethod.orderBy(teambuyPage.getSidx() + " " + teambuyPage.getSort());
        PageInfo<TeambuyInfo> pageInfo = new PageInfo<>(teambuyMapper.selectByExampleSelective(teambuyPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Transactional
    @Override
    public TeambuyInfo addInfo(TeambuyInfo teambuyInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        String markId = snowflake.nextIdStr();
        teambuyInfo.setMarkId(markId);
        teambuyInfo.setServerStatus(false);
        teambuyMapper.insertSelective(teambuyInfo);
        redis.lSet("activity:teambuy:ids", markId);
        return teambuyInfo;
    }

    @Override
    public void modifyInfo(TeambuyInfo teambuyInfo) {
        redis.del("activity:teambuy:stock:" + teambuyInfo.getMarkId());
        for (int i = 0; i < teambuyInfo.getTotalStock(); i++) {
            redis.lSet("activity:teambuy:stock:" + teambuyInfo.getMarkId(), i);
        }
        long timeout = teambuyInfo.getStartTime().getTime() - System.currentTimeMillis();
        redis.expire("activity:seckill:stock:" + teambuyInfo.getMarkId(), timeout / 1000);
        teambuyMapper.updateByPrimaryKeySelective(teambuyInfo);
    }

    @Override
    public TeambuyInfo getInfo(String markId) {
        return teambuyMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<Map<String, Object>> pageForeList(PageParam<String> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("type desc, start_time asc");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(teambuyMapper.selectForeList());
        return new PageGrid<>(pageInfo);
    }

    @Override
    public TeambuyDetail getDetail(String markId) {
        List<Object> teambuyIds = redis.lGet("activity:teambuy:ids", 0, -1);
        ShowAssert.checkTrue(!teambuyIds.contains(markId), StatusCode._4004);
        TeambuyDetail teambuyDetail;
        try {
            Object teambuyDetailObj = redis.get("activity:teambuy:detail:" + markId);
            if (ObjectUtil.isNotNull(teambuyDetailObj)) {
                teambuyDetail = JSON.parseObject(JSON.toJSONString(teambuyDetailObj), TeambuyDetail.class);
                return teambuyDetail;
            }
            // 防止数据库因请求量过大雪崩而做的拦截
            boolean lock = lockIdMap.put(markId, true) == null;
            ShowAssert.checkTrue(!lock, StatusCode._5010);
            teambuyDetailObj = redis.get("activity:teambuy:detail:" + markId);
            if (ObjectUtil.isNotNull(teambuyDetailObj)) {
                teambuyDetail = JSON.parseObject(JSON.toJSONString(teambuyDetailObj), TeambuyDetail.class);
                return teambuyDetail;
            }
            teambuyDetail = teambuyMapper.selectDetail(markId);
            redis.set("activity:teambuy:detail:" + markId, teambuyDetail, 2 * 60 * 60L);
            return teambuyDetail;
        } finally {
            lockIdMap.remove(markId);
        }
    }

    @Override
    public void subStock(String markId) {
        teambuyMapper.subStock(markId);
    }

    @Override
    public void addStock(String markId) {
        teambuyMapper.addStock(markId);
        redis.lSet("activity:teambuy:stock:" + markId, 1);
    }
}
