package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.wechat.vo.SeckillDetail;

public interface SeckillInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SeckillInfo record);

    int insertSelective(SeckillInfo record);

    SeckillInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SeckillInfo record);

    int updateByPrimaryKey(SeckillInfo record);
    
    List<SeckillInfo> selectByExampleSelective(SeckillInfo seckillInfo);
    
    @Select("SELECT mark_id FROM t_seckill_info")
    List<String> selectIds();
    
    List<Map<String, Object>> selectForeList();
    
    SeckillDetail selectDetail(@Param("markId") String markId);
    
    List<SeckillInfo> selectValidData();
    
    @Update("update t_seckill_info set total_stock = total_stock - 1 where mark_id=#{markId}")
    void subStock(@Param("markId") String markId);
    
    @Update("update t_seckill_info set total_stock = total_stock + 1 where mark_id=#{markId}")
    void addStock(@Param("markId") String markId);
}