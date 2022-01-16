package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchChance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MatchChanceMapper {

    int deleteByPrimaryKey(String userId);

    int insert(MatchChance record);

    int insertSelective(MatchChance record);

    MatchChance selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(MatchChance record);

    int updateByPrimaryKey(MatchChance record);

    @Select("select if(total_count-used_count < 0, 0, total_count-used_count) from t_match_chance where user_id=#{userId}")
    Integer selectUserCount(@Param("userId") String userId);

    @Update("update t_match_chance set used_count=used_count + #{quantity}, modify_time=NOW() where user_id=#{userId}")
    void updateUseCount(@Param("userId") String userId, @Param("quantity") Integer quantity);

    int updateExpiredUserChance();
}
