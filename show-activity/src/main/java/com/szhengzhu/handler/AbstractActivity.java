package com.szhengzhu.handler;

import com.szhengzhu.core.Result;

/**
 * @author Administrator
 */
public abstract class AbstractActivity {
    

     /**
     * 获取活动信息（0：新人领券 1：邀新有礼 2：其他）
     *
     * @param markId
     * @return
     * @date 2019年10月21日
     */
    public abstract Result<Object> getActBaseInfo(String markId, String userId);
    
}
