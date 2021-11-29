package com.szhengzhu.service;

import com.szhengzhu.bean.member.RechargeRule;
import com.szhengzhu.bean.member.vo.TicketTemplateVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface RechargeService {

    /**
     * 充值活动分页
     * @param base
     * @return
     */
    PageGrid<RechargeRule> page(PageParam<RechargeRule> base);

    /**
     * 获取充值活动信息
     * @param markId
     * @return
     */
    RechargeRule getInfo(String markId);

    /**
     * 添加充值活动
     *
     * @param base
     */
    void add(RechargeRule base);

    /**
     * 修改充值活动
     *
     * @param base
     */
    void modify(RechargeRule base);

    /**
     * 删除充值活动信息
     *
     * @param ruleId
     */
    void delete(String ruleId);

    /**
     * 修改充值活动状态
     * @param ruleIds
     * @param status
     */
    void modifyStatus(String[] ruleIds, Integer status);

    /**
     * 获取充值模板， 不包括ruleType=1
     *
     * @return
     */
    List<RechargeRule> list();

    /**
     * 获取充值设定赠送的券列表
     * @param ruleId
     */
    List<TicketTemplateVo> getRechargeTickets(String ruleId);

    /**
     * 获取模板键值对
     *
     * @return
     */
    List<Combobox> listCombobox();

}
