package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.bean.ordering.param.BookingParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BookingMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId id
     * @param status 状态
     * @return 实例对象
     */
    Booking selectById(String markId,@Param("status") Integer status);

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
     * @return 影响行数
     */
    int add(Booking booking);

    /**
     * 修改数据
     *
     * @param booking 实例对象
     * @return 影响行数
     */
    int modify(Booking booking);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(String markId);

    /**
     * 修改预订时间超过当前时间的信息状态
     *
     * @return
     */

    int modifyStatus(Booking booking);
}
