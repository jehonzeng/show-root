package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.SpecificationInfoMapper;
import com.szhengzhu.service.SpecificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("specificationService")
public class SpecificationServiceImpl implements SpecificationService {

    @Resource
    private SpecificationInfoMapper specificationInfoMapper;

    @CheckGoodsChange
    @Override
    public SpecificationInfo addSpecification(SpecificationInfo base) {
        int count = specificationInfoMapper.repeatRecords(base.getAttrValue(), base.getAttrName(),
                "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        specificationInfoMapper.insertSelective(base);
        return base;
    }

    @CheckGoodsChange
    @Override
    public SpecificationInfo modifySpecification(SpecificationInfo base) {
        String attrValue = StrUtil.isEmpty(base.getAttrValue()) ? "" : base.getAttrValue();
        String attrName = StrUtil.isEmpty(base.getAttrName()) ? "" : base.getAttrName();
        int count = specificationInfoMapper.repeatRecords(attrValue, attrName, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        specificationInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<SpecificationInfo> getPage(PageParam<SpecificationInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<SpecificationInfo> page = new PageInfo<>(
                specificationInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @CheckGoodsChange
    @Override
    public SpecBatchVo insertBatchSpec(SpecBatchVo info) {
        List<SpecificationInfo> list = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String attrValue : info.getAttrValues()) {
            SpecificationInfo base = SpecificationInfo.builder().markId(snowflake.nextIdStr()).attrName(info.getAttrName())
                    .serverStatus(false).attrValue(attrValue).build();
            list.add(base);
        }
        if (!list.isEmpty()) {
            specificationInfoMapper.insertBatch(list);
        }
        return info;
    }

    @Override
    public List<SpecChooseBox> getSpecList(String goodsId) {
        return specificationInfoMapper.selectNameByGoodsId(goodsId);
    }

    @Override
    public PageGrid<SpecificationInfo> pageInByType(PageParam<SpecificationInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<SpecificationInfo> page = new PageInfo<>(
                specificationInfoMapper.selectInByType(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public PageGrid<SpecificationInfo> pageNotInByType(
            PageParam<SpecificationInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<SpecificationInfo> page = new PageInfo<>(
                specificationInfoMapper.selectNotInByType(base.getData()));
        return new PageGrid<>(page);
    }
}
