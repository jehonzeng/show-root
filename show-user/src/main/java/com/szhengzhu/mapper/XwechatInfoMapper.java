package com.szhengzhu.mapper;

import com.szhengzhu.bean.user.XwechatInfo;

public interface XwechatInfoMapper {
    
    int deleteByPrimaryKey(String openId);

    int replaceInsert(XwechatInfo record);

    int insertSelective(XwechatInfo record);

    XwechatInfo selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(XwechatInfo record);

    int updateByPrimaryKey(XwechatInfo record);
}