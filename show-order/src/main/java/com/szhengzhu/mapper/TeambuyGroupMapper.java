package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.order.TeambuyGroup;

/**
 * @author Jehon Zeng
 */
public interface TeambuyGroupMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TeambuyGroup record);

    int insertSelective(TeambuyGroup record);

    TeambuyGroup selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TeambuyGroup record);

    int updateByPrimaryKey(TeambuyGroup record);
    
    List<TeambuyGroup> selectByExampleSelective(TeambuyGroup group);
    
//    @Select("SELECT mark_id, group_no, theme, goods_id, teambuy_id, creator, create_time, req_count, vaild_time, type, current_count, group_status FROM t_teambuy_group WHERE mark_id={groupId}")
//    @ResultMap("BaseResultMap")
//    TeambuyGroup selectById(@Param("groupId") String groupId);
}