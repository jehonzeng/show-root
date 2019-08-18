package com.szhengzhu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsContentMapper;
import com.szhengzhu.service.GoodsContentService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("goodsContentService")
public class GoodsContentServiceImpl implements GoodsContentService {

    @Autowired
    private GoodsContentMapper goodsContentMapper;

    @Override
    public Result<?> showContentByGoodsId(String goodsId) {
        if(StringUtils.isEmpty(goodsId))
            return new Result<>(StatusCode._4008);
        GoodsContent base = goodsContentMapper.selectByGoodsId(goodsId);
        if (base == null) {
            base = new GoodsContent();
            base.setMarkId(IdGenerator.getInstance().nexId());
            base.setContent("");
            base.setGoodsId(goodsId);
            goodsContentMapper.insertSelective(base);
        }
        return new Result<>(base);
    }

    @Override
    public Result<?> editGoodsContent(GoodsContent base) {
        if(base == null || StringUtils.isEmpty(base.getGoodsId()))
            return new Result<>(StatusCode._4004);
        GoodsContent oldContent = goodsContentMapper.selectByGoodsId(base.getGoodsId());
        base.setMarkId(oldContent.getMarkId());
        goodsContentMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }
}
