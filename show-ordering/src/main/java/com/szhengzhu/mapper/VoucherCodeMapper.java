package com.szhengzhu.mapper;

import com.szhengzhu.bean.excel.VoucherCodeExcel;
import com.szhengzhu.bean.ordering.VoucherCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface VoucherCodeMapper {

    int deleteByPrimaryKey(String code);

    int insert(VoucherCode record);

    int insertSelective(VoucherCode record);

    VoucherCode selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(VoucherCode record);

    int updateByPrimaryKey(VoucherCode record);

    @Select("SELECT code FROM t_voucher_code WHERE `status`= 1 AND voucher_id=#{voucherId}")
    List<VoucherCodeExcel> selectVoucherCode(@Param("voucherId") String voucherId);

    int insertBatch(List<VoucherCode> list);

    @Update("update t_voucher_code set use_time=NOW() where code=#{code}")
    int useByCode(@Param("code") String code);
}