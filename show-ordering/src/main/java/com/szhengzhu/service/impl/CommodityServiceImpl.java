package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Commodity;
import com.szhengzhu.bean.ordering.CommodityItem;
import com.szhengzhu.bean.ordering.CommodityPrice;
import com.szhengzhu.bean.ordering.CommoditySpecs;
import com.szhengzhu.bean.ordering.param.CommodityParam;
import com.szhengzhu.bean.ordering.vo.CommodityPageVo;
import com.szhengzhu.bean.ordering.vo.CommoditySpecsVo;
import com.szhengzhu.bean.ordering.vo.CommodityVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.CommodityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private TagCommodityMapper tagCommodityMapper;

    @Resource
    private CommodityImgMapper commodityImgMapper;

    @Resource
    private CommodityItemMapper commodityItemMapper;

    @Resource
    private CommodityPriceMapper commodityPriceMapper;

    @Resource
    private CommoditySpecsMapper commoditySpecsMapper;

    @Resource
    private CategoryCommodityMapper categoryCommodityMapper;

    @Override
    public PageGrid<CommodityPageVo> page(PageParam<CommodityParam> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        pageParam.setSort(" asc");
        if (StrUtil.isEmpty(pageParam.getData().getCateId())) {
            pageParam.setSidx("convert(c.name using gbk) ");
            PageMethod.orderBy(pageParam.getSidx() + " " + pageParam.getSort());
            PageInfo<CommodityPageVo> pageInfo = new PageInfo<>(commodityMapper.selectByExampleSelective(pageParam.getData()));
            return new PageGrid<>(pageInfo);
        }
        pageParam.setSidx("cc.sort, convert(c.name using gbk) ");
        PageMethod.orderBy(pageParam.getSidx() + " " + pageParam.getSort());
        PageInfo<CommodityPageVo> pageInfo = new PageInfo<>(commodityMapper.selectByCate(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Commodity getInfo(String commodityId) {
        return commodityMapper.selectByPrimaryKey(commodityId);
    }

    @Override
    public CommodityVo getInfoVo(String commodityId) {
        CommodityVo commodity = commodityMapper.selectVoById(commodityId);
        ShowAssert.checkNull(commodity, StatusCode._4004);
        List<String> imgList = commodityImgMapper.selectImgIdByCommodityId(commodityId);
        commodity.setImgList(imgList.toArray(new String[imgList.size()]));
        List<CommodityPrice> priceList = commodityPriceMapper.selectByCommodityId(commodityId);
        commodity.setPriceList(priceList);
        List<CommoditySpecsVo> specsList = commoditySpecsMapper.selectVoByCommodityId(commodityId);
        commodity.setSpecsList(specsList);
        List<String> cateList = categoryCommodityMapper.selectCateByCommodityId(commodityId);
        commodity.setCateList(cateList.toArray(new String[cateList.size()]));
        List<String> tagList = tagCommodityMapper.selectCommodityTag(commodityId);
        commodity.setTagList(tagList.toArray(new String[tagList.size()]));
        return commodity;
    }

    @Transactional
    @Override
    public String add(Commodity commodity) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        commodity.setMarkId(snowflake.nextIdStr());
        commodity.setCreateTime(DateUtil.date());
        commodityMapper.insertSelective(commodity);
        if (commodity.getImgList() != null && commodity.getImgList().length > 0) {
            commodityImgMapper.insertBatch(commodity.getImgList(), commodity.getMarkId());
        }
        return commodity.getMarkId();
    }

    @Override
    public void modify(Commodity commodity) {
        commodity.setModifyTime(DateUtil.date());
        commodityMapper.updateByPrimaryKeySelective(commodity);
        commodityImgMapper.deleteByCommodityId(commodity.getMarkId());
        if (commodity.getImgList() != null && commodity.getImgList().length > 0) {
            commodityImgMapper.insertBatch(commodity.getImgList(), commodity.getMarkId());
        }
    }

    @Override
    public void modifyQuantity(String commodityId, String employeeId, Integer quantity) {
        commodityMapper.updateQuantity(commodityId, employeeId, quantity);
    }

    @Override
    public void modifyStatus(String[] commodityIds, int status) {
        commodityMapper.updateBatchStatus(commodityIds, status);
    }

    @Override
    public List<Map<String, String>> listCommodity(String name) {
        return commodityMapper.selectByName(name);
    }

    @Override
    public void optSpecs(CommoditySpecs commoditySpecs) {
        CommoditySpecs specs = commoditySpecsMapper.selectByPrimaryKey(commoditySpecs.getCommodityId(),
                commoditySpecs.getSpecsId());
        if (ObjectUtil.isNull(specs)) {
            commoditySpecs.setCreateTime(DateUtil.date());
            commoditySpecsMapper.insertSelective(commoditySpecs);
            return;
        }
        specs.setMinValue(commoditySpecs.getMinValue());
        specs.setMaxValue(commoditySpecs.getMaxValue());
        specs.setModifyTime(DateUtil.date());
        commoditySpecsMapper.updateByPrimaryKeySelective(specs);
    }

    @Override
    public void optSpecsItem(CommodityItem commodityItem) {
        CommodityItem item = commodityItemMapper.selectByPrimaryKey(commodityItem.getCommodityId(),
                commodityItem.getSpecsId(), commodityItem.getItemId());
        if (ObjectUtil.isNull(item)) {
            commodityItemMapper.insertSelective(commodityItem);
            return;
        }
        item.setMarkupPrice(commodityItem.getMarkupPrice());
        item.setSort(commodityItem.getSort());
        item.setChecked(commodityItem.getChecked());
        commodityItemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public void deleteSpecsItem(String commodityId, String specsId, String itemId) {
        commodityItemMapper.deleteByPrimaryKey(commodityId, specsId, itemId);
    }

    @Override
    public void optCommodityCate(String commodityId, String[] cateIds) {
        categoryCommodityMapper.deleteByCommodityId(commodityId);
        if (cateIds.length > 0) {
            categoryCommodityMapper.insertCommodityBatch(cateIds, commodityId);
        }
    }

    @Override
    public void optCommodityTag(String commodityId, String[] tagIds) {
        tagCommodityMapper.deleteByCommodityId(commodityId);
        if (tagIds.length > 0) {
            tagCommodityMapper.insertCommodityBatch(commodityId, tagIds);
        }
    }
}
