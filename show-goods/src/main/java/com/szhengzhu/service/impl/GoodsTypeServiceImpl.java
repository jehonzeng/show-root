package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsTypeMapper;
import com.szhengzhu.mapper.TypeSpecMapper;
import com.szhengzhu.service.GoodsTypeService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService{
    
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    
    @Autowired
    private TypeSpecMapper typeSpecMapper;

    @Override
    public Result<?> addType(GoodsType base) {
        if(base == null || StringUtils.isEmpty(base.getTypeName())) {
            return new Result<>(StatusCode._4004);
        }
        int count = goodsTypeMapper.repeatRecords(base.getTypeName(), "");
        if(count > 0 ) {
            return new Result<>(StatusCode._4007);
        }
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        goodsTypeMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> editType(GoodsType base) {
        if(base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        String name = base.getTypeName() == null ? "" : base.getTypeName();
        int count = goodsTypeMapper.repeatRecords(name, base.getMarkId());
        if (count > 0) {
            return new Result<String>(StatusCode._4007);
        }
        goodsTypeMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<GoodsType>> getPage(PageParam<GoodsType> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsType> page = new PageInfo<>(
                goodsTypeMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<List<Combobox>> listCombobox() {
        List<Combobox> comboboxs = goodsTypeMapper.selectCombobox();
        return new Result<>(comboboxs);
    }

    @Override
    public Result<?> addTypeSpec(String[] specIds, String typeId) {
        if (specIds.length < 1 || StringUtils.isEmpty(typeId))
            return new Result<>(StatusCode._4004);
        typeSpecMapper.insertBatch(typeId, specIds);
        return new Result<>();
    }

    @Override
    public Result<?> removeTypeSpec(String typeId, String specId) {
        if (StringUtils.isEmpty(specId) || StringUtils.isEmpty(typeId))
            return new Result<>(StatusCode._4004);
        typeSpecMapper.deleteByPrimaryKey(typeId, specId);
        return new Result<>();
    }

    @Override
    public Result<?> modifyTypeSpec(TypeSpec typeSpec) {
        if (typeSpec == null || StringUtils.isEmpty(typeSpec.getTypeId()) || StringUtils.isEmpty(typeSpec.getSpecificationId()))
            return new Result<>(StatusCode._4004);
        if (typeSpec.getDefaultOr()) {
            int count = typeSpecMapper.existDefault(typeSpec.getSpecificationId(), typeSpec.getTypeId());
            if (count > 0)
                return new Result<>(StatusCode._5008);
        }
        typeSpecMapper.updateByPrimaryKeySelective(typeSpec);
        return new Result<>();
    }
}
