package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.DiscountInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface DiscountInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(DiscountInfo record);

    int insertSelective(DiscountInfo record);

    DiscountInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(DiscountInfo record);

    int updateByPrimaryKey(DiscountInfo record);

    List<DiscountInfo> selectByExampleSelective(DiscountInfo data);

    @Update("update t_discount_info set status = #{status} where mark_id = #{discountId}")
    int updateByDiscountId(@Param("discountId") String discountId, @Param("status") int status);

    int updateBatchStatus(@Param("discountIds") String[] discountIds,@Param("status") Integer status);
    
    List<Combobox> selectCombobox(@Param("employeeId") String employeeId, @Param("storeId") String storeId);
}