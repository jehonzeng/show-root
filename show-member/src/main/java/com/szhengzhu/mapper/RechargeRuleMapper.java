package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.RechargeRule;
import com.szhengzhu.bean.vo.Combobox;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface RechargeRuleMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(RechargeRule record);

    int insertSelective(RechargeRule record);

    RechargeRule selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(RechargeRule record);

    int updateByPrimaryKey(RechargeRule record);

    List<RechargeRule> selectByExampleSelective(RechargeRule data);

    @Update("update t_recharge_rule set status = #{status} where mark_id = #{ruleId}")
    int updateByRuleId(@Param("ruleId") String ruleId, @Param("status") int status);

    int updateBatchStatus(@Param("ruleIds") String[] ruleIds,@Param("status") Integer status);

    List<RechargeRule> selectList();

    List<Combobox> selectCombobox();
}