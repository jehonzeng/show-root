package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.user.WechatInfo;

public interface WechatInfoMapper {
    
    int deleteByPrimaryKey(String openId);

    int replaceInsert(WechatInfo record);

    int insertSelective(WechatInfo record);

    WechatInfo selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(WechatInfo record);

    int updateByPrimaryKey(WechatInfo record);
    
    @Update("UPDATE t_wechat_info SET wechat_status=#{wechatStatus} WHERE open_id=#{openId}")
    void updateWechatStatusByOpenId(@Param("openId") String openId, @Param("wechatStatus") int wechatStatus);
}