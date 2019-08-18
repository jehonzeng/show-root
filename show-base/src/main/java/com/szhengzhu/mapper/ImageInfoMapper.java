package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.provider.ImageProvider;

public interface ImageInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ImageInfo record);

    int insertSelective(ImageInfo record);

    ImageInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ImageInfo record);

    int updateByPrimaryKey(ImageInfo record);

    @SelectProvider(type = ImageProvider.class, method = "goodsSpecImage")
    ImageInfo goodsSpecImage(@Param("goodsId") String goodsId,
            @Param("serverType") Integer serverType, @Param("specIds") String specIds);

    @SelectProvider(type = ImageProvider.class, method = "selectGoodsSpecImage")
    ImageInfo selectGoodsSpecImage(@Param("goodsId") String goodsId, @Param("specIds") String specIds);

    @SelectProvider(type = ImageProvider.class, method = "selectVoucharSpecImage")
    ImageInfo selectVoucharSpecImage(@Param("goodsId") String goodsId, @Param("specIds") String specIds);

    @SelectProvider(type = ImageProvider.class, method = "selectMealSmallImage")
    ImageInfo selectMealSmallImage(@Param("mealId") String mealId);

    @SelectProvider(type= ImageProvider.class , method = "selectAccessoryImage")
    ImageInfo selectAccessoryImage(@Param("accessoryId") String accessoryId);

}