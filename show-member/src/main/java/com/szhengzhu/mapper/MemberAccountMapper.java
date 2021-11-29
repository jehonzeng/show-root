package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.MemberByType;
import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.member.vo.MemberAccountVo;
import com.szhengzhu.bean.member.vo.MemberTicketVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MemberAccountMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MemberAccount record);

    int insertSelective(MemberAccount record);

    MemberAccount selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MemberAccount record);

    int updateByPrimaryKey(MemberAccount record);

    MemberAccountVo selectVoById(String markId);

    List<MemberAccount> selectByExampleSelective(MemberByType param);

    List<MemberAccountVo> selectByNoOrPhone(@Param("phone") String phone, @Param("accountNo") String accountNo);

//    int insertBatch(List<MemberAccount> list);

    MemberTicketVo selectTicketVoById(@Param("memberId") String memberId);

    @Select("select ifnull(total_amount, 0) from t_member_account where user_id=#{userId}")
    BigDecimal selectTotalByUserId(@Param("userId") String userId);

    MemberAccount selectByUserId(@Param("userId") String userId);

    @Select("select mark_id from t_member_account where phone=#{phone} limit 1")
    String selectIdByPhone(@Param("phone") String phone);

    List<Map<String, String>> birthdayTicket(@Param("days") Integer days);

    BigDecimal selectMemberDiscount(@Param("markId")String markId);
}
