package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.IndentRemark;
import java.util.List;

/**
 * @author makejava
 * @since 2021-03-01 10:46:05
 */
public interface IndentRemarkMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     IndentRemark queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param indentRemark 实例对象
     * @return 对象列表
     */
    List<IndentRemark> query(IndentRemark indentRemark);

    /**
     * 新增数据
     *
     * @param indentRemark 实例对象
     * @return 影响行数
     */
    void add(IndentRemark indentRemark);

    /**
     * 修改数据
     *
     * @param indentRemark 实例对象
     */
    void modify(IndentRemark indentRemark);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
