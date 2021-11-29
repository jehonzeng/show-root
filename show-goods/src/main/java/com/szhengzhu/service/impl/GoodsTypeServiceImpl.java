package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.GoodsTypeMapper;
import com.szhengzhu.mapper.TypeSpecMapper;
import com.szhengzhu.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Resource
    private TypeSpecMapper typeSpecMapper;

    @Override
    public GoodsType addType(GoodsType base) {
        int count = goodsTypeMapper.repeatRecords(base.getTypeName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        goodsTypeMapper.insertSelective(base);
        return base;
    }

    @CheckGoodsChange
    @Override
    public GoodsType modifyGoodsType(GoodsType base) {
        String name = StrUtil.isEmpty(base.getTypeName()) ? "" : base.getTypeName();
        int count = goodsTypeMapper.repeatRecords(name, base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        goodsTypeMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<GoodsType> getPage(PageParam<GoodsType> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsType> page = new PageInfo<>(
                goodsTypeMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public List<Combobox> listCombobox() {
        return goodsTypeMapper.selectCombobox();
    }

    @Override
    public void addTypeSpec(String[] specIds, String typeId) {
        typeSpecMapper.insertBatch(typeId, specIds);
    }

    @Override
    public void removeTypeSpec(String typeId, String specId) {
        typeSpecMapper.deleteByPrimaryKey(typeId, specId);
    }

    @Override
    public void modifyTypeSpec(TypeSpec typeSpec) {
        if (Boolean.TRUE.equals(typeSpec.getDefaultOr())) {
            int count = typeSpecMapper.existDefault(typeSpec.getSpecificationId(), typeSpec.getTypeId());
            ShowAssert.checkTrue(count > 0, StatusCode._5008);
        }
        typeSpecMapper.updateByPrimaryKeySelective(typeSpec);
    }
}
