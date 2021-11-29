package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Category;
import com.szhengzhu.bean.ordering.CategoryCommodity;
import com.szhengzhu.bean.ordering.CategorySpecs;
import com.szhengzhu.bean.ordering.vo.CategoryVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.xwechat.vo.CategoryModel;
import com.szhengzhu.bean.xwechat.vo.CommodityModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.CategoryCommodityMapper;
import com.szhengzhu.mapper.CategoryMapper;
import com.szhengzhu.mapper.CategorySpecsMapper;
import com.szhengzhu.mapper.CommodityMapper;
import com.szhengzhu.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {


    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private CategorySpecsMapper categorySpecsMapper;

    @Resource
    private CategoryCommodityMapper categoryCommodityMapper;

    @Override
    public PageGrid<CategoryVo> page(PageParam<Category> pageParam) {
        String sidx = pageParam.getSidx().equals("mark_id") ? "sort" : pageParam.getSidx();
        String sort = sidx.equals("sort") ? "asc" : pageParam.getSort();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + sort);
        List<CategoryVo> list = categoryMapper.selectByExampleSelective(pageParam.getData());
        for (CategoryVo cate : list) {
            List<String> specsList = categorySpecsMapper.selectSpecsByCateId(cate.getMarkId());
            List<String> commodityList = categoryCommodityMapper.selectCommodityByCateId(cate.getMarkId());
            cate.setSpecsList(specsList);
            cate.setCommodityList(commodityList);
        }
        PageInfo<CategoryVo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(Category category) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        category.setMarkId(snowflake.nextIdStr());
        category.setCreateTime(DateUtil.date());
        categoryMapper.insertSelective(category);
    }

    @Override
    public void modify(Category category) {
        category.setModifyTime(DateUtil.date());
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void modifyStatus(String[] cateIds, int status) {
        categoryMapper.updateBatchStatus(cateIds, status);
    }

    @Override
    public List<Combobox> listCombobox(String storeId) {
        return categoryMapper.selectCombobox(storeId);
    }

    @Override
    public void modifyCateSpecs(CategorySpecs categorySpecs) {
        categorySpecsMapper.updateByPrimaryKey(categorySpecs);
    }

    @Override
    public void optCateSpecs(String[] specsIds, String cateId) {
        categorySpecsMapper.deleteByCateId(cateId);
        if (specsIds.length > 0) { categorySpecsMapper.insertBatch(cateId, specsIds); }
    }

    @Override
    public void modifyCateCommodity(CategoryCommodity categoryCommodity) {
        categoryCommodityMapper.updateByPrimaryKeySelective(categoryCommodity);
    }

    @Override
    public void optCateCommodity(String cateId, String[] commodityIds) {
        categoryCommodityMapper.deleteByCateId(cateId);
        if (commodityIds.length > 0) { categoryCommodityMapper.insertBatch(cateId, commodityIds); }
    }

    @Override
    public List<CategoryModel> listResCate(String storeId) {
        List<CategoryModel> cateList = categoryCommodityMapper.selectResCate(storeId);
        for (CategoryModel cate : cateList) {
            List<CommodityModel> commList = commodityMapper.selectResCommodity(storeId, cate.getCateId());
            cate.setCommodityList(commList);
        }
        return cateList;
    }

    @Override
    public List<CategoryModel> listLjsCate(String storeId) {
        List<CategoryModel> cateList = categoryCommodityMapper.selectLjsCate(storeId);
        for (CategoryModel cate : cateList) {
            List<CommodityModel> commList = commodityMapper.selectLjsCommodity(storeId, cate.getCateId());
            cate.setCommodityList(commList);
        }
        return cateList;
    }
}
