package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.IncreaseBase;

public interface SpecialInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SpecialInfo record);

    int insertSelective(SpecialInfo record);

    SpecialInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SpecialInfo record);

    int updateByPrimaryKey(SpecialInfo record);
    
    @Select("select count(*) from t_special_info where theme = #{theme} and mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);

    List<SpecialInfo> selectByExampleSelective(SpecialInfo data);

    @Select("SELECT s.mark_id AS code,s.theme AS name FROM t_special_info s WHERE NOT EXISTS(SELECT i.mark_id FROM t_special_item i WHERE i.special_id = s.mark_id AND i.goods_id = #{goodsId}) AND s.server_status = 1")
    List<Combobox> selectSpecialListByGoods(@Param("goodsId") String goodsId);
    
    List<IncreaseBase> selectCartIncrease();
    
    @Select("SELECT GROUP_CONCAT(attr_value SEPARATOR ' ') FROM db_goods.t_specification_info WHERE FIND_IN_SET(mark_id, #{specIds})")
    String selectBySpecIds(@Param("specIds") String specIds);
    
    
}