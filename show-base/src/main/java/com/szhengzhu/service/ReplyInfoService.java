package com.szhengzhu.service;

import com.szhengzhu.bean.base.ReplyInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface ReplyInfoService {

    /**
     * 获取自动回复信息
     *
     * @param msg
     * @return
     */
    ReplyInfo getInfoByMsg(String msg);

    /**
     * 获取自动回信信息
     *
     * @param markId
     * @return
     */
    ReplyInfo getInfo(String markId);

    /**
     * 获取自动回复分页列表
     *
     * @param page
     * @return
     */
    PageGrid<ReplyInfo> page(PageParam<ReplyInfo> page);

    /**
     * 添加自动回复信息
     *
     * @param replyInfo
     * @return
     */
    void add(ReplyInfo replyInfo);

    /**
     * 修改自动回复信息
     *
     * @param replyInfo
     * @return
     */
    void modify(ReplyInfo replyInfo);
}
