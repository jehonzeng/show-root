package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.mapper.GoodsSpecificationMapper;
import com.szhengzhu.mapper.SpecificationInfoMapper;
import com.szhengzhu.service.GoodsSpecService;
import com.szhengzhu.util.ShowUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("goodsSpecService")
public class GoodsSpecServiceImpl implements GoodsSpecService {

    @Resource
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Resource
    private SpecificationInfoMapper specificationInfoMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public PageGrid<GoodsSpecification> pageGoodsSpec(PageParam<GoodsSpecification> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<GoodsSpecification> page = new PageInfo<>(
                goodsSpecificationMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @CheckGoodsChange
    @Override
    public void add(GoodsSpecification base) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(base.getGoodsId());
        ShowAssert.checkNull(goodsInfo, StatusCode._4004);
        ShowAssert.checkTrue(StrUtil.isEmpty(goodsInfo.getTypeId()), StatusCode._4004);
        List<SpecChooseBox> specs = specificationInfoMapper.selectNameByGoodsId(base.getGoodsId());
        ShowAssert.checkTrue(specs.isEmpty(), StatusCode._4018);
        List<GoodsSpecification> newGoodsSpecList = new LinkedList<>();
        List<String> specIds = buildSpes(specs);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByGoods(base.getGoodsId());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int index = 0; index < specIds.size(); index++) {
            if (goodsSpecifications.isEmpty()) {
                break;
            }
            for (GoodsSpecification goodsSpecification : goodsSpecifications) {
                if (specIds.get(index).equals(goodsSpecification.getSpecificationIds())) {
                    specIds.remove(index);
                }
            }
        }
        for (String specId : specIds) {
            String markId = snowflake.nextIdStr();
            GoodsSpecification specification = create(markId, specId, goodsInfo);
            newGoodsSpecList.add(specification);
        }
        if (!newGoodsSpecList.isEmpty()) {
            goodsSpecificationMapper.insertBatch(newGoodsSpecList);
        }
    }

    private GoodsSpecification create(String markId, String specIds, GoodsInfo goodsInfo) {
        return GoodsSpecification.builder().markId(markId).goodsId(goodsInfo.getMarkId()).specificationIds(specIds)
                .basePrice(goodsInfo.getBasePrice()).salePrice(goodsInfo.getSalePrice()).serverStatus(false).goodsNo(ShowUtils.createGoodsNo(0, goodsInfo.getMarkId())).build();
    }

    private List<String> buildSpes(List<SpecChooseBox> specs) {
        List<String> specsList = new LinkedList<>();
        for (SpecChooseBox specChooseBox : specs) {
            List<String> result = new LinkedList<>();
            List<Combobox> comboboxs = specChooseBox.getList();
            if (!specsList.isEmpty()) {
                for (String spec : specsList) {
                    for (Combobox combobox : comboboxs) {
                        result.add(spec + ',' + combobox.getCode());
                    }
                }
            } else {
                for (Combobox combobox : comboboxs) {
                    result.add(combobox.getCode());
                }
            }
            specsList = result;
        }
        return specsList;
    }

    @CheckGoodsChange
    @Override
    public void modify(GoodsSpecification base) {
        goodsSpecificationMapper.updateByPrimaryKeySelective(base);
    }
}
