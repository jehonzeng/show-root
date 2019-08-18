package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.wechat.vo.VoucherBase;

public interface UserVoucherMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(UserVoucher record);

    int insertSelective(UserVoucher record);

    UserVoucher selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(UserVoucher record);

    int updateByPrimaryKey(UserVoucher record);
    
    List<VoucherBase> selectByUser(@Param("userId") String userId);
    
    GoodsVoucher selectProductInfoById(@Param("goodsVoucherId") String goodsVoucherId);
    
    int insertBatch(List<UserVoucher> voucherList);
    
    @Select("SELECT mark_id AS markId, product_id AS productId,specification_ids AS specificationIds,voucher_name AS voucherName FROM db_goods.t_goods_voucher WHERE mark_id=#{markId}")
    GoodsVoucher selectGoodsVoucher(@Param("markId") String markId); // 为了添加数据
}