package com.szhengzhu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.HolidayInfoMapper;
import com.szhengzhu.service.HolidayService;
import com.szhengzhu.util.TimeUtils;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {

    @Resource
    private HolidayInfoMapper holidayInfoMapper;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dayFormat = new SimpleDateFormat("MM-dd");
    
    private String[] dayOfWeek = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

    @Override
    public Result<Integer> addHolidays(List<Date> holidayList) {
        int count = holidayInfoMapper.insertBatch(holidayList);
        return new Result<>(count);
    }

    @Override
    public Result<?> operateHoliday(String holiday) throws ParseException {
        Date date = simpleDateFormat.parse(holiday);
        if (!date.after(new Date()))
            return new Result<>(StatusCode._4006);
        boolean isHoliday = holidayInfoMapper.countHoliday(date) > 0;
        if (isHoliday)
            holidayInfoMapper.deleteByPrimaryKey(date);
        else
            holidayInfoMapper.insert(new HolidayInfo(date));
        return new Result<>();
    }
    
    @Override
    public Result<List<DeliveryDate>> listDeliveryDate() {
        List<DeliveryDate> deliveryDates = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        String start = simpleDateFormat.format(today.getTime());
        today.add(Calendar.DATE, 8);
        String end = simpleDateFormat.format(today.getTime());
        Set<String> holidaySet = holidayInfoMapper.selectHolidaySet(start, end);
        today.add(Calendar.DATE, -8);  // 对时间的操作时间极快，可忽略不计
        DeliveryDate deliveryDate = null;
        for (int index = 0; index < 8; index++) {
            deliveryDate = new DeliveryDate();
            deliveryDate.setDate(today.getTime());
            deliveryDate.setDay(dayFormat.format(today.getTime()));
            deliveryDate.setFlag(holidaySet.contains(simpleDateFormat.format(today.getTime())));
            if (index == 0) {
                deliveryDate.setTag("今日");
                if (today.getTime().after(TimeUtils.getDeadline())) 
                    deliveryDate.setFlag(true);
            } else if (index == 1) {
                deliveryDate.setTag("明日");
            } else {
                deliveryDate.setTag(dayOfWeek[today.get(Calendar.DAY_OF_WEEK) - 1]);
            } 
            deliveryDates.add(deliveryDate);
            today.add(Calendar.DATE, 1);
        }
        return new Result<>(deliveryDates);
    }

    @Override
    public Result<List<HolidayInfo>> listHoliday(String start, String end) {
        List<HolidayInfo> holidayInfos = holidayInfoMapper.selectHoliday(start, end);
        return new Result<>(holidayInfos);
    }

    @Override
    public Result<HolidayInfo> getHoliday(Date date) {
        HolidayInfo holidayInfo = holidayInfoMapper.selectByDate(date);
        return new Result<>(holidayInfo);
    }

}
