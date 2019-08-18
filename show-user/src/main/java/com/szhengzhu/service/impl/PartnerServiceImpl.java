package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.PartnerInfoMapper;
import com.szhengzhu.service.PartnerService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

    @Resource
    private PartnerInfoMapper partnerInfoMapper;

    @Override
    public Result<?> addPartner(PartnerInfo base) {
        if (base == null || StringUtils.isEmpty(base.getName()))
            return new Result<>(StatusCode._4004);
        int count = partnerInfoMapper.repeatRecords(base.getName(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setAddTime(TimeUtils.today());
        partnerInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> editPartner(PartnerInfo base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        int count = partnerInfoMapper.repeatRecords(base.getName(), base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        partnerInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> getPartnerPage(PageParam<PartnerInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        List<PartnerInfo> list = partnerInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<PartnerInfo> page = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> deletePartner(String markId) {
        partnerInfoMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }
}
