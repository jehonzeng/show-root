package com.szhengzhu.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.GoodsSpecificationMapper;
import com.szhengzhu.mapper.GoodsStockMapper;
import com.szhengzhu.mapper.SpecificationInfoMapper;
import com.szhengzhu.service.GoodsSpecService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;

@Service("goodsSpecService")
public class GoodsSpecServiceImpl implements GoodsSpecService {

    @Resource
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Resource
    private SpecificationInfoMapper specificationInfoMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    
    @Resource
    private GoodsStockMapper goodsStockMapper;

    @Override
    public Result<PageGrid<GoodsSpecification>> pageGoodsSpec(PageParam<GoodsSpecification> base) {
        if (base == null || base.getData() == null
                || StringUtils.isEmpty(base.getData().getGoodsId()))
            return new Result<>(StatusCode._4004);
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageHelper.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsSpecification> page = new PageInfo<>(
                goodsSpecificationMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> add(GoodsSpecification base) {
        if (base == null || StringUtils.isEmpty(base.getGoodsId()))
            return new Result<>(StatusCode._4004);
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(base.getGoodsId());
        if (goodsInfo == null || goodsInfo.getTypeId() == null)
            return new Result<>(StatusCode._4004);
        List<SpecChooseBox> specs = specificationInfoMapper.selectNameByGoodsId(base.getGoodsId());
        List<GoodsSpecification> newGoodsSpecList = new LinkedList<>();
        List<String> specIds = buildSpes(specs);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper
                .selectByGoods(base.getGoodsId());
        IdGenerator generator = IdGenerator.getInstance();
        for (int index = 0; index < specIds.size(); index++) {
            if (goodsSpecifications.size() == 0)
                break;
            for (int existIndex = 0; existIndex < goodsSpecifications.size(); existIndex++) {
                if (specIds.get(index)
                        .equals(goodsSpecifications.get(existIndex).getSpecificationIds()))
                    specIds.remove(index);
            }
        }
        for (int index = 0; index < specIds.size(); index++) {
            String markId = generator.nexId();
            GoodsSpecification specification = create(markId, specIds.get(index), goodsInfo);
            newGoodsSpecList.add(specification);
        }
        if (newGoodsSpecList.size() > 0)
            goodsSpecificationMapper.insertBatch(newGoodsSpecList);

        return new Result<>();
    }

    private GoodsSpecification create(String markId, String specIds, GoodsInfo goodsInfo) {
        GoodsSpecification specification = new GoodsSpecification();
        specification.setMarkId(markId);
        specification.setGoodsId(goodsInfo.getMarkId());
        specification.setSpecificationIds(specIds);
        specification.setBasePrice(goodsInfo.getBasePrice());
        specification.setSalePrice(goodsInfo.getSalePrice());
        specification.setServerStatus(false);
        specification.setGoodsNo(ShowUtils.createGoodsNo(0, goodsInfo.getMarkId()));
        return specification;
    }

    private List<String> buildSpes(List<SpecChooseBox> specs) {
        List<String> spesList = new LinkedList<>();
        for (int index = 0; index < specs.size(); index++) {
            List<String> result = new LinkedList<>();
            List<Combobox> comboboxs = specs.get(index).getList();
            if (spesList.size() > 0) {
                for (int i = 0; i < spesList.size(); i++) {
                    for (int j = 0; j < comboboxs.size(); j++) {
                        result.add(spesList.get(i) + ',' + comboboxs.get(j).getCode());
                    }
                }
            } else {
                for (int i = 0; i < comboboxs.size(); i++) {
                    result.add(comboboxs.get(i).getCode());
                }
            }
            spesList = result;
        }
        return spesList;
    }

    @Override
    public Result<?> modify(GoodsSpecification base) {
        if (base == null || StringUtils.isEmpty(base.getMarkId()))
            return new Result<>(StatusCode._4004);
        goodsSpecificationMapper.updateByPrimaryKeySelective(base);
        return new Result<>();
    }
}
