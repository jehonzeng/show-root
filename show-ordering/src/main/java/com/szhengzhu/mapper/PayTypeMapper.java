package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.PayType;
import com.szhengzhu.bean.vo.Combobox;

public interface PayTypeMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(PayType record);

    int insertSelective(PayType record);

    PayType selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PayType record);

    int updateByPrimaryKey(PayType record);
    
    List<PayType> selectByExampleSelective(PayType payType);
    
    @Update("update t_pay_type set status=#{status}, modify_time=NOW() where mark_id=#{markId}")
    int updateStatus(@Param("markId") String markId, @Param("status") int status);
    
    @Select("select mark_id AS code, name, code as other from t_pay_type where store_id=#{storeId} and status=1 ORDER BY sort")
    List<Combobox> selectCombobox(@Param("storeId") String storeId);
}