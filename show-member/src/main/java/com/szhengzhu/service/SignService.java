package com.szhengzhu.service;

import com.szhengzhu.bean.member.SignDetail;
import com.szhengzhu.bean.member.SignMember;
import com.szhengzhu.bean.member.SignRule;
import com.szhengzhu.bean.member.param.SignInfoParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author makejava
 * @since 2021-06-07 16:15:12
 */
public interface SignService {


    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    SignInfoParam selectBySignId(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param signMember 实例对象
     * @return 对象列表
     */
    List<SignInfoParam> selectBySignMember(SignMember signMember);

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    SignRule queryBySignRuleId(String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param signRule 实例对象
     * @return 对象列表
     */
    List<SignRule> querySignRuleList(SignRule signRule);

    /**
     * 新增数据
     *
     * @param signRule 实例对象
     */
    void addSignRule(SignRule signRule);

    /**
     * 修改数据
     *
     * @param signRule 实例对象
     */
    void modifySignRule(SignRule signRule);

    /**
     * 通过实体对象筛选查询
     *
     * @param param 分页对象
     * @return 对象列表
     */
    PageGrid<SignDetail> querySignDetailList(PageParam<SignDetail> param);

    /**
     * 通过查询单条数据
     *
     * @param signMember
     * @return 实例对象
     */
    SignInfoParam queryByUserId(SignMember signMember);

    void deleteBySignRuleId(String markId);
}
