package com.szhengzhu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.CategoryInfoMapper;
import com.szhengzhu.service.CategoryService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    @Override
    public Result<?> addCategory(CategoryInfo categoryInfo) {
        if (categoryInfo == null || StringUtils.isEmpty(categoryInfo.getName())) 
            return new Result<>(StatusCode._4004);
        int count = categoryInfoMapper.repeatRecords(categoryInfo.getName(), "");
        if (count > 0) 
            return new Result<String>(StatusCode._4007);
        categoryInfo.setMarkId(IdGenerator.getInstance().nexId());
        categoryInfo.setServerStatus(false);
        categoryInfoMapper.insertSelective(categoryInfo);
        return new Result<>(categoryInfo);
    }

    @Override
    public Result<?> editCategory(CategoryInfo categoryInfo) {
        if (categoryInfo == null || categoryInfo.getMarkId() == null) 
            return new Result<>(StatusCode._4004);
        String name = categoryInfo.getName() == null ? "" : categoryInfo.getName();
        int count = categoryInfoMapper.repeatRecords(name, categoryInfo.getMarkId());
        if (count > 0) 
            return new Result<String>(StatusCode._4007);
        categoryInfoMapper.updateByPrimaryKeySelective(categoryInfo);
        return new Result<>(categoryInfo);
    }

    @Override
    public Result<PageGrid<CategoryInfo>> getPage(PageParam<CategoryInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<CategoryInfo> page = new PageInfo<>(
                categoryInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getCategoryInfo(String markId) {
        CategoryInfo categoryInfo = categoryInfoMapper.selectByMark(markId);
        return new Result<>(categoryInfo);
    }

    @Override
    public Result<?> getDownList(String serverStatus) {
        List<Combobox> infos = categoryInfoMapper.selectDownList(serverStatus);
        return new Result<>(infos);
    }

    @Override
    public Result<?> getSuperList() {
        List<Combobox> list = categoryInfoMapper.selectSuperList();
        return new Result<>(list);
    }
}
