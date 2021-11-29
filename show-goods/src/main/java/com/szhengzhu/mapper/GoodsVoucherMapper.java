package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface GoodsVoucherMapper {

    int deleteByPrimaryKey(String markId);

    int insert(GoodsVoucher record);

    int insertSelective(GoodsVoucher record);

    GoodsVoucher selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsVoucher record);

    int updateByPrimaryKey(GoodsVoucher record);

    @Select("SELECT COUNT(*) FROM t_goods_voucher WHERE voucher_name= #{voucherName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("voucherName") String voucherName, @Param("markId") String markId);

    List<GoodsVoucherVo> selectByExampleSelective(GoodsVoucher record);
    
    @Select("SELECT mark_id AS code, voucher_name AS name FROM t_goods_voucher WHERE server_status=1 ORDER BY sort")
    List<Combobox> selectCombobox();
    
    @Select("SELECT v.mark_id AS markId,v.product_id AS productId,v.voucher_name AS voucherName,v.price,v.current_stock AS currentStock,c.content FROM t_goods_voucher v LEFT JOIN t_goods_content c ON v.product_id=c.goods_id WHERE v.mark_id=#{voucherId} AND v.server_status=1 AND current_stock > 0")
    GoodsVoucher selectById(@Param("voucherId") String voucherId);
    
    @Select("SELECT v.mark_id AS goodsId,v.voucher_name AS goodsName,g.sale_price AS basePrice,v.price AS salePrice,v.server_status AS goodsStatus," + 
                  "(SELECT image_path FROM t_goods_image " + 
            "                WHERE goods_id=g.mark_id AND server_type=0 limit 1) AS goodImage " + 
            "FROM t_goods_voucher v LEFT JOIN t_goods_specification g ON (v.product_id=g.goods_id AND g.specification_ids=v.specification_ids) WHERE v.mark_id=#{voucherId}")
    GoodsBase selectCartVoucher(@Param("voucherId") String voucherId);
    
    List<JudgeBase> selectVoucherJudge(@Param("voucherId") String voucherId, @Param("userId") String userId);
    
    GoodsVoucher selectInfoById(@Param("voucherId") String voucherId);

    @Update("update t_goods_voucher set server_status = 0 where product_id = #{goodsId} ")
    int updateStatusByGoods(@Param("goodsId") String goodsId);
    
    @Update("UPDATE t_goods_voucher SET stock=stock-#{quantity} WHERE mark_id=#{voucherId}")
    void subStock(@Param("voucherId") String voucherId, @Param("quantity") int quantity);
    
    @Update("UPDATE t_goods_voucher SET stock=stock+#{quantity} WHERE mark_id=#{voucherId}")
    void addStock(@Param("voucherId") String voucherId, @Param("quantity") int quantity);
    
    @Select("SELECT mark_id FROM t_goods_voucher")
    List<String> selectIds();
}