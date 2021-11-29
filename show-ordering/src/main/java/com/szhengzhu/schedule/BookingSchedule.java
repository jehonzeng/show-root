package com.szhengzhu.schedule;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.mapper.BookingMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class BookingSchedule {
    @Resource
    private BookingMapper bookingMapper;

    @Scheduled(cron = "0 0 3 * * ?")
    public void autoExpire() {
        bookingMapper.modifyStatus(Booking.builder().bookingTime(DateUtil.date()).modifyTime(DateUtil.date()).status(0).build());
    }
}
