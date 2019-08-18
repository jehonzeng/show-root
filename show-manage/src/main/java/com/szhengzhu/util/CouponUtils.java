package com.szhengzhu.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.order.UserCoupon;

public class CouponUtils {

    public static List<UserCoupon> createCoupon(CouponTemplate template, String userId) {
        List<UserCoupon> list = new LinkedList<>();
        UserCoupon userCoupon = null;
        Date start = null;
        Date end = null;
        IdGenerator idGenerator = IdGenerator.getInstance();
        // 根据限领取张数生成同种优惠券集合
//        int total = template.getCouponTotal();
        for (int i = 0, len = template.getLimitCount(); i < len; i++) {
            userCoupon = new UserCoupon();
            userCoupon.setMarkId(idGenerator.nexId());
            userCoupon.setCouponName(template.getCouponName());
            userCoupon.setCouponType(template.getLineType());//券类型0线下1线上
            userCoupon.setLinkId(template.getRangeId());
            userCoupon.setLinkName(template.getRangeName());
            userCoupon.setLimitPrice(template.getLimitPrice());
            userCoupon.setServerStatus(0);
            userCoupon.setTemplateId(template.getMarkId());//关联模板
            userCoupon.setUserId(userId);
            userCoupon.setCouponPrice(template.getCouponPrice());
            userCoupon.setCouponDiscount(template.getCouponDiscount());
            // 选择哪种时间类型处理
            if (template.getValidityType().intValue() == 0) {
                // 指定时间段
                start = template.getStartTime();
                end = template.getStopTime();
            }
            if (template.getValidityType().intValue() == 1) {
                // 指定有效天数
                start = TimeUtils.today();
                end = TimeUtils.tomorow(template.getValidityDay(), 23, 59, 59);
            }
            userCoupon.setStartTime(start);
            userCoupon.setStopTime(end);
            userCoupon.setUseTime(null);
            userCoupon.setLimitRegion(template.getLimitRegion());
            list.add(userCoupon);
        }
        return list;
    }
}
