package com.szhengzhu.service;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author Administrator
 */
public interface ReceiveService {

    /**
     * 修改待领取信息
     *
     * @param pendDishes 实例对象
     */
    void modifyPend(PendDishes pendDishes);

    /**
     * 查询待领取信息
     *
     * @param pendDishes 实例对象
     * @return 对象列表
     */
    List<PendDishes> queryPend(PendDishes pendDishes);

    /**
     * 查询领取菜品的信息
     *
     * @param param 实例对象
     * @return 对象列表
     */
    PageGrid<ReceiveVo> queryReceive(PageParam<ReceiveDishes> param);

    /**
     * 新增领取菜品的信息
     *
     * @param receiveDishes 实例对象
     */
    void addReceive(ReceiveDishes receiveDishes);

    /**
     * 修改领取菜品的信息
     *
     * @param receiveDishes 实例对象
     */
    void modifyReceive(ReceiveDishes receiveDishes);

    /**
     * 查询领取记录信息
     *
     * @param receiveRecord 实例对象
     * @return 对象列表
     */
    List<ReceiveRecord> queryRecord(ReceiveRecord receiveRecord);

    /**
     * 修改领取记录信息
     *
     * @param receiveRecord 实例对象
     */
    void modifyRecord(ReceiveRecord receiveRecord);

    /**
     * 查询领取菜品的详细信息
     *
     * @param markId 主键
     * @return 对象列表
     */
    List<ReceiveVo> selectByDish(String markId);

    /**
     * 领取菜品券
     *
     * @param markId 主键
     */
    void receiveTicket(String markId);
}
