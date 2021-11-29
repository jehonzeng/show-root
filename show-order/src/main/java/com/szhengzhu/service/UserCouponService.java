package com.szhengzhu.service;

import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.bean.wechat.vo.CouponBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author Jehon Zeng
 */
public interface UserCouponService {

    /**
     * 获取用户优惠券列表
     * 
     * @date 2019年3月4日 下午4:09:07
     * @param couponPage
     * @return
     */
    PageGrid<UserCoupon> pageCoupon(PageParam<UserCoupon> couponPage);
    
    /**
     * 获取用户优惠券或线下代金券
     * 
     * @date 2019年6月11日 下午6:11:14
     * @param userId
     * @param type
     * @return
     */
    List<CouponBase> listByUser(String userId, Integer type);

    /**
     * 添加用户领券信息
     * 
     * @date 2019年6月21日 下午6:01:14
     * @param base
     * @return
     */
    void addUserCoupon(List<UserCoupon> base);

    /**
     * 给角色中所有用户添加用户券信息
     * 
     * @date 2019年6月25日 下午2:07:38
     * @param base
     * @return
     */
    void addCouponByRole(List<UserCoupon> base,List<UserBase> userList);

    
    
    /**
     * 获取优惠券详细信息
     * 
     * @date 2019年7月4日 下午12:22:35
     * @param couponId
     * @return
     */
    UserCoupon getInfo(String couponId);

    /**
     * 清理过期优惠券
     *
     * @return
     * @date 2019年8月28日
     */
    int overdue();
}
