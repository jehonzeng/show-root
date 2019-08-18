package com.szhengzhu.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.szhengzhu.service.HolidayService;

@Component
public class HolidaySchedule {
    
    @Resource
    private HolidayService holidayService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void autoInsert() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> weekList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int index = 1; index <= daySize; index++) {
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DATE);
                weekList.add(simpleDateFormat.parse(year + "-" + (month > 9? month : "0" + month) + "-" + (day > 9? day : "0" + day)));
            }
            calendar.add(Calendar.DATE, 1);
        }
        holidayService.addHolidays(weekList);
    }
}
