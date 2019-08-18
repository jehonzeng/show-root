package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.BrandInfoMapper;
import com.szhengzhu.service.BrandService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandInfoMapper brandInfoMapper;

    @Override
    public Result<?> addGoodsBrand(BrandInfo brandInfo) {
        if (brandInfo==null || StringUtils.isEmpty(brandInfo.getCnName())
                || StringUtils.isEmpty(brandInfo.getEnName())) 
            return new Result<>(StatusCode._4004);
        int count = brandInfoMapper.repeatRecords(brandInfo.getCnName(), brandInfo.getEnName(), "");
        if (count > 0) 
            return new Result<>(StatusCode._4007);
        brandInfo.setMarkId(IdGenerator.getInstance().nexId());
        brandInfo.setBrandStatus(false);
        brandInfoMapper.insertSelective(brandInfo);
        return new Result<>(brandInfo);
    }

    @Override
    public Result<?> editGoodsBrand(BrandInfo brandInfo) {
        if (brandInfo == null || brandInfo.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        String cnName = brandInfo.getCnName() == null ? "" : brandInfo.getCnName();
        String enName = brandInfo.getEnName() == null ? "" : brandInfo.getEnName();
        int count = brandInfoMapper.repeatRecords(cnName, enName, brandInfo.getMarkId());
        if (count > 0) 
            return new Result<>(StatusCode._4007);
        brandInfoMapper.updateByPrimaryKeySelective(brandInfo);
        return new Result<>(brandInfo);
    }

    @Override
    public Result<PageGrid<BrandInfo>> getPage(PageParam<BrandInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<BrandInfo> page = new PageInfo<>(
                brandInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getBrandInfo(String markId) {
        BrandInfo brandInfo = brandInfoMapper.selectByPrimaryKey(markId);
        return new Result<>(brandInfo);
    }

    @Override
    public Result<List<Combobox>> listCombobox() {
        List<Combobox> comboboxs = brandInfoMapper.selectCombobx();
        return new Result<>(comboboxs);
    }

}
