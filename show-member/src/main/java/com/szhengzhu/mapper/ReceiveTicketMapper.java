package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.ReceiveTicket;
import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2021-04-19 11:12:30
 */
public interface ReceiveTicketMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param receiveId 主键
     * @return 对象列表
     */
    List<ReceiveTicket> queryById(String receiveId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param receiveTicket 实例对象
     * @return 对象列表
     */
    List<ReceiveTicket> queryAll(ReceiveTicket receiveTicket);

    /**
     * 新增数据
     *
     * @param receiveTicket 实例对象
     */
    void add(ReceiveTicket receiveTicket);

    /**
     * 批量新增数据
     *
     * @param receiveId
     * @param tickets
     */
    void addBatchTicket(@Param("receiveId") String receiveId,
                        @Param("tickets") List<ReceiveTicket> tickets);

    /**
     * 修改数据
     *
     * @param receiveTicket 实例对象
     */
    void modify(ReceiveTicket receiveTicket);

    /**
     * 通过主键删除数据
     *
     * @param receiveId 主键
     */
    void deleteById(String receiveId);
}
