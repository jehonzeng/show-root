package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.vo.IntegralVo;
import com.szhengzhu.provider.UserIntegralProvider;

public interface UserIntegralMapper {
    
    int insert(UserIntegral record);

    int insertSelective(UserIntegral record);
    
    List<UserIntegral> selectByExampleSelective(UserIntegral userIntegral);
    
    @SelectProvider(type = UserIntegralProvider.class, method = "selectUserTotal")
    List<IntegralVo> selectUserTotal(@Param("nickName") String nickName, @Param("phone") String phone);
}