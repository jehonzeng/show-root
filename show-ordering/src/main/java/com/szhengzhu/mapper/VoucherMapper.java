package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Voucher;

import java.util.List;

public interface VoucherMapper {

    int deleteByPrimaryKey(String markId);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);

    List<Voucher> selectByExampleSelective(Voucher voucher);
}