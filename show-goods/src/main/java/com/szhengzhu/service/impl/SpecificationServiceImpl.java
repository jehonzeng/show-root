package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.SpecificationInfoMapper;
import com.szhengzhu.service.SpecificationService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("specificationService")
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationInfoMapper specificationInfoMapper;

    @Override
    public Result<?> addSpecification(SpecificationInfo base) {
        if (base == null || StringUtils.isEmpty(base.getAttrName())
                || StringUtils.isEmpty(base.getAttrValue())) {
            return new Result<>(StatusCode._4004);
        }
        int count = specificationInfoMapper.repeatRecords(base.getAttrValue(), base.getAttrName(),
                "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        specificationInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> editSpecification(SpecificationInfo base) {
        if (base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        String attrValue = base.getAttrValue() == null ? "" : base.getAttrValue();
        String attrName = base.getAttrName() == null ? "" : base.getAttrName();
        int count = specificationInfoMapper.repeatRecords(attrValue, attrName, base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        specificationInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<SpecificationInfo>> getPage(PageParam<SpecificationInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<SpecificationInfo> page = new PageInfo<>(
                specificationInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> insertBatchSpec(SpecBatchVo info) {
        List<SpecificationInfo> list = new LinkedList<>();
        IdGenerator generator = IdGenerator.getInstance();
        for (String attrValue : info.getAttrValues()) {
            SpecificationInfo base = new SpecificationInfo();
            base.setMarkId(generator.nexId());
            base.setAttrName(info.getAttrName());
            base.setServerStatus(false);
            base.setAttrValue(attrValue);
            list.add(base);
        }
        if (list.size() > 0) {
            specificationInfoMapper.insertBatch(list);
        }
        return new Result<>(info);
    }

    @Override
    public Result<?> getSpecList(String goodsId) {
        List<SpecChooseBox> list = specificationInfoMapper.selectNameByGoodsId(goodsId);
        return new Result<>(list);
    }

    @Override
    public Result<PageGrid<SpecificationInfo>> pageInByType(PageParam<SpecificationInfo> base) {
        if (base == null || base.getData() == null
                || StringUtils.isEmpty(base.getData().getTypeId()))
            return new Result<>(StatusCode._4004);
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<SpecificationInfo> page = new PageInfo<>(
                specificationInfoMapper.selectInByType(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<PageGrid<SpecificationInfo>> pageNotInByType(
            PageParam<SpecificationInfo> base) {
        if (base == null || base.getData() == null
                || StringUtils.isEmpty(base.getData().getTypeId()))
            return new Result<>(StatusCode._4004);
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<SpecificationInfo> page = new PageInfo<>(
                specificationInfoMapper.selectNotInByType(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }
}
