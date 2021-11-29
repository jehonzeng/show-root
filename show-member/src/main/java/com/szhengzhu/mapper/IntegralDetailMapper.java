package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.member.MemberRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IntegralDetailMapper {

    int insert(IntegralDetail record);

    int insertSelective(IntegralDetail record);

    List<IntegralDetail> selectByExampleSelective(IntegralDetail userIntegral);

    int updateByPrimaryKeySelective(IntegralDetail integralDetail);

    @Select("select IFNULL(SUM(integral_limit), 0) from t_integral_detail where user_id=#{userId} and status=1")
    int selectTotalByUser(@Param("userId") String userId);

    @Update("update t_integral_detail set account_id=#{accountId} where user_id=#{userId} and status=1")
    int updateAccountByUser(@Param("accountId") String accountId, @Param("userId") String userId);

    List<IntegralDetail> selectByUserId(@Param("userId") String userId);

    List<IntegralDetail> memberIntegral(MemberRecord memberRecord);

    @Select("SELECT IFNULL(SUM(integral_limit), 0) FROM t_integral_detail WHERE user_id=#{userId} AND (type = 0 or type = 1) AND TO_DAYS(create_time)=TO_DAYS(NOW()) AND status=1")
    Integer selectTodayTotal(@Param("userId") String userId);

    @Select("SELECT ABS(IFNULL(SUM(integral_limit), 0)) FROM t_integral_detail WHERE user_id=#{userId} AND (type=-1 or type = 2) AND status=1")
    Integer selectConsumeTotal(@Param("userId") String userId);

    void deleteById(@Param("markId") String markId);

    List<IntegralDetail> selectIntegralExpire(@Param("month") Integer month);

    List<Map<String, String>> selectPushUser(@Param("month") Integer month, @Param("pushDays") Integer pushDays, @Param("userId") String userId);
}
