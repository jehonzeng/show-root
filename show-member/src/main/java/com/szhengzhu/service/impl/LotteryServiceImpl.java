package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.code.LotteryTypeCode;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.LotteryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author makejava
 * @since 2021-03-15 14:35:07
 */
@Service("lotteryService")
public class LotteryServiceImpl implements LotteryService {

    @Resource
    private PrizeInfoMapper prizeInfoMapper;

    @Resource
    private PrizeReceiveMapper prizeReceiveMapper;

    @Resource
    private MemberLotteryMapper memberLotteryMapper;

    @Resource
    private LotteryInfoMapper lotteryInfoMapper;

    @Override
    public MemberLottery selectLotteryByType(Integer type, Integer status) {
        return memberLotteryMapper.selectLotteryByType(type, status);
    }

    @Override
    public MemberLottery queryLottery(String markId) {
        return memberLotteryMapper.queryById(markId);
    }

    @Override
    public void addLottery(MemberLottery memberLottery) {
        if (ObjectUtil.isEmpty(memberLottery.getMarkId())) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            memberLottery.setMarkId(snowflake.nextIdStr());
            memberLottery.setCreateTime(DateUtil.date());
            memberLottery.setStatus(true);
            memberLotteryMapper.add(memberLottery);
        } else {
            memberLottery.setMarkId(memberLottery.getMarkId());
            memberLottery.setModifyTime(DateUtil.date());
            memberLotteryMapper.modify(memberLottery);
        }
    }

    @Override
    public PrizeInfo queryPrizeById(String markId) {
        return prizeInfoMapper.queryById(markId);
    }

    @Override
    public List<PrizeInfo> queryAllPrize(PrizeInfo prizeInfo) {
        return prizeInfoMapper.queryAll(prizeInfo);
    }

    @Override
    public void addPrize(PrizeInfo prizeInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        prizeInfo.setMarkId(snowflake.nextIdStr());
        prizeInfo.setStatus(1);
        prizeInfo.setCreateTime(DateUtil.date());
        prizeInfoMapper.add(prizeInfo);
        multiple(prizeInfo.getProbability(), prizeInfo.getLotteryId());
    }

    @Override
    public void modifyPrize(PrizeInfo prizeInfo) {
        prizeInfo.setModifyTime(DateUtil.date());
        prizeInfoMapper.modify(prizeInfo);
        multiple(prizeInfo.getProbability(), prizeInfo.getLotteryId());
    }

    public void multiple(BigDecimal probability, String markId) {
        List<PrizeInfo> prizeInfoList = prizeInfoMapper.queryAll(PrizeInfo.builder().lotteryId(markId).build());
        BigDecimal probabilityNum = probability;
        for (PrizeInfo info : prizeInfoList) {
            if (info.getProbability().compareTo(probabilityNum) == -1) {
                probabilityNum = (info.getProbability()).stripTrailingZeros();
            }
        }
        if (!(probabilityNum.compareTo(BigDecimal.ZERO) == 0)) {
            StringBuilder multiple = new StringBuilder();
            int decimalPlaces = probabilityNum.toString().substring(probabilityNum.toString().indexOf(".")).length();
            for (int i = 1; i <= decimalPlaces; i++) {
                multiple.append(i == 1 ? 1 : 0);
            }
            memberLotteryMapper.modify(MemberLottery.builder().markId(markId).multiple(Integer.valueOf(multiple.toString())).
                    modifyTime(DateUtil.date()).build());
        }
    }

    @Override
    public PrizeReceive queryReceiveById(String markId) {
        return prizeReceiveMapper.queryById(markId);
    }

    @Override
    public PageGrid<PrizeReceive> queryReceiveAll(PageParam<PrizeReceive> param) {
        MemberLottery lottery = memberLotteryMapper.selectLotteryByType(LotteryTypeCode.FRESH_LOTTERY.code, 1);
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<PrizeReceive> pageInfo = new PageInfo<>(prizeReceiveMapper.queryAll(lottery.getMarkId(), param.getData().getUserId()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addReceive(PrizeReceive prizeReceive) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        prizeReceive.setMarkId(snowflake.nextIdStr());
        prizeReceive.setCreateTime(DateUtil.date());
        prizeReceiveMapper.add(prizeReceive);
    }

    @Override
    public void modifyReceive(PrizeReceive prizeReceive) {
        prizeReceive.setModifyTime(DateUtil.date());
        prizeReceiveMapper.modify(prizeReceive);
    }

    @Override
    public void deletePrizeById(String markId) {
        prizeInfoMapper.deleteById(markId);
    }

    @Override
    public List<PrizeInfo> selectUserReceive(MemberLottery lottery, String userId) {
        return prizeInfoMapper.selectUserReceive(userId, lottery.getMarkId(), lottery.getStartTime(), lottery.getEndTime());
    }

    @Override
    public LotteryInfo queryInfoById(String lotteryId) {
        MemberLottery memberLottery = memberLotteryMapper.queryById(lotteryId);
        LotteryInfo lotteryInfo = lotteryInfoMapper.queryById(lotteryId);
        lotteryInfo.setStartTime(memberLottery.getStartTime());
        lotteryInfo.setEndTime(memberLottery.getEndTime());
        return lotteryInfo;
    }

    @Override
    public void addLotteryInfo(LotteryInfo lotteryInfo) {
        LotteryInfo info = lotteryInfoMapper.queryById(lotteryInfo.getLotteryId());
        ShowAssert.checkTrue(ObjectUtil.isNotEmpty(info), StatusCode._5049);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        lotteryInfo.setMarkId(snowflake.nextIdStr());
        lotteryInfo.setCreateTime(DateUtil.date());
        lotteryInfoMapper.add(lotteryInfo);
    }

    @Override
    public void modifyLotteryInfo(LotteryInfo lotteryInfo) {
        lotteryInfoMapper.modify(lotteryInfo);
    }

    @Override
    public List<PrizeReceive> selectReceive() {
        MemberLottery lottery = memberLotteryMapper.selectLotteryByType(LotteryTypeCode.FRESH_LOTTERY.code, 1);
        return prizeReceiveMapper.selectReceive(lottery.getMarkId());
    }
}
