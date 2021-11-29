package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.IndentRemark;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @since 2021-03-01 10:46:03
 */
public interface IndentRemarkService {
    /**
     * 通过ID查询单条数据
     *
     * @param  markId 主键
     * @return 实例对象
     */
    IndentRemark queryById(String markId);

    /**
     * 通过实体作为筛选条件查询并分页
     *
     * @param param 分页对象
     * @return 对象列表
     */
    PageGrid<IndentRemark> queryByPage(PageParam<IndentRemark> param);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param indentRemark 实例对象
     * @return 对象列表
     */
    List<IndentRemark> queryByIndentId(IndentRemark indentRemark);

    /**
     * 新增数据
     *
     * @param indentRemark 实例对象
     * @return map集合
     */
    Map<String, Object> add(IndentRemark indentRemark);

    /**
     * 修改数据
     *
     * @param indentRemark 实例对象
     */
    void modify(IndentRemark indentRemark);
}
