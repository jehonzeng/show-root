package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.CategoryInfoMapper;
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
    private CategoryInfoMapper categoryInfoMapper;

    @Override
    public CategoryInfo addCategory(CategoryInfo categoryInfo) {
        int count = categoryInfoMapper.repeatRecords(categoryInfo.getName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        categoryInfo.setMarkId(snowflake.nextIdStr());
        categoryInfo.setServerStatus(false);
        categoryInfoMapper.insertSelective(categoryInfo);
        return categoryInfo;
    }

    @Override
    public CategoryInfo editCategory(CategoryInfo categoryInfo) {
        String name = StrUtil.isEmpty(categoryInfo.getName()) ? "" : categoryInfo.getName();
        int count = categoryInfoMapper.repeatRecords(name, categoryInfo.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        categoryInfoMapper.updateByPrimaryKeySelective(categoryInfo);
        return categoryInfo;
    }

    @Override
    public PageGrid<CategoryInfo> getPage(PageParam<CategoryInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<CategoryInfo> page = new PageInfo<>(
                categoryInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public CategoryInfo getCategoryInfo(String markId) {
        return categoryInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<Combobox> getDownList(String serverStatus) {
        return categoryInfoMapper.selectDownList(serverStatus);
    }
}
