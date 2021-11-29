package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.PacksItem;
import com.szhengzhu.bean.vo.PacksVo;

public interface PacksItemMapper {
    int deleteByPrimaryKey(String markId);

    int insert(PacksItem record);

    int insertSelective(PacksItem record);

    PacksItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PacksItem record);

    int updateByPrimaryKey(PacksItem record);

    @Select("SELECT template_id FROM t_packs_item WHERE packs_id =#{packsId} and  server_status = 1")
    List<String> selectTemplates(@Param("packsId") String packsId);
    
    @Select("select count(*) from db_order.t_user_coupon where user_id = #{userId} and template_id= #{templateId}")
    int isExitsCoupon(@Param("userId") String userId,@Param("templateId") String templateId);

    List<PacksVo> selectByExampleSelective(PacksItem data);

    int insertBatch(List<PacksItem> items);
}