package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.base.CounselInfo;

public interface CounselInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(CounselInfo record);

    int insertSelective(CounselInfo record);

    CounselInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(CounselInfo record);

    int updateByPrimaryKey(CounselInfo record);

    List<CounselInfo> selectByExampleSelective(CounselInfo data);
}