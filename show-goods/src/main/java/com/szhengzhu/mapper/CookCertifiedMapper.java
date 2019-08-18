package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.wechat.vo.Cooker;

public interface CookCertifiedMapper {
    
    int insert(CookCertified record);

    int insertSelective(CookCertified record);
    
    int updateByPrimaryKeySelective(CookCertified record);
    
    List<CookCertified> selectByExampleSelective(CookCertified cookCertified);

    @Select("SELECT COUNT(*) FROM t_cook_certified  WHERE short_name = #{shortName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("shortName") String shortName, @Param("markId") String markId);
    
    List<Cooker> selectCookList(@Param("userId") String userId, @Param("number") Integer number);
    
    Cooker selectByUser(@Param("cooker") String cooker, @Param("userId") String userId);
}