package com.szhengzhu.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.order.UserCoupon;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CouponUtils {

    public static List<UserCoupon> createCoupon(CouponTemplate template, String userId) {
        List<UserCoupon> list = new LinkedList<>();
        UserCoupon userCoupon;
        Date start = null;
        Date end = null;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        // 根据限领取张数生成同种优惠券集合
//        int total = template.getCouponTotal();
        for (int i = 0, len = template.getLimitCount(); i < len; i++) {
            userCoupon = UserCoupon.builder().markId(snowflake.nextIdStr()).couponName(template.getCouponName()).couponType(template.getCouponType())
                    .linkId(template.getRangeId()).linkName(template.getRangeName()).limitPrice(template.getLimitPrice()).serverStatus(0)
                    //关联模板
                    .templateId(template.getMarkId()).userId(userId).useTime(null).limitRegion(template.getLimitRegion()).limitTime(template.getLimitTime()).build();
//            userCoupon = new UserCoupon();
//            userCoupon.setMarkId(snowflake.nextIdStr());
//            userCoupon.setCouponName(template.getCouponName());
//            userCoupon.setCouponType(template.getCouponType());
//            userCoupon.setLinkId(template.getRangeId());
//            userCoupon.setLinkName(template.getRangeName());
//            userCoupon.setLimitPrice(template.getLimitPrice());
//            userCoupon.setServerStatus(0);
//            //关联模板
//            userCoupon.setTemplateId(template.getMarkId());
//            userCoupon.setUserId(userId);
//            userCoupon.setUseTime(null);
//            userCoupon.setLimitRegion(template.getLimitRegion());
//            userCoupon.setLimitTime(template.getLimitTime());
            if (template.getServerType().intValue() == 0) {
                userCoupon.setCouponPrice(template.getCouponPrice());
            }
            if (template.getServerType().intValue() == 1) {
                userCoupon.setCouponDiscount(template.getCouponDiscount());
            }
            // 选择哪种时间类型处理
            if (template.getValidityType().intValue() == 0) {
                // 指定时间段
                start = template.getStartTime();
                end = template.getStopTime();
            }
            if (template.getValidityType().intValue() == 1) {
                // 指定有效天数
                start = DateUtil.date();
                // 相隔天数的 23：59：59
                end = DateUtil.endOfDay(DateUtil.offsetDay(DateUtil.date(), template.getValidityDay()));
            }
            userCoupon.setStartTime(start);
            userCoupon.setStopTime(end);
            list.add(userCoupon);
        }
        return list;
    }
}
