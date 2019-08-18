package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.wechat.vo.Label;

public interface LabelInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(LabelInfo record);

    int insertSelective(LabelInfo record);

    LabelInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(LabelInfo record);

    int updateByPrimaryKey(LabelInfo record);

    @Select("SELECT COUNT(*) FROM t_label_info WHERE theme = #{theme} AND mark_id <> #{markId}")
    int repeatRecords(@Param("theme") String theme, @Param("markId") String markId);
    
    List<LabelInfo> selectByExampleSelective(LabelInfo record);
    
    @Select("SELECT mark_id AS labelId,theme,image_path AS imagePath, server_type AS type FROM t_label_info WHERE server_status=1 ORDER BY sort")
    List<Label> selectLabel();
}