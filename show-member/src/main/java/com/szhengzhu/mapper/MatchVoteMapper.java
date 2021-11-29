package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchVote;
import com.szhengzhu.bean.member.VoteInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatchVoteMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchVote record);

    int insertSelective(MatchVote record);

    MatchVote selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchVote record);

    int updateByPrimaryKey(MatchVote record);

    List<VoteInfo> selectVoteInfo(@Param("matchId") String matchId, @Param("teamId") String teamId,
                                  @Param("stageId") String stageId);
}
