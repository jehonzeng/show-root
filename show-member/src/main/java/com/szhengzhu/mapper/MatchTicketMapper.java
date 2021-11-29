package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchTicket;

public interface MatchTicketMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchTicket record);

    int insertSelective(MatchTicket record);

    MatchTicket selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchTicket record);

    int updateByPrimaryKey(MatchTicket record);
}