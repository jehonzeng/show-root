package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.PrizeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author makejava
 * @since 2021-03-15 14:35:07
 */
public interface PrizeInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    PrizeInfo queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param prizeInfo 实例对象
     * @return 对象列表
     */
    List<PrizeInfo> queryAll(PrizeInfo prizeInfo);

    /**
     * 新增数据
     *
     * @param prizeInfo 实例对象
     */
    void add(PrizeInfo prizeInfo);

    /**
     * 修改数据
     *
     * @param prizeInfo 实例对象
     */
    void modify(PrizeInfo prizeInfo);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);

    List<PrizeInfo> selectUserReceive(@Param("userId") String userId, @Param("lotteryId") String lotteryId,
                                      @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
