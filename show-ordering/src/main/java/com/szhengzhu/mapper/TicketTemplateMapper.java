package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.TicketTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface TicketTemplateMapper {
    int deleteByPrimaryKey(String markId);

    int insert(TicketTemplate record);

    int insertSelective(TicketTemplate record);

    TicketTemplate selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TicketTemplate record);

    int updateByPrimaryKey(TicketTemplate record);

    List<TicketTemplate> selectByExampleSelective(TicketTemplate data);

    int updateBatchStatus(@Param("templateIds") String[] templateIds,@Param("status") Integer status);

    @Update("update t_ticket_template set status = #{status} where mark_id = #{markId}")
    int updateByTempalteId(@Param("markId") String markId, @Param("status") int status);

    @Select("select mark_id as templateId, name, description from t_ticket_template where status = 1 AND (CASE WHEN start_time IS NOT NULL THEN start_time < NOW() ELSE 1 = 1 END) " +
            "AND ( CASE WHEN stop_time IS NOT NULL THEN stop_time > NOW() ELSE 1 = 1 END ) order by create_time desc")
    List<Map<String, String>> selectCombobox();
}
