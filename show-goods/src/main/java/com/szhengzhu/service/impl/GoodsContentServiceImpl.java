package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.mapper.GoodsContentMapper;
import com.szhengzhu.service.GoodsContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service("goodsContentService")
public class GoodsContentServiceImpl implements GoodsContentService {

    @Resource
    private GoodsContentMapper goodsContentMapper;

    @Override
    public GoodsContent showContentByGoodsId(String goodsId) {
        GoodsContent base = goodsContentMapper.selectByGoodsId(goodsId);
        if (ObjectUtil.isNull(base)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            base = GoodsContent.builder().markId(snowflake.nextIdStr()).content("").goodsId(goodsId).build();
            goodsContentMapper.insertSelective(base);
        }
        return base;
    }

    @CheckGoodsChange
    @Override
    public GoodsContent editGoodsContent(GoodsContent base) {
        GoodsContent oldContent = goodsContentMapper.selectByGoodsId(base.getGoodsId());
        base.setMarkId(oldContent.getMarkId());
        base.setContent(StrUtil.isEmpty(base.getContent()) ? "" : base.getContent());
        goodsContentMapper.updateByPrimaryKeyWithBLOBs(base);
        return base;
    }
}
