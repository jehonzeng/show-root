package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.AttributeInfoMapper;
import com.szhengzhu.service.AttributeService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("attributeService")
public class AttributeServiceImpl implements AttributeService {
    
    @Resource
    private AttributeInfoMapper attributeMapper;

    @Override
    public Result<AttributeInfo> saveAttribute(AttributeInfo attributeInfo) {
        if (attributeInfo == null || StringUtils.isEmpty(attributeInfo.getCode()) || StringUtils.isEmpty(attributeInfo.getName())) {
            return new Result<>(StatusCode._4004);
        }
        int count = attributeMapper.countAttribute(attributeInfo.getCode(), attributeInfo.getName(), "0");
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        attributeInfo.setMarkId(IdGenerator.getInstance().nexId());
        attributeMapper.insertSelective(attributeInfo);
        return new Result<>(attributeInfo);
    }

    @Override
    public Result<AttributeInfo> updateAttribute(AttributeInfo attributeInfo) {
        if (attributeInfo == null || attributeInfo.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        int count = attributeMapper.countAttribute(attributeInfo.getCode(), attributeInfo.getName(), attributeInfo.getMarkId());
        if (count > 0) {
            return new Result<>(StatusCode._4007);
        }
        attributeMapper.updateByPrimaryKeySelective(attributeInfo);
        return new Result<>(attributeInfo);
    }

    @Override
    public Result<AttributeInfo> getAttributeById(String markId) {
        AttributeInfo attributeInfo = attributeMapper.selectByPrimaryKey(markId);
        return new Result<>(attributeInfo);
    }

    @Override
    public Result<PageGrid<AttributeInfo>> pageAttribute(PageParam<AttributeInfo> attrPage) {
        PageHelper.startPage(attrPage.getPageIndex(), attrPage.getPageSize());
        PageHelper.orderBy(attrPage.getSidx() + " " + attrPage.getSort());
        PageInfo<AttributeInfo> pageInfo = new PageInfo<>(attributeMapper.selectByExampleSelective(attrPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<List<Combobox>> listCombobox(String type) {
        List<Combobox> comboboxs = attributeMapper.selectCombobox(type);
        return new Result<>(comboboxs);
    }

}
