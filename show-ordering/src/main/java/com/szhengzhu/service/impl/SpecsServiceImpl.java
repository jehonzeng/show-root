package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Specs;
import com.szhengzhu.bean.ordering.SpecsItem;
import com.szhengzhu.bean.ordering.vo.SpecsVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.SpecsItemMapper;
import com.szhengzhu.mapper.SpecsMapper;
import com.szhengzhu.service.SpecsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("specsService")
public class SpecsServiceImpl implements SpecsService {

    @Resource
    private SpecsMapper specsMapper;

    @Resource
    private SpecsItemMapper specsItemMapper;

    @Override
    public PageGrid<Specs> page(PageParam<Specs> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<Specs> pageInfo = new PageInfo<>(
                specsMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(Specs specs) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        specs.setMarkId(snowflake.nextIdStr());
        specs.setCreateTime(DateUtil.date());
        specsMapper.insertSelective(specs);
    }

    @Override
    public void modify(Specs specs) {
        specs.setModifyTime(DateUtil.date());
        specsMapper.updateByPrimaryKeySelective(specs);
    }

    @Override
    public void modifyStatus(String[] specsIds, int status) {
        specsMapper.updateBatchStatus(specsIds, status);
    }

    @Override
    public List<SpecsVo> getCombobox(String storeId) {
        List<SpecsVo> comboboxList = specsMapper.selectCombobox(storeId);
        for (SpecsVo specs : comboboxList) {
            String specsId = specs.getCode();
            List<Combobox> itemList = specsItemMapper.selectComboboxBySpecsId(specsId);
            specs.setItemList(itemList);
        }
        return comboboxList;
    }

    @Override
    public List<Map<String, String>> list(String name) {
        return specsMapper.selectByName(name);
    }

    @Override
    public PageGrid<SpecsItem> pageItem(PageParam<SpecsItem> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("create_time " + pageParam.getSort());
        PageInfo<SpecsItem> pageInfo = new PageInfo<>(
                specsItemMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addItem(SpecsItem specsItem) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        specsItem.setMarkId(snowflake.nextIdStr());
        specsItem.setCreateTime(DateUtil.date());
        specsItem.setStatus(0);
        specsItemMapper.insertSelective(specsItem);
    }

    @Override
    public void modifyItem(SpecsItem specsItem) {
        specsItem.setModifyTime(DateUtil.date());
        specsItemMapper.updateByPrimaryKeySelective(specsItem);
    }

    @Override
    public void modifyItemStatus(String[] itemIds, int status) {
        specsItemMapper.updateBatchStatus(itemIds, status);
    }

    @Override
    public List<SpecsVo> getComboboxByCateId(String[] cateIds, String storeId) {
        List<SpecsVo> specsList = specsMapper.selectVoByCateId(cateIds, storeId);
        for (SpecsVo specs : specsList) {
            String specsId = specs.getCode();
            List<Combobox> itemList = specsItemMapper.selectComboboxBySpecsId(specsId);
            specs.setItemList(itemList);
        }
        return specsList;
    }
}
