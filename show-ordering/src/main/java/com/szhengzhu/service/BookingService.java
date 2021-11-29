package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.bean.ordering.param.BookingParam;

import java.util.List;

public interface BookingService {
    /**
     * 通过ID查询单条数据
     *
     * @param booking 实例对象
     * @return 实例对象
     */
    List<Booking> selectInfo(BookingParam booking);

    /**
     * 新增数据
     *
     * @param booking 实例对象
     */
    void add(Booking booking);

    /**
     * 修改数据
     *
     * @param booking 实例对象
     */
    void modify(Booking booking);
}
