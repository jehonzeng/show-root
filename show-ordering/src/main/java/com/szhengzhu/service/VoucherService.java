package com.szhengzhu.service;

import com.szhengzhu.bean.excel.VoucherCodeExcel;
import com.szhengzhu.bean.ordering.Voucher;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface VoucherService {

    /**
     * 获取代金券分页列表
     *
     * @param param
     * @return
     */
    PageGrid<Voucher> page(PageParam<Voucher> param);

    /**
     * 添加代金券
     *
     * @param voucher
     * @return
     */
    void add(Voucher voucher) throws InterruptedException;

    /**
     * 修改代金券描述
     *
     * @param voucher
     * @return
     */
    void modify(Voucher voucher);

    /**
     * 下载代金券码
     *
     * @param voucherId
     * @return
     */
    List<VoucherCodeExcel> listCode(String voucherId);

    /**
     * 通过code获取信息
     * @param code
     * @return
     */
    Voucher getCodeInfo(String code);

    /**
     * 确认使用券
     *
     * @param code
     * @return
     */
    void useCode(String code, Integer amount);
}
