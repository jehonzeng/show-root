package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MemberLottery;
import org.apache.ibatis.annotations.Param;

/**
 * @author makejava
 * @since 2021-06-16 12:15:54
 */
public interface MemberLotteryMapper {

    MemberLottery selectLotteryByType(@Param("type") Integer type,@Param("status") Integer status);

    /**
     * 通过主键ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    MemberLottery queryById(String markId);

    /**
     * 新增数据
     *
     * @param memberLottery 实例对象
     */
    void add(MemberLottery memberLottery);

    /**
     * 修改数据
     *
     * @param memberLottery 实例对象
     */
    void modify(MemberLottery memberLottery);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);
}
