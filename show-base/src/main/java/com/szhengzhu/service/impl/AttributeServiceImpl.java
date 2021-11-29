package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.AreaInfoMapper;
import com.szhengzhu.mapper.AttributeInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.AttributeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("attributeService")
public class AttributeServiceImpl implements AttributeService {
    
    @Resource
    private AttributeInfoMapper attributeMapper;
    
    @Resource
    private AreaInfoMapper areaInfoMapper;
    
    @Resource
    private Redis redis;

    @Override
    public AttributeInfo saveAttribute(AttributeInfo attributeInfo) {
        int count = attributeMapper.countAttribute(attributeInfo.getCode(), attributeInfo.getName(), "0");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        attributeInfo.setMarkId(snowflake.nextIdStr());
        attributeMapper.insertSelective(attributeInfo);
        return attributeInfo;
    }

    @Override
    public AttributeInfo updateAttribute(AttributeInfo attributeInfo) {
        int count = attributeMapper.countAttribute(attributeInfo.getCode(), attributeInfo.getName(), attributeInfo.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        attributeMapper.updateByPrimaryKeySelective(attributeInfo);
        if ("AV".equals(attributeInfo.getType())) {
            List<AreaInfo> areaList = areaInfoMapper.selectAll();
            redis.set("base:area:list", areaList, 7L * 24 * 60 * 60);
        } 
        return attributeInfo;
    }

    @Override
    public AttributeInfo getAttributeById(String markId) {
        return attributeMapper.selectByPrimaryKey(markId);
    }

    @Override
    public PageGrid<AttributeInfo> pageAttribute(PageParam<AttributeInfo> attrPage) {
        PageMethod.startPage(attrPage.getPageIndex(), attrPage.getPageSize());
        PageMethod.orderBy(attrPage.getSidx() + " " + attrPage.getSort());
        PageInfo<AttributeInfo> pageInfo = new PageInfo<>(attributeMapper.selectByExampleSelective(attrPage.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<Combobox> listCombobox(String type) {
        return attributeMapper.selectCombobox(type);
    }

    @Override
    public String getCodeByName(String name) {
        return attributeMapper.selectByName(name);
    }

}
