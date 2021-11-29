package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.goods.CookFollow;

/**
 * @author Administrator
 */
public interface CookFollowMapper {
    
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("cookId") String cookId);

    int insert(CookFollow record);

    int insertSelective(CookFollow record);

    CookFollow selectByPrimaryKey(@Param("userId") String userId, @Param("cookId") String cookId);

    int updateByPrimaryKeySelective(CookFollow record);

    int updateByPrimaryKey(CookFollow record);
}