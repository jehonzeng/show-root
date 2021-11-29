package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.bean.wechat.vo.CouponBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.UserCouponMapper;
import com.szhengzhu.service.UserCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Service("userCouponService")
public class UserCouponServiceImpl implements UserCouponService {

    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    public PageGrid<UserCoupon> pageCoupon(PageParam<UserCoupon> couponPage) {
        userCouponMapper.updateOverdueCoupon();
        PageMethod.startPage(couponPage.getPageIndex(), couponPage.getPageSize());
        PageMethod.orderBy(couponPage.getSidx() + " " + couponPage.getSort());
        PageInfo<UserCoupon> pageInfo = new PageInfo<>(userCouponMapper.selectByExampleSelective(couponPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<CouponBase> listByUser(String userId, Integer type) {
        userCouponMapper.updateOverdueCouponByUserId(userId);
        return userCouponMapper.selectByUser(userId, type);
    }

    @Override
    public void addUserCoupon(List<UserCoupon> base) {
        userCouponMapper.insertBatch(base);
    }

    @Override
    public void addCouponByRole(List<UserCoupon> base, List<UserBase> userList) {
        List<UserCoupon> list = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        UserCoupon temp;
        for (UserBase users : userList) {
            for (UserCoupon coupon : base) {
                temp = coupon.clone();
                temp.setMarkId(snowflake.nextIdStr());
                temp.setUserId(users.getMarkId());
                list.add(temp);
                if (list.size() % 1000 == 0) {
                    userCouponMapper.insertBatch(list);
                    list = new LinkedList<>();
                }
            }
        }
        if (!list.isEmpty()) { userCouponMapper.insertBatch(list); }
    }

    @Override
    public UserCoupon getInfo(String couponId) {
        UserCoupon coupon = userCouponMapper.selectByPrimaryKey(couponId);
        if (coupon.getStopTime().getTime() < DateUtil.date().getTime() && coupon.getServerStatus() == 0) {
            coupon.setServerStatus(-1);
            userCouponMapper.updateByPrimaryKeySelective(coupon);
        }
        return coupon;
    }

    @Override
    public int overdue() {
        return userCouponMapper.updateByEnd();
    }
}