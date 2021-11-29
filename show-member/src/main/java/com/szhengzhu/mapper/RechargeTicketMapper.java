package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.RechargeTicket;
import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RechargeTicketMapper {
    
    int deleteByPrimaryKey(@Param("ruleId") String ruleId,
            @Param("templateId") String templateId);

    int insert(RechargeTicket record);

    int insertSelective(RechargeTicket record);

    RechargeTicket selectByPrimaryKey(@Param("ruleId") String ruleId,
            @Param("templateId") String templateId);

    int updateByPrimaryKeySelective(RechargeTicket record);

    int updateByPrimaryKey(RechargeTicket record);

    int insertBatchTicket(@Param("ruleId") String ruleId,
            @Param("tickets") List<TicketTemplateVo> tickets);

    @Select("select template_id as templateId,quantity from t_recharge_ticket where rule_id = #{ruleId}")
    List<TicketTemplateVo> selectByRuleId(@Param("ruleId") String ruleId);

    @Delete("delete from t_recharge_ticket where rule_id = #{ruleId} ")
    int deleteByRuleId(@Param("ruleId") String ruleId);
}