package com.szhengzhu.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.core.Result;

public interface HolidayService {

    /**
     * 批量添加
     * 
     * @date 2019年3月26日 下午5:08:47
     * @param holidayList
     */
    Result<Integer> addHolidays(List<Date> holidayList);
    
    /**
     * 添加或刪除节假日
     * 
     * @date 2019年3月26日 下午6:51:48
     * @param holiday
     * @return
     */
    Result<?> operateHoliday(String holiday) throws ParseException;
    
    /**
     * 获取配送时间列表
     * 
     * @date 2019年3月26日 下午7:12:36
     * @return
     */
    Result<List<DeliveryDate>> listDeliveryDate();
    
    /**
     * 获取某时间段的假期
     * 
     * @date 2019年3月27日 下午12:31:06
     * @param start
     * @param end
     * @return
     */
    Result<List<HolidayInfo>> listHoliday(String start, String end);
    
    /**
     * 查询某一日期，作为判断该日期是否可配送
     * 
     * @date 2019年7月22日 下午5:14:23
     * @param date
     * @return
     */
    Result<HolidayInfo> getHoliday(Date date);
}
