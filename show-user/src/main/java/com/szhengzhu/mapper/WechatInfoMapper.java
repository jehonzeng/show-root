package com.szhengzhu.mapper;

import com.szhengzhu.bean.user.WechatInfo;

public interface WechatInfoMapper {
    
    int deleteByPrimaryKey(Long markId);

    int insert(WechatInfo record);

    int insertSelective(WechatInfo record);

    WechatInfo selectByPrimaryKey(Long markId);

    int updateByPrimaryKeySelective(WechatInfo record);

    int updateByPrimaryKey(WechatInfo record);
}