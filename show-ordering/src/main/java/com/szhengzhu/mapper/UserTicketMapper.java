package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.member.GradeTicket;
import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.ordering.TicketExpire;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.UserTicket;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;

public interface UserTicketMapper {

    int deleteByPrimaryKey(String markId);

    int insert(UserTicket record);

    int insertSelective(UserTicket record);

    UserTicket selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(UserTicket record);

    int updateByPrimaryKey(UserTicket record);

    @Update("update t_user_ticket set status=-1 where user_id=#{userId} and stop_time is not null and stop_time < NOW()")
    int updateExpire(@Param("userId") String userId);

    UserTicket selectUserTicket(@Param("ticketId") String ticketId);

    List<UserTicket> selectUserTicketBySelected(@Param("ticketIds") List<String> ticketIds, @Param("userId") String userId);

    List<UserTicketVo> listUserTicket(@Param("userId") String userId, @Param("status") Integer status);

    List<UserTicketVo> selectRes(@Param("userId") String userId);

    @Update("update t_user_ticket set use_time=null, status=1 where mark_id=#{ticketId}")
    int cancelUse(@Param("ticketId") String ticketId);

    int insertBatch(@Param("tickets") List<UserTicket> tickets);

    List<UserTicketVo> xlistUserTicketByIndent(@Param("userId") String userId, @Param("indentId") String indentId);

    List<MemberTicket> memberTicket(@Param("markId") String markId);

    int deleteMemberTicket(@Param("markId") String markId);

    List<TicketExpire> ticketExpire();

    List<String> selectUserGradeTicket(@Param("userId") String userId, @Param("templateId") String templateId);
}
