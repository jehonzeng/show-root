package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.StoreItem;
import com.szhengzhu.bean.vo.StoreStaffVo;

public interface StoreItemMapper {
    int deleteByPrimaryKey(StoreItem key);

    int insert(StoreItem record);

    int insertSelective(StoreItem record);

    List<StoreStaffVo> selectByExampleSelective(StoreItem data);

    int insertBatch(List<StoreItem> list);

    @Delete("delete from t_store_item where store_id = #{storeId} and user_id = #{userId}")
    int deleteItem(@Param("storeId") String storeId,@Param("userId") String userId);
    
    @Select("SELECT store_id FROM t_store_item WHERE user_id = #{userId}")
    String selectByStaff(@Param("userId") String userId);
}