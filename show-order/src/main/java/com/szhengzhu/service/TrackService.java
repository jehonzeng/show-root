package com.szhengzhu.service;

import java.util.Map;

/**
 * @author Jehon Zeng
 */
public interface TrackService {

    /**
     * 实时查询快递信息
     * 
     * @date 2019年9月4日 下午4:10:02
     * @param orderId
     * @return
     */
    Map<String, Object> getInfoByOrderId(String orderId) throws Exception;
    
    /**
     * 实时查询快递信息
     * 
     * @date 2019年9月4日 下午5:22:28
     * @param deliveryId
     * @return
     * @throws Exception
     */
    Map<String, Object> getInfoByDeliveryId(String deliveryId) throws Exception;
}
