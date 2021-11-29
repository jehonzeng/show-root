package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface PartnerInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(PartnerInfo record);

    int insertSelective(PartnerInfo record);

    PartnerInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PartnerInfo record);

    int updateByPrimaryKey(PartnerInfo record);
    
    List<PartnerInfo> selectByExampleSelective(PartnerInfo data);

    @Select("SELECT COUNT(*) FROM t_partner_info WHERE `name`= #{name} AND mark_id <> #{markId}")
    int repeatRecords(@Param("name") String name,@Param("markId") String markId);
    
    @Select("SELECT identification_code as code, name FROM t_partner_info")
    List<Combobox> selectCombobox();
}