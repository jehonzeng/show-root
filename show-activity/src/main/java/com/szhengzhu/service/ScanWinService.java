package com.szhengzhu.service;

import com.szhengzhu.bean.activity.ScanWin;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface ScanWinService {

    /**
     * 获取抽奖活动分页列表
     * 
     * @param page
     * @return
     */
    PageGrid<ScanWin> page(PageParam<ScanWin> page);

    /**
     * 获取抽奖活动详细信息
     * 
     * @param markId
     * @return
     */
    ScanWin getInfo(String markId);

    /**
     * 添加抽奖活动信息
     * 
     * @param win
     * @return
     */
    void add(ScanWin win);

    /**
     * 修改抽奖活动信息
     * @param win
     * @return
     */
    void modify(ScanWin win);
    
    /**
     * 用户扫码抽奖
     * 
     * @param scanCode
     * @param openId
     * @return
     */
    String scanWin(String scanCode, String openId);
}
