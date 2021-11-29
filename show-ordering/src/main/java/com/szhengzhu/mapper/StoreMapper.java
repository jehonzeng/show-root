package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.Store;
import com.szhengzhu.bean.ordering.param.StoreParam;
import com.szhengzhu.bean.ordering.vo.StoreMapVo;
import com.szhengzhu.bean.xwechat.vo.StoreModel;

public interface StoreMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);
    
    List<Store> selectByExampleSelective(StoreParam param);
    
    @Update("update t_store_info set status = #{status}, modify_time=NOW() where mark_id = #{storeId}")
    int updateStatus(@Param("storeId") String storeId, @Param("status") Integer status);
    
    @Select("SELECT mark_id AS storeId, `name` from t_store_info s LEFT JOIN t_store_employee e ON s.mark_id=e.store_id WHERE e.employee_id=#{employeeId}")
    List<StoreMapVo> selectMapByEmployee(@Param("employeeId") String employeeId);
    
    List<StoreModel> selectLjsStoreByCity(@Param("city") String city);
}