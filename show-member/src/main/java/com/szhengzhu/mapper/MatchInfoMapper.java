package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MatchInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchInfo record);

    int insertSelective(MatchInfo record);

    MatchInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchInfo record);

    int updateByPrimaryKey(MatchInfo record);

    List<MatchInfo> selectByExampleSelective(MatchInfo record);

    List<Map<String, Object>> selectList();

    @Select("select count(1) from t_match_info where status=1")
    int selectCount();

    MatchInfo selectMatchInfo(String markId);

    List<MatchInfo> selectMatchTime();
}
