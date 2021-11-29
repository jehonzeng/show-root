package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.annotation.CheckGoodsChange;
import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.mapper.GoodsImageMapper;
import com.szhengzhu.mapper.GoodsInfoMapper;
import com.szhengzhu.service.GoodsImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("goodsImageService")
public class GoodsImageServiceImpl implements GoodsImageService {

    @Resource
    private GoodsImageMapper goodsImageMapper;
    
    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    
    @CheckGoodsChange
    @Override
    public GoodsImage addGoodsImage(GoodsImage base) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        base.setMarkId(snowflake.nextIdStr());
        goodsImageMapper.insertSelective(base);
        return base;
    }

    @CheckGoodsChange
    @Override
    public GoodsImage modifyGoodsImage(GoodsImage base) {
        goodsImageMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @CheckGoodsChange
    @Override
    public void deleteGoodsImage(String markId) {
        goodsImageMapper.deleteByPrimaryKey(markId);
    }

    @Override
    public Map<String, Object> getGoodsImage(String goodsId,Integer serverType) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(serverType == 2) {
            //获取规格组合列表
            List<SpecificationInfo> specList = goodsInfoMapper.selectSpecListById(goodsId);
            map.put("specList", specList);
        }
        List<GoodsImage> list  = goodsImageMapper.selectByGoodsIdAndType(goodsId, serverType);
        map.put("imageServer", Contacts.IMAGE_SERVER);
        map.put("infos", list);
        return map;
    }

    @Override
    public GoodsImage getImageInfo(String markId) {
        return goodsImageMapper.selectByPrimaryKey(markId);
    }
}