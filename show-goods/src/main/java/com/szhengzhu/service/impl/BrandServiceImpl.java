package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.BrandInfoMapper;
import com.szhengzhu.service.BrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandInfoMapper brandInfoMapper;

    @Override
    public BrandInfo addGoodsBrand(BrandInfo brandInfo) {
        int count = brandInfoMapper.repeatRecords(brandInfo.getCnName(), brandInfo.getEnName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        brandInfo.setMarkId(snowflake.nextIdStr());
        brandInfo.setBrandStatus(false);
        brandInfoMapper.insertSelective(brandInfo);
        return brandInfo;
    }

    @Override
    public BrandInfo editGoodsBrand(BrandInfo brandInfo) {
        String cnName = StrUtil.isEmpty(brandInfo.getCnName()) ? "" : brandInfo.getCnName();
        String enName = StrUtil.isEmpty(brandInfo.getEnName()) ? "" : brandInfo.getEnName();
        int count = brandInfoMapper.repeatRecords(cnName, enName, brandInfo.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        brandInfoMapper.updateByPrimaryKeySelective(brandInfo);
        return brandInfo;
    }

    @Override
    public PageGrid<BrandInfo> getPage(PageParam<BrandInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<BrandInfo> page = new PageInfo<>(
                brandInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public BrandInfo getBrandInfo(String markId) {
        return brandInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<Combobox> listCombobox() {
        return brandInfoMapper.selectCombobx();
    }

}
