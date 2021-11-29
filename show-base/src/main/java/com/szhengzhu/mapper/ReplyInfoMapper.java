package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.base.ReplyInfo;

public interface ReplyInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(ReplyInfo record);

    int insertSelective(ReplyInfo record);

    ReplyInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ReplyInfo record);

    int updateByPrimaryKey(ReplyInfo record);
    
    ReplyInfo selectByMsg(@Param("msg") String msg);
    
    List<ReplyInfo> selectByExampleSelective(ReplyInfo replyInfo);
}