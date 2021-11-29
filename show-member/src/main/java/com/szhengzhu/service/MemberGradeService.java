package com.szhengzhu.service;

import com.szhengzhu.bean.member.GradeRecord;
import com.szhengzhu.bean.member.GradeTicket;
import com.szhengzhu.bean.member.MemberGrade;
import com.szhengzhu.bean.member.MemberGradeShow;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author makejava
 * @since 2021-04-21 10:57:10
 */
public interface MemberGradeService {

    /**
     * 通过ID查询单条数据
     *
     * @param  markId 主键
     * @return 实例对象
     */
    MemberGrade queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param memberGrade 实例对象
     * @return 对象列表
     */
     List<MemberGrade> queryAll(MemberGrade memberGrade);

    /**
     * 新增数据
     *
     * @param memberGrade 实例对象
     */
    void add(MemberGrade memberGrade);

    /**
     * 修改数据
     *
     * @param memberGrade 实例对象
     */
    void modify(MemberGrade memberGrade);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
   void deleteById(String markId);

    List<MemberGradeShow> memberGradeShow(String userId);

    List<GradeTicket> queryGradeTicket(String gradeId);

    MemberGrade selectByGradeId(Integer amount);

    PageGrid<GradeRecord> queryGradeRecord(PageParam<GradeRecord> param);
}
