package com.szhengzhu.mapper;

import com.szhengzhu.bean.user.XwechatInfo;

public interface XwechatInfoMapper {
    
    int deleteByPrimaryKey(Long markId);

    int insert(XwechatInfo record);

    int insertSelective(XwechatInfo record);

    XwechatInfo selectByPrimaryKey(Long markId);

    int updateByPrimaryKeySelective(XwechatInfo record);

    int updateByPrimaryKey(XwechatInfo record);
}