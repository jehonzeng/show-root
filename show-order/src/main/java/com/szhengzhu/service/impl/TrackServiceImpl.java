package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhengzhu.bean.kd.KdInfo;
import com.szhengzhu.bean.kd.KdResult;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.TrackInfo;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.OrderDeliveryMapper;
import com.szhengzhu.mapper.TrackInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.TrackService;
import com.szhengzhu.util.KdQueryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Service("trackService")
public class TrackServiceImpl implements TrackService {

    @Resource
    private TrackInfoMapper trackInfoMapper;

    @Resource
    private OrderDeliveryMapper deliveryMapper;

    @Resource
    private Redis redis;

    @SuppressWarnings({ "unchecked", "null" })
    @Override
    public Map<String, Object> getInfoByOrderId(String orderId) throws Exception {
        Map<String, Object> trackMap = (Map<String, Object>) redis.get("order:track:orderId:" + orderId);
        if (trackMap != null) { return trackMap; }
        trackMap = new HashMap<>(4);
        OrderDelivery delivery = deliveryMapper.selectByOrderId(orderId);
        ShowAssert.checkTrue(StrUtil.isEmpty(delivery.getDeliveryType()), StatusCode._4020);
        String defaultDelivery = "shansong";
        if (!defaultDelivery.equals(delivery.getDeliveryType())) {
            trackMap = getResultMap(delivery);
        } else {
            trackMap.put("trackNo", "无");
            trackMap.put("com", delivery.getDeliveryTypeDesc());
        }
        redis.set("order:track:orderId:" + orderId, trackMap, 3L * 60 * 60);
        return trackMap;
    }

    /**
     * 操作物流信息
     * 
     * @date 2019年9月4日 下午5:29:41
     * @param delivery
     * @return
     * @throws Exception
     */
    private Map<String, Object> getResultMap(OrderDelivery delivery) throws Exception {
        Map<String, Object> trackMap = new HashMap<>();
        List<KdInfo> kdList = new ArrayList<>();
        trackMap.put("trackNo", delivery.getTrackNo());
        trackMap.put("com", delivery.getDeliveryTypeDesc());
        TrackInfo trackInfo = trackInfoMapper.selectByNoAndCom(delivery.getDeliveryType(), delivery.getTrackNo());
        KdResult kdResult;
        String info;
        if (ObjectUtil.isNotNull(trackInfo) && trackInfo.getState() == 3) {
            info = trackInfo.getInfo();
        } else {
            info = KdQueryUtils.synQueryData(delivery.getDeliveryType(), delivery.getTrackNo());
        }
        if (StrUtil.isEmpty(info)) { return trackMap; }
        Map mapType = JSON.parseObject(info, Map.class);
        String result = "result";
        if (!mapType.containsKey(result)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            kdResult = mapper.readValue(info, KdResult.class);
            if (ObjectUtil.isNull(trackInfo)) {
                addInfo(kdResult, info);
            } else if (trackInfo.getState() != 3) {
                trackInfo.setState(kdResult.getState());
                trackInfo.setInfo(info);
                trackInfoMapper.updateByPrimaryKeySelective(trackInfo);
            }
            kdList = kdResult.getData();
        }
        trackMap.put("info", kdList);
        return trackMap;
    }

    private void addInfo(KdResult result, String info) {
        TrackInfo track = new TrackInfo();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        track.setMarkId(snowflake.nextIdStr());
        track.setCom(result.getCom());
        track.setTrackNo(result.getNu());
        track.setState(result.getState());
        track.setInfo(info);
        trackInfoMapper.insertSelective(track);
    }

    @SuppressWarnings({ "unchecked", "null" })
    @Override
    public Map<String, Object> getInfoByDeliveryId(String deliveryId) throws Exception {
        Map<String, Object> trackMap = (Map<String, Object>) redis.get("order:track:deliveryId:" + deliveryId);
        if (trackMap != null) { return trackMap; }
        trackMap = new HashMap<>(4);
        OrderDelivery delivery = deliveryMapper.selectByPrimaryKey(deliveryId);
        ShowAssert.checkTrue(StrUtil.isEmpty(delivery.getTrackNo()), StatusCode._4020);
        String defaultDelivery = "shansong";
        if (!defaultDelivery.equals(delivery.getDeliveryType())) {
            trackMap = getResultMap(delivery);
        } else {
            trackMap.put("trackNo", "无");
            trackMap.put("com", delivery.getDeliveryTypeDesc());
        }
        redis.set("order:track:deliveryId:" + deliveryId, trackMap, 30L * 60);
        return trackMap;
    }

}
