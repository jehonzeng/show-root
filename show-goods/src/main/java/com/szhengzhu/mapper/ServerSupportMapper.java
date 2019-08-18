package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.vo.Combobox;

public interface ServerSupportMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ServerSupport record);

    int insertSelective(ServerSupport record);

    ServerSupport selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ServerSupport record);

    int updateByPrimaryKey(ServerSupport record);

    @Select("SELECT COUNT(*) FROM t_server_support WHERE theme = #{theme} AND mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);

    @Select("SELECT mark_id AS code,theme AS name FROM t_server_support  WHERE server_status = 1 ")
    List<Combobox> selectServeList();

    List<ServerSupport> selectByExampleSelective(ServerSupport data);
    
    @Select("SELECT s.theme, s.detail_explain AS detailExplain FROM t_goods_server g LEFT JOIN t_server_support s ON g.server_id=s.mark_id WHERE g.goods_id=#{goodsId} AND s.server_status=1 ORDER BY g.sort")
    List<Map<String, String>> selectByGoods(@Param("goodsId") String goodsId);

}