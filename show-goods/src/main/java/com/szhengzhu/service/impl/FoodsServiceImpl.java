package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.FoodsInfoMapper;
import com.szhengzhu.mapper.FoodsItemMapper;
import com.szhengzhu.mapper.SpecificationInfoMapper;
import com.szhengzhu.service.FoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("foodsService")
public class FoodsServiceImpl implements FoodsService {

    @Resource
    private FoodsInfoMapper foodsInfoMapper;

    @Resource
    private FoodsItemMapper foodsItemMapper;

    @Resource
    private SpecificationInfoMapper specificationInfoMapper;

    @Override
    public FoodsInfo addFoodsInfo(FoodsInfo base) {
        int count = foodsInfoMapper.repeatRecords(base.getFoodName(), "");
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setServerStatus(false);
        foodsInfoMapper.insertSelective(base);
        return base;
    }

    @Override
    public FoodsInfo modifyFoodsInfo(FoodsInfo base) {
        int count = foodsInfoMapper.repeatRecords(base.getFoodName(), base.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4007);
        foodsInfoMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<FoodsInfo> getPage(PageParam<FoodsInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<FoodsInfo> page = new PageInfo<>(
                foodsInfoMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public FoodsInfo getFoodsInfo(String markId) {
        return foodsInfoMapper.selectByPrimaryKey(markId);
    }

    @Override
    public List<Combobox> listFoodWithoutGoods(String goodsId) {
        return foodsInfoMapper.selectComboboxList(goodsId);
    }

    @Override
    public void addBatchItem(FoodsItem base) {
        List<SpecChooseBox> specList = specificationInfoMapper
                .selectNameByGoodsId(base.getGoodsId());
        List<String> specIds = getSpecIds(specList);
        List<FoodsItem> list = new LinkedList<>();
        FoodsItem foodsItem;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String specId : specIds) {
            foodsItem = FoodsItem.builder().markId(snowflake.nextIdStr()).goodsId(base.getGoodsId()).specificationIds(specId)
                    .foodId(base.getFoodId()).useSize(base.getUseSize()).serverStatus(false).build();
            list.add(foodsItem);
        }
        if (!list.isEmpty()) {
            foodsItemMapper.insertBatch(list);
        }
    }

    /**
     * 组合所有该商品的规格
     *
     * @param specList
     * @return
     * @date 2019年7月5日
     */
    private List<String> getSpecIds(List<SpecChooseBox> specList) {
        List<String> specIds = new LinkedList<>();
        for (int i = 0, len = specList.size(); i < len; i++) {
            List<Combobox> comboboxs = specList.get(i).getList();
            if (i == 0) {
                for (Combobox combobox : comboboxs) {
                    specIds.add(combobox.getCode());
                }
                continue;
            }
            List<String> temp = new LinkedList<>();
            specIds.forEach(specId ->
                    comboboxs.forEach(combobox -> temp.add(specId + "," + combobox.getCode()))
            );
            specIds = temp;
        }
        return specIds;
    }

    @Override
    public PageGrid<GoodsFoodVo> getItemPage(PageParam<FoodsItem> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy("i." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsFoodVo> page = new PageInfo<>(
                foodsItemMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public void deleteItem(String markId) {
        foodsItemMapper.deleteByPrimaryKey(markId);
    }

    @Override
    public FoodsItem updateFoodsItem(FoodsItem base) {
        foodsItemMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public List<Combobox> getFoodCombobox() {
        return foodsInfoMapper.selectFoodList();
    }
}
