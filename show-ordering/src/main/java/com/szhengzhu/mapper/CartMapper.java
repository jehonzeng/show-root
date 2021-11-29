package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.Cart;
import com.szhengzhu.bean.ordering.UserIndent;
import com.szhengzhu.bean.xwechat.vo.DetailModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CartMapper {

    int deleteByPrimaryKey(String markId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByTableComm(@Param("tableId") String tableId, @Param("commodityId") String commodityId, @Param("priceId") String priceId, @Param("specsItems") String specsItems, @Param("userId") String userId);

    @Delete("delete from t_cart_info where table_id=#{tableId}")
    int clearCartByTable(@Param("tableId") String tableId);

    List<DetailModel> selectVoByTable(@Param("tableId") String tableId);

    List<Cart> selectByTable(@Param("tableId") String tableId);

    @Update("update t_cart_info set table_id=#{tableId} where user_id=#{userId}")
    int updateByUser(@Param("userId") String userId, @Param("tableId") String tableId);

    List<Cart> selectExpire();

    UserIndent userCart(@Param("userId") String userId);
}
