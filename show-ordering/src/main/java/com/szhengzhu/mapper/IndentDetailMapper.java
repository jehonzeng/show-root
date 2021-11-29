package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.IndentDetail;
import com.szhengzhu.bean.ordering.vo.DiscountProductVo;
import com.szhengzhu.bean.xwechat.vo.DetailModel;
import com.szhengzhu.bean.xwechat.vo.IndentTimeModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IndentDetailMapper {

    int deleteByPrimaryKey(String markId);

    int insert(IndentDetail record);

    int insertSelective(IndentDetail record);

    IndentDetail selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(IndentDetail record);

    int updateByPrimaryKey(IndentDetail record);

    int insertBatch(List<IndentDetail> details);

    List<IndentDetail> selectByIndent(@Param("indentId") String indentId);

    List<DetailModel> selectVoByIndent(@Param("indentId") String indentId);

    List<IndentTimeModel> selectIndentTime(@Param("indentId") String indentId);

    List<IndentDetail> selectReturnByComm(@Param("indentId") String indentId, @Param("commodityId") String commodityId, @Param("priceId") String priceId, @Param("specsItems") String specsItems);

    @Update("update t_indent_detail set status=1, modify_time=NOW() where status=0 and indent_id=#{indentId} and FIND_IN_SET(time_code,#{timeCodes})")
    int updateStatus(@Param("indentId") String indentId, @Param("timeCodes") String timeCodes);

    @Select("select GROUP_CONCAT(DISTINCT time_code) from t_indent_detail where status=0 and indent_id=#{indentId}")
    String selectNoCookTimeCode(@Param("indentId") String indentId);

    @Select("select commodity_id as commodityId, price_id as priceId, specs_items as specsItems, quantity from t_indent_detail where status<>-1 and indent_id=#{indentId}")
    List<Map<String, Object>> selectCommByIndent(@Param("indentId") String indentId);

    @Select("SELECT 1 FROM t_indent_detail where status=0 and indent_id=#{indentId} LIMIT 1")
    Integer selectExistNoCookTime(@Param("indentId") String indentId);

    List<IndentDetail> selectMarketDetail(@Param("indentId") String indentId, @Param("marketId") String marketId);

    @Select("SELECT IFNULL(SUM(sale_price * quantity), 0) FROM t_indent_detail WHERE `status`<>-1 AND indent_id=#{indentId,jdbcType=VARCHAR}")
    BigDecimal selectIndentTotal(@Param("indentId") String indentId);

    @Select("SELECT IFNULL(SUM(cost_price * quantity), 0) FROM t_indent_detail WHERE `status`<>-1 AND indent_id=#{indentId,jdbcType=VARCHAR}")
    BigDecimal selectCostTotal(@Param("indentId") String indentId);

    @Select("SELECT user_id FROM t_indent_detail WHERE indent_id=#{indentId} AND user_id IS not null GROUP BY user_id")
    List<String> selectIndentUser(@Param("indentId") String indentId);

    List<IndentDetail> selectDiscountProduct(@Param("indentId") String indentId);

    @Select("SELECT IFNULL(SUM(member_price * quantity), 0) FROM t_indent_detail WHERE `status` <> -1 AND indent_id = #{indentId,jdbcType=VARCHAR}")
    BigDecimal selectMemberDiscountTotal(@Param("indentId") String indentId);

    int updateMemberDiscount(BigDecimal memberDiscount, BigDecimal discount, Date modifyTime,String indentId);

    BigDecimal selectDiscountPrice(String indentId,BigDecimal discount);
}
