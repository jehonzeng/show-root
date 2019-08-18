package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.vo.GoodsFoodVo;

public interface FoodsItemMapper {
    int deleteByPrimaryKey(String markId);

    int insert(FoodsItem record);

    int insertSelective(FoodsItem record);

    FoodsItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(FoodsItem record);

    int updateByPrimaryKey(FoodsItem record);

    int insertBatch(List<FoodsItem> list);

    List<GoodsFoodVo> selectByExampleSelective(FoodsItem data);
}