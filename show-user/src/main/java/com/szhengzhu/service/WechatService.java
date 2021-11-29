package com.szhengzhu.service;

import com.szhengzhu.bean.user.WechatInfo;
import com.szhengzhu.bean.user.XwechatInfo;

/**
 * @author Jehon Zeng
 */
public interface WechatService {

    /**
     * 添加用户微信信息
     * 
     * @date 2019年8月12日 下午4:15:26
     * @param wechatInfo
     */
    void add(WechatInfo wechatInfo);
    
    /**
     * 添加小程序信息
     * 
     * @param xwechatInfo
     */
    void addX(XwechatInfo xwechatInfo);
}
