package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.LotteryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2021-11-17 17:32:26
 */
public interface LotteryInfoMapper {

    /**
     * 通过主键ID查询单条数据
     *
     * @param lotteryId
     * @return 实例对象
     */
     LotteryInfo queryById(@Param("lotteryId") String lotteryId);

    /**
     * 通过实体对象筛选查询
     *
     * @param lotteryInfo 实例对象
     * @return 对象列表
     */
    List<LotteryInfo> queryAll(LotteryInfo lotteryInfo);

    /**
     * 新增数据
     *
     * @param lotteryInfo 实例对象
     */
    void add(LotteryInfo lotteryInfo);

    /**
     * 修改数据
     *
     * @param lotteryInfo 实例对象
     */
    void modify(LotteryInfo lotteryInfo);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
