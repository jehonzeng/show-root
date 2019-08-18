package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.wechat.vo.CouponBase;

public interface UserCouponMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);
    
    List<UserCoupon> selectByExampleSelective(UserCoupon userCoupon);
    
    List<CouponBase> selectByUser(@Param("userId") String userId, @Param("type") Integer type);

    int insertBatch(List<UserCoupon> base);

    @Update("update t_user_coupon set server_status = -1 where server_status = 0 and stop_time < NOW()")
    void updateOverdueCoupon();
    
    @Update("update t_user_coupon set server_status = 1, use_time = #{useTime} where mark_id=#{couponId}")
    void useCoupon(@Param("couponId") String couponId, @Param("useTime") Date useTime);
}