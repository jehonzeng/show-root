package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.GradeRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author makejava
 * @since 2021-05-12 15:13:03
 */
public interface GradeRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    GradeRecord queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param gradeRecord 实例对象
     * @return 对象列表
     */
    List<GradeRecord> queryAll(GradeRecord gradeRecord);

    /**
     * 新增数据
     *
     * @param gradeRecord 实例对象
     */
    void add(GradeRecord gradeRecord);

    /**
     * 修改数据
     *
     * @param gradeRecord 实例对象
     */
    void modify(GradeRecord gradeRecord);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);

    /**
     * 返回会员的成长值
     *
     * @return BigDecimal
     */
    BigDecimal consumeTotal(@Param("memberId") String memberId);

    GradeRecord selectIndentId(@Param("indentId") String indentId);
}
