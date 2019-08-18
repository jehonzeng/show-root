package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.goods.DeliveryArea;

public interface DeliveryAreaMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(DeliveryArea record);

    int insertSelective(DeliveryArea record);

    DeliveryArea selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(DeliveryArea record);

    int updateByPrimaryKey(DeliveryArea record);
    
    List<DeliveryArea> selectByExampleSelective(DeliveryArea record);

    int insertBatch(List<DeliveryArea> data);

}