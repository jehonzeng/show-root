package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.vo.Combobox;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface GoodsTypeMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);
    
    @Select("SELECT count(*) FROM t_goods_type WHERE type_name = #{typeName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("typeName")String typeName,@Param("markId")String markId);
    
    List<GoodsType> selectByExampleSelective(GoodsType record);
    
    @Select("SELECT mark_id AS code, type_name AS name FROM t_goods_type WHERE server_status=1 ORDER BY sort")
    List<Combobox> selectCombobox();
}