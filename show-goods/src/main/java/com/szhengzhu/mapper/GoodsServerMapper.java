package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.GoodsServer;
import com.szhengzhu.bean.vo.BatchVo;

public interface GoodsServerMapper {
    int deleteByPrimaryKey(String markId);

    int insert(GoodsServer record);

    int insertSelective(GoodsServer record);

    GoodsServer selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsServer record);

    int updateByPrimaryKey(GoodsServer record);

    int insertBatch(List<GoodsServer> list);
 
    int deletetBatch(BatchVo base);
    
    @Select("SELECT server_id FROM t_goods_server WHERE goods_id = #{goodsId} ")
    List<String> selectListByGoodsId(@Param("goodsId") String goodsId);

}