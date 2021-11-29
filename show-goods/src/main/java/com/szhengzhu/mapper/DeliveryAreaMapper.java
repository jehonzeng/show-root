package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.DeliveryArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface DeliveryAreaMapper {

    int deleteByPrimaryKey(String markId);

    int insert(DeliveryArea record);

    int insertSelective(DeliveryArea record);

    DeliveryArea selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(DeliveryArea record);

    int updateByPrimaryKey(DeliveryArea record);

    List<DeliveryArea> selectByExampleSelective(DeliveryArea record);

    int insertBatch(List<DeliveryArea> data);

    List<DeliveryArea> selectAreaByAddressId(@Param("addressId") String addressId);

    @Select("SELECT concat(city, ',', area) FROM t_delivery_area WHERE province = #{province} and storehouse_id = #{storehouseId}")
    List<String> exitRecords(@Param("province") String province,
            @Param("storehouseId") String storehouseId);

    @Update("UPDATE t_delivery_area SET server_status = 1 WHERE storehouse_id = #{houseId} AND province= #{province} AND server_status = 0 ")
    int updateByHouseAndProvince(@Param("houseId") String houseId,@Param("province") String province);
}