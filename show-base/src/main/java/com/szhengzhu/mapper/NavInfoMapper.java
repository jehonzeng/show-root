package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.wechat.vo.NavVo;

public interface NavInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(NavInfo record);

    int insertSelective(NavInfo record);

    NavInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(NavInfo record);

    int updateByPrimaryKey(NavInfo record);

    @Select("SELECT COUNT(*) FROM t_nav_info WHERE nav_code = #{navCode} AND mark_id <> #{markId}")
    int repeatRecords(@Param("navCode") String navCode, @Param("markId") String markId);

    List<NavInfo> selectByExampleSelective(NavInfo data);
    
    List<NavVo> selectForeNav();
}