package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.PrizeReceive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2021-03-15 14:35:07
 */
public interface PrizeReceiveMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    PrizeReceive queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @return 对象列表
     */
    List<PrizeReceive> queryAll(@Param("lotteryId") String lotteryId, @Param("userId") String userId);

    /**
     * 新增数据
     *
     * @param prizeReceive 实例对象
     */
    void add(PrizeReceive prizeReceive);

    /**
     * 修改数据
     *
     * @param prizeReceive 实例对象
     */
    void modify(PrizeReceive prizeReceive);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);

    /**
     * 查询中奖列表
     *
     * @return PrizeReceive 对象列表
     */
    List<PrizeReceive> selectReceive(@Param("lotteryId") String lotteryId);
}
