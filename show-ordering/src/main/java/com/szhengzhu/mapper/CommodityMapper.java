package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Commodity;
import com.szhengzhu.bean.ordering.param.CommodityParam;
import com.szhengzhu.bean.ordering.vo.CommodityPageVo;
import com.szhengzhu.bean.ordering.vo.CommodityVo;
import com.szhengzhu.bean.xwechat.vo.CommodityModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CommodityMapper {

    int deleteByPrimaryKey(String markId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    List<CommodityPageVo> selectByExampleSelective(CommodityParam commodity);

    List<CommodityPageVo> selectByCate(CommodityParam commodity);

    void updateBatchStatus(@Param("commodityIds") String[] commodityIds, @Param("status") int status);

    CommodityVo selectVoById(@Param("commodityId") String commodityId);

    List<Map<String, String>> selectByName(@Param("name") String name);

    @Update("update t_commodity_info set quantity=null, modify_time=NOW()")
    int cancelCommQuantity();

    @Update("update t_commodity_info set quantity=#{quantity}, modifier=#{modifier}, modify_time=NOW() where mark_id=#{commodityId}")
    int updateQuantity(@Param("commodityId") String commodityId, @Param("modifier") String modifier, @Param("quantity") Integer quantity);

    List<CommodityModel> selectResCommodity(@Param("storeId") String storeId, @Param("cateId") String cateId);

    List<CommodityModel> selectLjsCommodity(@Param("storeId") String storeId, @Param("cateId") String cateId);

    List<Commodity> queryComboCommodity(String markId);
}
