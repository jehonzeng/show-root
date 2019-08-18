package com.szhengzhu.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.GoodsImageMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.service.GoodsImageService;
import com.szhengzhu.util.IdGenerator;

@Service("goodsImageService")
public class GoodsImageServiceImpl implements GoodsImageService {

    @Autowired
    private GoodsImageMapper goodsImageMapper;
    
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    
    @Override
    public Result<?> addGoodsImage(GoodsImage base) {
        if(base == null)
            return new Result<>(StatusCode._4004);
        base.setMarkId(IdGenerator.getInstance().nexId());
        goodsImageMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> midifyGoodsImage(GoodsImage base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        goodsImageMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> deleteGoodsImage(String markId) {
        goodsImageMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }

    @Override
    public Result<?> getGoodsImage(String goodsId,Integer serverType) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if(serverType.intValue() == 2) {
            //获取规格组合列表
            List<SpecificationInfo> specs = goodsInfoMapper.selectSpecListById(goodsId);
            map.put("specList", specs);
        }
        List<GoodsImage> list  = goodsImageMapper.selectByGoodsIdAndType(goodsId, serverType);
        map.put("imageServer", Contacts.IMAGE_SERVER);
        map.put("infos", list);
        return new Result<>(map);
    }

    @Override
    public GoodsImage getImageInfo(String markId) {
        return goodsImageMapper.selectByPrimaryKey(markId);
    }
}