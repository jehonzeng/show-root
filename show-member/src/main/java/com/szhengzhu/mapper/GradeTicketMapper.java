package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.GradeTicket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2021-04-21 10:57:10
 */
public interface GradeTicketMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param gradeId 主键
     * @return 实例对象
     */
    List<GradeTicket> queryById(String gradeId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param gradeTicket 实例对象
     * @return 对象列表
     */
    List<GradeTicket> queryAll(GradeTicket gradeTicket);

    /**
     * 新增数据
     *
     * @param gradeTicket 实例对象
     */
    void add(GradeTicket gradeTicket);

    /**
     * 修改数据
     *
     * @param gradeTicket 实例对象
     */
    void modify(GradeTicket gradeTicket);

    /**
     * 通过主键删除数据
     *
     * @param gradeId 主键
     */
    void deleteById(String gradeId);

    /**
     * 批量添加
     *
     * @param gradeId
     * @param tickets
     */
    void addBatchGradeTicket(@Param("gradeId") String gradeId, @Param("tickets") List<GradeTicket> tickets);
}
