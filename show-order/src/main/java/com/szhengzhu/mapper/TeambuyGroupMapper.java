package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.order.TeambuyGroup;

public interface TeambuyGroupMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TeambuyGroup record);

    int insertSelective(TeambuyGroup record);

    TeambuyGroup selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TeambuyGroup record);

    int updateByPrimaryKey(TeambuyGroup record);
    
    List<TeambuyGroup> selectByExampleSelective(TeambuyGroup group);
    
    @Select("SELECT mark_id, group_no, theme, product_id, product_type, super_no, teambuy_id, creator, create_time, req_count, vaild_time, "
            + "modifier, modify_time, type, current_count, server_status FROM t_teambuy_order WHERE group_no={groupNo}")
    TeambuyGroup selectByNo(@Param("groupNo") String groupNo);
}