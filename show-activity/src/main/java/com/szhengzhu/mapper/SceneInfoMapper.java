package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.activity.SceneInfo;

public interface SceneInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SceneInfo record);

    int insertSelective(SceneInfo record);

    SceneInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SceneInfo record);

    int updateByPrimaryKey(SceneInfo record);
    
    List<SceneInfo> selectByExampleSelective(SceneInfo sceneInfo);
}