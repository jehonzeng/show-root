package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.HolidayInfoMapper;
import com.szhengzhu.service.HolidayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Jehon Zeng
 */
@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {

    @Resource
    private HolidayInfoMapper holidayInfoMapper;

    private final String[] dayOfWeek = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Integer addHolidays(List<Date> holidayList) {
        return holidayInfoMapper.insertBatch(holidayList);
    }

    @Override
    public void operateHoliday(String holiday) {
        Date date = DateUtil.parse(holiday, DATE_FORMAT);
        ShowAssert.checkTrue(!date.after(DateUtil.date()), StatusCode._4006);
        boolean isHoliday = holidayInfoMapper.countHoliday(date) > 0;
        if (isHoliday) {
            holidayInfoMapper.deleteByPrimaryKey(date);
        } else {
            holidayInfoMapper.insert(new HolidayInfo(date));
        }
    }

    @Override
    public List<DeliveryDate> listDeliveryDate() {
        int daySize = 8;
        String deadTime = "10:00:00";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<DeliveryDate> deliveryDates = new ArrayList<>();
        String start = DateUtil.today();
        String end = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), daySize), DATE_FORMAT);
//        Calendar today = Calendar.getInstance();
//        String start = simpleDateFormat.format(today.getTime());
//        today.add(Calendar.DATE, daySize);
//        String end = simpleDateFormat.format(today.getTime());
        Set<String> holidaySet = holidayInfoMapper.selectHolidaySet(start, end);
        // 对时间的操作时间极快，可忽略不计
//        today.add(Calendar.DATE, - daySize);
        DeliveryDate deliveryDate = new DeliveryDate();
        Date date = DateUtil.date();
        deliveryDate.setDate(date);
        deliveryDate.setDay(DateUtil.format(date, "MM-dd"));
        deliveryDate.setFlag(holidaySet.contains(DateUtil.today()));
        deliveryDate.setTag("今日");
        if (date.getTime() < DateUtil.parse(deadTime).getTime()) {
            deliveryDate.setFlag(true);
        }
        deliveryDates.add(deliveryDate);
        for (int index = 1; index < daySize; index++) {
            deliveryDate = new DeliveryDate();
            date = DateUtil.offsetDay(DateUtil.date(), index);
            deliveryDate.setDate(date);
            deliveryDate.setDay(DateUtil.format(date, "MM-dd"));
            deliveryDate.setFlag(holidaySet.contains(DateUtil.format(date, DATE_FORMAT)));
            deliveryDate.setTag(dayOfWeek[DateUtil.dayOfWeek(date) - 1]);
            if (index == 1) {
                deliveryDate.setTag("明日");
            }
            deliveryDates.add(deliveryDate);
        }
        return deliveryDates;
    }

    @Override
    public List<HolidayInfo> listHoliday(String start, String end) {
        return holidayInfoMapper.selectHoliday(start, end);
    }

    @Override
    public HolidayInfo getHoliday(Date date) {
        return holidayInfoMapper.selectByDate(date);
    }

}
