package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.wechat.vo.TeambuyDetail;

public interface TeambuyInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TeambuyInfo record);

    int insertSelective(TeambuyInfo record);

    TeambuyInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TeambuyInfo record);

    int updateByPrimaryKey(TeambuyInfo record);
    
    List<TeambuyInfo> selectByExampleSelective(TeambuyInfo teambuyInfo);
    
    List<Map<String, Object>> selectForeList();
    
    @Select("SELECT mark_id FROM t_teambuy_info")
    List<String> selectIds();
    
    TeambuyDetail selectDetail(@Param("markId") String markId);
    
    List<TeambuyInfo> selectValidData();
    
    @Update("update t_teambuy_info set total_stock = total_stock - 1 where mark_id=#{markId}")
    void subStock(@Param("markId") String markId);
    
    @Update("update t_teambuy_info set total_stock = total_stock + 1 where mark_id=#{markId}")
    void addStock(@Param("markId") String markId);
}