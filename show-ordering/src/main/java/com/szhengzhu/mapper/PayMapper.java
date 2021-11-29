package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.Pay;
import com.szhengzhu.bean.ordering.vo.PayBaseVo;

public interface PayMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Pay record);

    int insertSelective(Pay record);

    Pay selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Pay record);

    int updateByPrimaryKey(Pay record);
    
    List<Pay> selectByExampleSelective(Pay pay);
    
    @Update("update t_pay_info set status=#{status}, modify_time=NOW() where mark_id=#{markId}")
    int updateStatus(@Param("markId") String markId, @Param("status") int status);
    
    List<PayBaseVo> selectResPay(@Param("storeId") String storeId);
    
    PayBaseVo selectById(@Param("payId") String payId);
}