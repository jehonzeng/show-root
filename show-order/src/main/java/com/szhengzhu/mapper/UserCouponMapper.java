package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.wechat.vo.CouponBase;

/**
 * @author Jehon Zeng
 */
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

    @Update("UPDATE t_user_coupon SET server_status = -1 WHERE server_status = 0 AND stop_time < NOW()")
    void updateOverdueCoupon();
    
    @Update("UPDATE t_user_coupon SET server_status = -1 WHERE server_status = 0 AND stop_time < NOW() AND user_id=#{userId}")
    void updateOverdueCouponByUserId(@Param("userId") String userId);
    
    @Update("update t_user_coupon set server_status = 1, use_time = #{useTime} where mark_id=#{couponId}")
    void useCoupon(@Param("couponId") String couponId, @Param("useTime") Date useTime);

    @Update("UPDATE t_user_coupon SET server_status = -1 WHERE server_status = 0 and stop_time < NOW() ")
    int updateByEnd();
    
    @Update("UPDATE t_user_coupon SET server_status = 0, use_time = null WHERE mark_id=#{couponId}")
    void backOrderCoupon(@Param("couponId") String couponId);
}