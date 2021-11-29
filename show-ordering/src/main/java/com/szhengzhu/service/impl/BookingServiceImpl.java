package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.bean.ordering.param.BookingParam;
import com.szhengzhu.mapper.BookingMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Slf4j
@Service("bookingService")
public class BookingServiceImpl implements BookingService {

    @Resource
    private Redis redis;

    @Resource
    private BookingMapper bookingMapper;

    @Override
    public List<Booking> selectInfo(BookingParam booking) {
        return bookingMapper.selectInfo(booking);
    }

    @Override
    public void add(Booking booking) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        booking.setMarkId(markId);
        booking.setCreateTime(DateUtil.date());
        booking.setStatus(1);
        bookingMapper.add(booking);
        if (ObjectUtil.isNotEmpty(booking.getBookingTime())) {
            int days = differenceBetween(DateUtil.date(), booking.getBookingTime());
            redis.set("booking_" + markId, booking, days);
            log.info("key：booking_{},录入时间：{}", markId, DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Override
    public void modify(Booking booking) {
        booking.setModifyTime(DateUtil.date());
        bookingMapper.modify(booking);
        Booking bookinginfo = bookingMapper.selectById(booking.getMarkId(), 1);
        if (ObjectUtil.isNotEmpty(bookinginfo)) {
            redis.del("booking_" + booking.getMarkId());
            int days = differenceBetween(DateUtil.date(), bookinginfo.getBookingTime());
            redis.set("booking_" + booking.getMarkId(), bookinginfo, days);
            log.info("key：booking_{},录入时间：{}", booking.getMarkId(), DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            redis.del("booking_" + booking.getMarkId());
        }
    }

    //两个时间的毫秒数
    public static int differenceBetween(Date date1, Date date2) {
        return (int) DateUtil.between(date1, date2, DateUnit.SECOND);
//        return (int) ((date2.getTime() - date1.getTime()) / 1000);
    }
}
