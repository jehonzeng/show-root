package com.szhengzhu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.user.WechatInfo;
import com.szhengzhu.bean.user.XwechatInfo;
import com.szhengzhu.mapper.WechatInfoMapper;
import com.szhengzhu.mapper.XwechatInfoMapper;
import com.szhengzhu.service.WechatService;

/**
 * @author Jehon Zeng
 */
@Service("wechatService")
public class WechatServiceImpl implements WechatService {

    @Resource
    private WechatInfoMapper wechatInfoMapper;
    
    @Resource
    private XwechatInfoMapper xwechatInfoMapper;

    @Override
    public void add(WechatInfo wechatInfo) {
        wechatInfoMapper.replaceInsert(wechatInfo);
    }

    @Override
    public void addX(XwechatInfo xwechatInfo) {
        xwechatInfoMapper.replaceInsert(xwechatInfo);
    }

}
