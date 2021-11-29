package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Income;
import com.szhengzhu.bean.ordering.IndentPay;
import com.szhengzhu.bean.ordering.print.PrintPay;
import com.szhengzhu.bean.ordering.vo.IndentPayVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IndentPayMapper {

    int deleteByPrimaryKey(String markId);

    int insert(IndentPay record);

    int insertSelective(IndentPay record);

    IndentPay selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(IndentPay record);

    int updateByPrimaryKey(IndentPay record);

    @Select("SELECT IFNULL(SUM(pay_amount), 0) FROM t_indent_pay WHERE indent_id=#{indentId} and status=1")
    BigDecimal selectPayTotal(@Param("indentId") String indentId);

    @Select("SELECT IFNULL(SUM(amount), 0) FROM t_indent_pay WHERE indent_id=#{indentId} and status=1")
    BigDecimal selectAmount(@Param("indentId") String indentId);

    @Select("SELECT IFNULL(SUM(pay_amount), 0) FROM t_indent_pay WHERE indent_id=#{indentId} and (user_id is null or user_id=#{userId}) and status=1 and pay_id != #{payId}")
    BigDecimal selectPayTotalByUser(@Param("indentId") String indentId, @Param("userId") String userId, @Param("payId") String payId);

    List<IndentPayVo> selectIndentPayVo(@Param("indentId") String indentId);

    List<PrintPay> selectIncomePay(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") String storeId);

    int insertBatch(List<IndentPay> list);

    @Update("update t_indent_pay set status=#{status},modify_time=NOW() where indent_id=#{indentId} and user_id=#{userId} and status=0")
    int updateStatusByUser(@Param("indentId") String indentId, @Param("userId") String userId, @Param("status") Integer status);

    List<IndentPay> selectOtherPay(@Param("indentId") String indentId);

    @Update("update t_indent_pay set status=#{status},modify_time=NOW() where mark_id=#{payId}")
    int updateStatusById(@Param("payId") String payId, @Param("status") Integer status);

//    @Select("SELECT count(1) FROM t_indent_pay WHERE indent_id=#{indentId}")
//    Integer selectCountByIndent(@Param("indentId") String indentId);

    List<IndentPayVo> selectPayDiscount(@Param("indentId") String indentId);

    int updateMemberDiscount(@Param("indentId") String indentId, @Param("payId") String payId, @Param("status") Integer status, @Param("payAmount") BigDecimal payAmount);

    List<IndentPay> selectByPayId(@Param("indentId") String indentId, @Param("payId") String payId);
}
