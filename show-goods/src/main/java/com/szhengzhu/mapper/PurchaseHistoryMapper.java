package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;

import java.util.List;

/**
 * @author Administrator
 */
public interface PurchaseHistoryMapper {

    int deleteByPrimaryKey(String markId);

    int insert(PurchaseHistory record);

    int insertSelective(PurchaseHistory record);

    PurchaseHistory selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PurchaseHistory record);

    int updateByPrimaryKey(PurchaseHistory record);

    List<PurchaseHistoryVo> selectByExampleSelective(PurchaseHistory data);
}