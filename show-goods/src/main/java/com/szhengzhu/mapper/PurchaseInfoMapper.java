package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.vo.FoodCount;
import com.szhengzhu.bean.vo.PurchaseFood;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface PurchaseInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(PurchaseInfo record);

    int insertSelective(PurchaseInfo record);

    PurchaseInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PurchaseInfo record);

    int updateByPrimaryKey(PurchaseInfo record);

    List<PurchaseFood> selectByExampleSelective(PurchaseInfo data);

    int insertBatch(List<PurchaseInfo> list);
    
    @Update("UPDATE t_purchase_info SET user_id = NULL,server_status = #{serverStatus},buy_total= #{buyTotal} WHERE mark_id = #{markId}")
    int updateBuyTotal(PurchaseInfo temp);
    
    @Update("UPDATE t_purchase_info SET user_id = #{userId},server_status = 1  WHERE mark_id = #{markId} AND server_status = 0")
    int updateInfoByIdAndStatus(@Param("markId") String markId, @Param("userId") String userId);

    @Update("UPDATE t_purchase_info SET user_id = NULL ,server_status = 0 WHERE mark_id = #{markId} AND server_status = 1")
    int updateStatusByIdAndStatus(@Param("markId") String markId);

    @Delete("DELETE FROM t_purchase_info WHERE buy_time = #{buyTime}")
    int deletePurchaseOrders(@Param("buyTime") String buyTime);

    @Select("SELECT foodId,SUM(IFNULL(foodConsume,0)) AS totalCount FROM v_purchase_data GROUP BY foodId ")
    List<FoodCount> selectTodayList();
    
    @Delete("DELETE FROM t_purchase_info WHERE buy_time = #{buyTime} AND reflash_time != #{reflashTime} AND buy_total = 0")
    int deleteBatchPurchase(@Param("buyTime")String buyTime,@Param("reflashTime") String reflashTime);
    
    @Update("UPDATE t_purchase_info SET purchase_total = 0,reflash_time = #{reflashTime} WHERE buy_time = #{buyTime} AND reflash_time != #{reflashTime} AND buy_total > 0")
    int updateBatchPurchase(@Param("buyTime")String buyTime,@Param("reflashTime") String reflashTime);

    List<PurchaseFood> selectListByStatus(@Param("userId")String userId,@Param("status") Integer status);

}