package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.order.BackHistory;
import com.szhengzhu.bean.order.OrderError;
import com.szhengzhu.bean.order.OrderRecord;
import com.szhengzhu.bean.order.RefundBack;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.BackHistoryMapper;
import com.szhengzhu.mapper.OrderErrorMapper;
import com.szhengzhu.mapper.OrderRecordMapper;
import com.szhengzhu.mapper.RefundBackMapper;
import com.szhengzhu.service.BackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Service("backService")
public class BackServiceImpl implements BackService {
    
    @Resource
    private BackHistoryMapper backHistoryMapper;
    
    @Resource
    private RefundBackMapper refundBackMapper;
    
    @Resource
    private OrderErrorMapper orderErrorMapper;
    
    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Override
    public PageGrid<BackHistory> pageOrderBack(PageParam<BackHistory> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("add_time " + pageParam.getSort());
        List<BackHistory> backList = backHistoryMapper.selectByExampleSelective(pageParam.getData());
        PageInfo<BackHistory> pageInfo = new PageInfo<>(backList);
        return new PageGrid<>(pageInfo);
    }
    
    @Override
    public void addOrderBack(BackHistory backHistory) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        backHistory.setMarkId(snowflake.nextIdStr());
        backHistoryMapper.insertSelective(backHistory);
    }

    @Override
    public PageGrid<RefundBack> pageRefundBack(PageParam<RefundBack> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("add_time " + pageParam.getSort());
        List<RefundBack> backList = refundBackMapper.selectByExampleSelective(pageParam.getData());
        PageInfo<RefundBack> pageInfo = new PageInfo<>(backList);
        return new PageGrid<>(pageInfo);
    }
    
    @Override
    public void addRefundBack(RefundBack refundBack) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        refundBack.setMarkId(snowflake.nextIdStr());
        refundBackMapper.insertSelective(refundBack);
    }

    @Override
    public RefundBack getRefundBackInfo(String markId) {
        return refundBackMapper.selectByPrimaryKey(markId);
    }

    @Override
    public void addErrorBack(String orderNo, String userId) {
        int type = Character.getNumericValue(orderNo.charAt(0));
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        OrderError orderError = new OrderError();
        orderError.setMarkId(snowflake.nextIdStr());
        orderError.setOrderNo(orderNo);
        orderError.setErrorInfo("支付回调验证失败！");
        orderError.setErrorType(0);
        orderError.setAddTime(DateUtil.date());
        orderError.setUserMark(userId);
        orderError.setOrderType(type);
        orderErrorMapper.insertSelective(orderError);
    }

    @Override
    public void addOrderRecord(OrderRecord orderRecord) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        orderRecord.setMarkId(snowflake.nextIdStr());
        orderRecordMapper.insertSelective(orderRecord);
    }
}
