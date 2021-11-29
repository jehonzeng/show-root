package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.vo.Combobox;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface IconInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(IconInfo record);

    int insertSelective(IconInfo record);

    IconInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(IconInfo record);

    int updateByPrimaryKey(IconInfo record);

    @Select("SELECT COUNT(*) FROM t_icon_info WHERE theme = #{theme} AND mark_id <> #{markId}")
    int repeatRecoreds(@Param("theme") String theme, @Param("markId") String markId);
    
    List<IconInfo> selectByExampleSelective(IconInfo record);

    @Select("SELECT c.mark_id AS code,c.theme AS name FROM t_icon_info c WHERE NOT EXISTS(SELECT i.mark_id FROM t_icon_item i WHERE i.icon_id = c.mark_id AND i.goods_id = #{goodsId}) AND c.server_status = 1 ")
    List<Combobox> selectComboboxByGoods(String goodsId);
}