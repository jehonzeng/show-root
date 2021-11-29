package com.szhengzhu.handler;

import com.szhengzhu.bean.wechat.vo.ActivityGiftVo;

import java.util.List;

/**
 * @author Administrator
 */
public abstract class AbstractPart {

    /**
     * 自动领取
     *
     * @param activityId
     * @param userId
     * @param type
     * @return
     * @date 2019年10月22日
     */
    public abstract List<ActivityGiftVo> atuoGift(String activityId, String userId, Integer type);
    
    /**
     * 手动领取
     *
     * @param actGiftId
     * @param userId
     * @param type
     * @return
     * @date 2019年10月22日
     */
    public abstract ActivityGiftVo manualGift(String actGiftId, String userId, Integer type);
}
