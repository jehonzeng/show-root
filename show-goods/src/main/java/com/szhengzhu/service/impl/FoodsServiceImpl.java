package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.FoodsInfoMapper;
import com.szhengzhu.mapper.FoodsItemMapper;
import com.szhengzhu.mapper.SpecificationInfoMapper;
import com.szhengzhu.service.FoodsService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("foodsService")
public class FoodsServiceImpl implements FoodsService {

    @Autowired
    private FoodsInfoMapper foodsInfoMapper;

    @Autowired
    private FoodsItemMapper foodsItemMapper;

    @Autowired
    private SpecificationInfoMapper specificationInfoMapper;

    @Override
    public Result<?> addFoodsInfo(FoodsInfo base) {
        if (base == null || StringUtils.isEmpty(base.getFoodName()))
            return new Result<>(StatusCode._4004);
        int count = foodsInfoMapper.repeatRecords(base.getFoodName(), "");
        if (count > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setServerStatus(false);
        foodsInfoMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> modifyFoodsInfo(FoodsInfo base) {
        if (base == null || StringUtils.isEmpty(base.getMarkId()))
            return new Result<>(StatusCode._4004);
        int count = foodsInfoMapper.repeatRecords(base.getFoodName(), base.getMarkId());
        if (count > 0)
            return new Result<>(StatusCode._4007);
        foodsInfoMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<FoodsInfo>> getPage(PageParam<FoodsInfo> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<FoodsInfo> page = new PageInfo<>(
                foodsInfoMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getFoodsInfo(String markId) {
        return new Result<>(foodsInfoMapper.selectByPrimaryKey(markId));
    }

    @Override
    public Result<List<Combobox>> listFoodWithoutGoods(String goodsId) {
        List<Combobox> list = foodsInfoMapper.selectComboboxList(goodsId);
        return new Result<>(list);
    }

    @Override
    public Result<?> addBatchItem(FoodsItem base) {
        if (base == null || base.getFoodId() == null || base.getGoodsId() == null) {
            return new Result<>(StatusCode._4004);
        }
        List<SpecChooseBox> specList = specificationInfoMapper
                .selectNameByGoodsId(base.getGoodsId());
        if (specList == null || specList.size() == 0)
            return new Result<>(StatusCode._4008);
        List<String> specIds = getSpesIds(specList);
        List<FoodsItem> list = new LinkedList<>();
        IdGenerator idGenerator = IdGenerator.getInstance();
        for (int i = 0, len = specIds.size(); i < len; i++) {
            FoodsItem foodsItem = new FoodsItem();
            foodsItem.setMarkId(idGenerator.nexId());
            foodsItem.setGoodsId(base.getGoodsId());
            foodsItem.setSpecificationIds(specIds.get(i));
            foodsItem.setFoodId(base.getFoodId());
            foodsItem.setUseSize(base.getUseSize());
            foodsItem.setServerStatus(false);
            list.add(foodsItem);
        }
        if (list.size() > 0)
            foodsItemMapper.insertBatch(list);
        return new Result<>();
    }

    /**
     * 组合所有该商品的规格
     *
     * @param specList
     * @return
     * @date 2019年7月5日
     */
    private List<String> getSpesIds(List<SpecChooseBox> specList) {
        List<String> specIds = new LinkedList<>();
        for (int i = 0, len = specList.size(); i < len; i++) {
            List<Combobox> comboboxs = specList.get(i).getList();
            if (i == 0) {
                for (int index = 0; index < comboboxs.size(); index++) {
                    specIds.add(comboboxs.get(index).getCode());
                }
                continue;
            }
            List<String> temp = new LinkedList<>();
            for (int k = 0; k < specIds.size(); k++) {
                for (int j = 0; j < comboboxs.size(); j++) {
                    temp.add(specIds.get(k) + "," + comboboxs.get(j).getCode());
                }

            }
            specIds = temp;
        }
        return specIds;
    }

    @Override
    public Result<PageGrid<GoodsFoodVo>> getItemPage(PageParam<FoodsItem> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy("i." + base.getSidx() + " " + base.getSort());
        PageInfo<GoodsFoodVo> page = new PageInfo<>(
                foodsItemMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> deleteItem(String markId) {
        foodsItemMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }

    @Override
    public Result<?> updateFoodsItem(FoodsItem base) {
        foodsItemMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<List<Combobox>> getFoodCombobox() {
        List<Combobox> list = foodsInfoMapper.selectFoodList();
        return new Result<>(list);
    }
}
