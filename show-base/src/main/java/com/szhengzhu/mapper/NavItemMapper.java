package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.wechat.vo.NavItemBase;

public interface NavItemMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(NavItem record);

    int insertSelective(NavItem record);

    NavItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(NavItem record);

    int updateByPrimaryKey(NavItem record);
    
    List<NavItem> selectByExampleSelective(NavItem data);
    
    @Select("SELECT theme,link_url AS linkUrl,image_path AS imagePath FROM t_nav_item WHERE nav_id=#{navId} AND server_status=1 AND start_time < NOW() AND end_time > NOW() ORDER BY sort")
    List<NavItemBase> selectServerItem(@Param("navId") String navId);

    @Select("SELECT COUNT(*) FROM t_nav_item WHERE theme = #{theme} AND mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);
}