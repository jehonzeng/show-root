package com.szhengzhu.mapper;

import com.szhengzhu.bean.order.PushInfo;
import com.szhengzhu.bean.order.PushType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author makejava
 * @since 2021-08-16 11:02:11
 */
public interface PushTypeMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
     PushType queryById(String markId);

    /**
     * 通过实体对象筛选查询
     *
     * @param pushType 实例对象
     * @return 对象列表
     */
    List<PushType> queryAll(PushType pushType);

    /**
     * 新增数据
     *
     * @param pushType 实例对象
     */
    void add(PushType pushType);

    /**
     * 修改数据
     *
     * @param pushType 实例对象
     */
    void modify(PushType pushType);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
