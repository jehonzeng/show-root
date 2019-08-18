package com.szhengzhu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.SeckillInfoMapper;
import com.szhengzhu.service.SeckillService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private SeckillInfoMapper seckillMapper;

    @Override
    public Result<PageGrid<SeckillInfo>> pageSeckill(PageParam<SeckillInfo> seckillPage) {
        PageHelper.startPage(seckillPage.getPageIndex(), seckillPage.getPageSize());
        PageHelper.orderBy(seckillPage.getSidx() + " " + seckillPage.getSort());
        PageInfo<SeckillInfo> pageInfo = new PageInfo<>(seckillMapper.selectByExampleSelective(seckillPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<SeckillInfo> saveSeckill(SeckillInfo seckillInfo) {
        if (seckillInfo == null || StringUtils.isEmpty(seckillInfo.getProductId())
                || StringUtils.isEmpty(seckillInfo.getTheme()) || seckillInfo.getPrice() == null
                || seckillInfo.getStartTime() == null || seckillInfo.getStopTime() == null
                || (seckillInfo.getStartTime().getTime() > seckillInfo.getStopTime().getTime())) {
            return new Result<>(StatusCode._4004);
        }
        seckillInfo.setMarkId(IdGenerator.getInstance().nexId());
        seckillInfo.setServerStatus(false);
        seckillMapper.insertSelective(seckillInfo);
        return new Result<>(seckillInfo);
    }

    @Override
    public Result<SeckillInfo> updateSeckill(SeckillInfo seckillInfo) {
        if (seckillInfo == null || seckillInfo.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        seckillMapper.updateByPrimaryKeySelective(seckillInfo);
        return new Result<>();
    }

    @Override
    public Result<SeckillInfo> getSeckillInfo(String markId) {
        if (StringUtils.isEmpty(markId))
            return new Result<>(StatusCode._4004);
        SeckillInfo seckillInfo = seckillMapper.selectByPrimaryKey(markId);
        return new Result<>(seckillInfo);
    }

}
