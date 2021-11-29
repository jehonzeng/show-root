package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.activity.GiftInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface GiftInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(GiftInfo record);

    int insertSelective(GiftInfo record);

    GiftInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GiftInfo record);

    int updateByPrimaryKey(GiftInfo record);

    List<GiftInfo> selectByExampleSelective(GiftInfo data);

    @Select("select count(*) from t_gift_info where gift_name = #{giftName} and mark_id<> #{markId}")
    int repeatRecoreds(@Param("giftName") String giftName, @Param("markId") String markId);

    @Select("select mark_id as `code`,gift_name as `name` from t_gift_info where gift_status = 1")
    List<Combobox> selectGiftCombobox();
}