package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.provider.GoodsProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
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
    
    GoodsDetail selectById(@Param("goodsId") String goodsId);
    
    List<GoodsBase> selectByCooker(@Param("cooker") String cooker, @Param("number") Integer number);
    
    GoodsBase selectCartGoodsInfo(@Param("goodsId") String goodsId, @Param("specIds") String specIds);
    
    GoodsInfo selectInfoById(@Param("goodsId") String goodsId, @Param("specIds") String specIds);

    @Update("UPDATE t_goods_info SET pre_upper_time = null ,pre_down_time = null  WHERE mark_id = #{markId}")
    int clearTime(@Param("markId") String markId);
    
    @Select("SELECT goods_name FROM t_goods_info WHERE mark_id=#{goodsId}")
    String selectNameById(@Param("goodsId") String goodsId);

    @Update("UPDATE t_goods_info SET server_status ='ZT02' WHERE pre_upper_time <= #{currentTime} AND pre_down_time > #{currentTime} AND server_status='ZT01' ")
    int updateAutoUpperStatus(@Param("currentTime") String currentTime);
    
    @Update("UPDATE t_goods_info SET server_status ='ZT03' WHERE pre_down_time <= #{currentTime} AND server_status = 'ZT02' ")
    int updateAutoDownStatus(@Param("currentTime") String currentTime);
    
    @Select("SELECT mark_id FROM t_goods_info")
    List<String> selectGoodsIds();
    
    BigDecimal selectGoodsSalePrice(@Param("goodsId") String goodsId);

    @SelectProvider(type= GoodsProvider.class,method = "selectByCategoryId")
    String selectDishesByCategoryId(@Param("productId") String productId);
    
    @Select("SELECT pre_upper_time AS preUpperTime ,pre_down_time AS preDownTime FROM t_goods_info WHERE pre_upper_time IS NOT NUll AND pre_down_time IS NOT NUll")
    List<GoodsInfo> selectPreGoods();
}
