package com.szhengzhu.service;

import com.szhengzhu.bean.base.ScanReply;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface ScanReplyService {

    /**
     * 根据code获取信息
     *
     * @param code
     * @return
     */
    List<ScanReply> listByCode(String code);

    /**
     * 获取扫码回复信息
     *
     * @param markId
     * @return
     */
    ScanReply getInfo(String markId);

    /**
     * 获取扫码回复分页列表
     *
     * @param page
     * @return
     */
    PageGrid<ScanReply> page(PageParam<ScanReply> page);

    /**
     * 添加信息
     *
     * @param replyInfo
     * @return
     */
    void add(ScanReply replyInfo);

    /**
     * 修改信息
     *
     * @param replyInfo
     * @return
     */
    void modify(ScanReply replyInfo);
}
