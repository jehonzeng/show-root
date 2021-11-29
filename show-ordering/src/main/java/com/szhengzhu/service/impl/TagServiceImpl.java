package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Tag;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.TagCommodityMapper;
import com.szhengzhu.mapper.TagMapper;
import com.szhengzhu.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("tagService")
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagCommodityMapper tagCommodityMapper;

    @Override
    public PageGrid<Tag> page(PageParam<Tag> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx())? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<Tag> pageInfo = new PageInfo<>(tagMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void add(Tag tag) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        tag.setMarkId(snowflake.nextIdStr());
        tag.setCreateTime(DateUtil.date());
        tagMapper.insertSelective(tag);
    }

    @Override
    public void modify(Tag tag) {
        tag.setModifyTime(DateUtil.date());
        tagMapper.updateByPrimaryKeySelective(tag);
    }

    @Override
    public void modifyStatus(String[] tagIds, int status) {
        tagMapper.updateBatchStatus(tagIds, status);
    }

    @Override
    public List<Combobox> getCombobox(String storeId) {
        return tagMapper.selectCombobox(storeId);
    }

    @Override
    public void addTagCommodity(String[] commodityIds, String tagId) {
        tagCommodityMapper.insertBatch(commodityIds, tagId);
    }

    @Override
    public void deleteTagCommodity(String[] commodityIds, String tagId) {
        tagCommodityMapper.deleteBatch(commodityIds, tagId);
    }
}
