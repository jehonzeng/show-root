package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.provider.GoodsProvider;

public interface GoodsInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    @Select("SELECT COUNT(*) FROM t_goods_info WHERE goods_name = #{goodsName} AND mark_id <> #{markId} ")
    int repeatRecords(@Param("goodsName") String goodsName, @Param("markId") String markId);

    @SelectProvider(type = GoodsProvider.class, method = "selectSpecListById")
    List<SpecificationInfo> selectSpecListById(@Param("markId") String markId);

    List<GoodsVo> selectByExampleSelective(GoodsInfo record);

    @Select("SELECT mark_id AS code, goods_name AS name FROM t_goods_info WHERE server_status IN ('ZT01', 'ZT02')")
    List<Combobox> selectCombobox();

    @SelectProvider(type = GoodsProvider.class, method = "selectListNotInColumn")
    List<Combobox> selectListNotInColumn();

    @SelectProvider(type = GoodsProvider.class, method = "selectListNotInLabel")
    List<Combobox> selectListNotInLabel(@Param("labelId") String labelId);

    @SelectProvider(type = GoodsProvider.class, method = "selectListNotSpecial")
    List<Combobox> selectListNotSpecial();

    @SelectProvider(type = GoodsProvider.class, method = "selectListNotIcon")
    List<Combobox> selectListNotIcon();

    List<GoodsVo> selectByExampleSelectiveNotColumn(GoodsInfo data);

    List<GoodsVo> selectByExampleSelectiveNotLabel(GoodsInfo data);

    List<GoodsVo> selectByExampleSelectiveNotIcon(GoodsInfo data);

    List<GoodsVo> selectByExampleSelectiveNotSpecial(GoodsInfo data);
    
    List<GoodsBase> selectLabelGoods(@Param("labelId") String labelId);
    
    List<GoodsBase> selectRecommend();
    
    List<GoodsBase> selectRecommenByUser(@Param("userId") String userId); 
    
    GoodsInfoVo selectById(@Param("goodsId") String goodsId);
    
    List<GoodsBase> selectByCooker(@Param("cooker") String cooker, @Param("number") Integer number);
    
    GoodsBase selectCartGoodsInfo(@Param("goodsId") String goodsId, @Param("specIds") String specIds);
    
    GoodsInfo selectInfoById(@Param("goodsId") String goodsId, @Param("specIds") String specIds);

    @Update("UPDATE t_goods_info SET pre_upper_time = null ,pre_down_time = null  WHERE mark_id = #{markId}")
    int clearTime(@Param("markId") String markId);
    
    @Select("SELECT goods_name FROM t_goods_info WHERE mark_id=#{goodsId}")
    String selectNameById(@Param("goodsId") String goodsId);
}