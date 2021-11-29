package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.CounselInfo;
import com.szhengzhu.bean.base.ProductContent;
import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.bean.vo.NewsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.CounselInfoMapper;
import com.szhengzhu.mapper.ProductContentMapper;
import com.szhengzhu.mapper.ProductInfoMapper;
import com.szhengzhu.service.WebsiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("websiteService")
public class WebsiteServiceImpl implements WebsiteService {

    @Resource
    private CounselInfoMapper counselInfoMapper;

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private ProductContentMapper productContentMapper;

    @Override
    public PageGrid<ProductInfo> getProductPage(PageParam<ProductInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("add_time desc,"+base.getSidx() + " " + base.getSort());
        List<ProductInfo> list = productInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<ProductInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public ProductInfo addProductInfo(ProductInfo base) {
        int count = productInfoMapper.repeatRecords(base.getName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        base.setAddTime(DateUtil.date());
        base.setServerStatus(false);
        productInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public ProductInfo editProductInfo(ProductInfo base) {
        int count = productInfoMapper.repeatRecords(base.getName(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        productInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public ProductContent getContent(String productId) {
        ProductContent content = productContentMapper.selectByProductId(productId);
        if (ObjectUtil.isNotNull(content)) { return content; }
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        content = ProductContent.builder().markId(snowflake.nextIdStr()).productId(productId).content("").build();
        productContentMapper.insertOrUpdate(content);
        return content;
    }

    @Override
    public ProductContent editContent(ProductContent base) {
        ProductContent old = productContentMapper.selectByProductId(base.getProductId());
        base.setMarkId(old.getMarkId());
        productContentMapper.insertOrUpdate(base);
        return base;
    }

    @Override
    public PageGrid<CounselInfo> getCounselPage(PageParam<CounselInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        List<CounselInfo> list = counselInfoMapper.selectByExampleSelective(base.getData());
        PageInfo<CounselInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public void addConselInfo(CounselInfo base) {
        counselInfoMapper.insertSelective(base);
    }

    @Override
    public List<ProductInfo> getGoodsList() {
        return productInfoMapper.selectProductListByType(0);
    }

    @Override
    public PageGrid<ProductInfo> getNewList(Integer pageNo, Integer pageSize) {
        PageMethod.startPage(pageNo, pageSize);
        List<ProductInfo> list = productInfoMapper.selectProductListByType(1);
        PageInfo<ProductInfo> page = new PageInfo<>(list);
        return new PageGrid<>(page);
    }

    @Override
    public NewsVo getNewsDetail(String markId) {
        return productInfoMapper.selectNewsById(markId);
    }

    @Override
    public ProductInfo getProductInfo(String markId) {
        return productInfoMapper.selectByPrimaryKey(markId);
    }
}
