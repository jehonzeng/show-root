package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.ordering.UserIndent;
import com.szhengzhu.bean.ordering.param.IndentPageParam;
import com.szhengzhu.bean.ordering.print.PrintIncome;
import com.szhengzhu.bean.ordering.vo.IndentBaseVo;
import com.szhengzhu.bean.ordering.vo.IndentVo;
import com.szhengzhu.bean.xwechat.vo.IndentModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IndentMapper {

    int deleteByPrimaryKey(String markId);

    int insert(Indent record);

    int insertSelective(Indent record);

    Indent selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Indent record);

    int updateByPrimaryKey(Indent record);

    Indent selectByNo(@Param("indentNo") String indentNo);

    @Update("update t_indent_info set base_total=#{baseTotal} where mark_id=#{indentId}")
    int updateBaseTotal(@Param("indentId") String indentId, @Param("baseTotal") BigDecimal baseTotal);

    @Update("update t_indent_info set man_num=#{manNum} where table_id=#{tableId} and temp_num=#{tempNum}")
    int updateManNum(@Param("tableId") String tableId, @Param("tempNum") String tempNum,
                     @Param("manNum") Integer manNum);

    Indent selectByTable(@Param("tableId") String tableId, @Param("tempNum") String tempNum);

    IndentBaseVo selectBaseDetailById(@Param("indentId") String indentId);

    List<IndentVo> selectManageList(IndentPageParam indent);

    @Update("update t_indent_info set indent_status = #{indentStatus} where mark_id = #{indentId}")
    int updateIndentStatus(@Param("indentId") String indentId, @Param("indentStatus") String indentStatus);

    @Update("update t_indent_info set member_id=#{memberId} where mark_id = #{indentId}")
    int bindMember(@Param("indentId") String indentId, @Param("memberId") String memberId);

    List<IndentModel> selectXModelByUser(@Param("userId") String userId);

    IndentModel selectXModelById(@Param("indentId") String indentId);

    @Update("update t_indent_info set indent_status='IS05' where indent_status in ('IS01', 'IS02', 'IS03')")
    void updateExpireStatus();

    //    @Select("SELECT COUNT(1) AS quantity, SUM(indent_total) AS income, IFNULL(SUM(p.amount),0) AS paidIncome FROM t_indent_info i LEFT JOIN t_indent_pay p ON p.indent_id=i.mark_id WHERE indent_status='IS04' AND p.`status`=1 AND TO_DAYS(bill_time) between TO_DAYS(#{startDate}) and TO_DAYS(#{endDate}) AND store_id=#{storeId}")
    PrintIncome selectIncome(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") String storeId);

    @Select("select table_id from t_indent_info where mark_id=#{indentId}")
    String selectTableIdByIndentId(@Param("indentId") String indentId);

    @Select("select indent_total from t_indent_info where mark_id=#{indentId}")
    BigDecimal selectTotalById(@Param("indentId") String indentId);

    IndentInfo selectById(@Param("markId") String markId);

    UserIndent userIndent(@Param("userId")String userId,@Param("memberId")String memberId);
}
