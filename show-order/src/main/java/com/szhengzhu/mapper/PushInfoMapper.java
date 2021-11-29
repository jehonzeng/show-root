package com.szhengzhu.mapper;

import com.szhengzhu.bean.order.PushInfo;

import java.util.List;

/**
 * @author makejava
 * @since 2021-08-06 10:28:36
 */
public interface PushInfoMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     PushInfo queryById(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param pushInfo 实例对象
     * @return 对象列表
     */
    List<PushInfo> queryAll(PushInfo pushInfo);

    /**
     * 新增数据
     *
     * @param pushInfo 实例对象
     */
    void add(PushInfo pushInfo);

    /**
     * 修改数据
     *
     * @param pushInfo 实例对象
     */
    void modify(PushInfo pushInfo);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);

    void deleteByTemplateId(String templateId);

    PushInfo queryPushTemplate(String modalId, String typeId);
}
