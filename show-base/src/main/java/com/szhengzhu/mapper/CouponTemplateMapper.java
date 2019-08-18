package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.provider.TemplateProvider;

public interface CouponTemplateMapper {

    int deleteByPrimaryKey(String markId);

    int insert(CouponTemplate record);

    int insertSelective(CouponTemplate record);

    CouponTemplate selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(CouponTemplate record);

    int updateByPrimaryKey(CouponTemplate record);

    List<CouponTemplate> selectByExampleSelective(CouponTemplate couponTemplate);

    @SelectProvider(type = TemplateProvider.class, method = "selectByMark")
    CouponTemplate selectByMark(@Param("templateId") String templateId);

    @Select("SELECT mark_id AS code,coupon_name AS name FROM t_coupon_template WHERE server_status = 1 AND coupon_type = #{couponType}")
    List<Combobox> selectComboboxList(@Param("couponType") Integer couponType);
}