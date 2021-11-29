package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.SignMember;
import com.szhengzhu.bean.member.param.SignInfoParam;
import feign.Param;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-07 16:15:15
 */
public interface SignMemberMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    SignInfoParam queryById(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param signMember 实例对象
     * @return 对象列表
     */
    List<SignInfoParam> queryAll(SignMember signMember);

    /**
     * 新增数据
     *
     * @param signMember 实例对象
     */
    void add(SignMember signMember);

    /**
     * 修改数据
     *
     * @param signMember 实例对象
     */
    void modify(SignMember signMember);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     */
    void deleteById(String markId);

    SignInfoParam queryByUserId(@Param("userId") String userId,@Param("year")  Integer year,@Param("month") Integer month);
}
