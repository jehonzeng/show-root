package com.szhengzhu.service;

import com.szhengzhu.bean.member.LotteryInfo;
import com.szhengzhu.bean.member.MemberLottery;
import com.szhengzhu.bean.member.PrizeInfo;
import com.szhengzhu.bean.member.PrizeReceive;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author makejava
 * @since 2021-03-15 14:35:07
 */
public interface LotteryService {

    MemberLottery selectLotteryByType(Integer type, Integer status);

    /**
     * 通过实体对象筛选查询
     *
     * @return 对象
     */
    MemberLottery queryLottery(String markId);

    /**
     * 新增数据
     *
     * @param memberLottery 实例对象
     */
    void addLottery(MemberLottery memberLottery);

    /**
     * 通过ID查询奖品信息
     *
     * @param markId 主键
     * @return 实例对象
     */
    PrizeInfo queryPrizeById(String markId);

    /**
     * 通过实体作为筛选条件查询奖品信息
     *
     * @param prizeInfo 实例对象
     * @return 对象列表
     */
    List<PrizeInfo> queryAllPrize(PrizeInfo prizeInfo);

    /**
     * 新增奖品信息
     *
     * @param prizeInfo 实例对象
     */
    void addPrize(PrizeInfo prizeInfo);

    /**
     * 修改奖品信息
     *
     * @param prizeInfo 实例对象
     */
    void modifyPrize(PrizeInfo prizeInfo);

    /**
     * 通过ID查询奖品领取信息
     *
     * @param markId 主键
     * @return 实例对象
     */
    PrizeReceive queryReceiveById(String markId);

    /**
     * 通过实体作为筛选条件查询奖品领取信息
     *
     * @param param 分页对象
     * @return 分页列表
     */
    PageGrid<PrizeReceive> queryReceiveAll(PageParam<PrizeReceive> param);

    /**
     * 新增数据奖品领取信息
     *
     * @param prizeReceive 实例对象
     */
    void addReceive(PrizeReceive prizeReceive);

    /**
     * 修改数据奖品领取信息
     *
     * @param prizeReceive 实例对象
     */
    void modifyReceive(PrizeReceive prizeReceive);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deletePrizeById(String markId);

    List<PrizeInfo> selectUserReceive(MemberLottery lottery, String userId);

    /**
     * 通过主键ID查询单条数据
     *
     * @param lotteryId
     * @return 实例对象
     */
    LotteryInfo queryInfoById(String lotteryId);

    /**
     * 新增数据
     *
     * @param lotteryInfo 实例对象
     */
    void addLotteryInfo(LotteryInfo lotteryInfo);

    /**
     * 修改数据
     *
     * @param lotteryInfo 实例对象
     */
    void modifyLotteryInfo(LotteryInfo lotteryInfo);

    List<PrizeReceive> selectReceive();
}

