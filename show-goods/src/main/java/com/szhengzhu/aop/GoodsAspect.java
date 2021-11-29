package com.szhengzhu.aop;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.mapper.GoodsInfoMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Aspect
@Component
public class GoodsAspect {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    
    @Before(value = "@annotation(com.szhengzhu.annotation.CheckStatus)")
    private void before() {
        Date now = DateUtil.date();
        //获取预约上下架商品列表
        List<GoodsInfo> list = goodsInfoMapper.selectPreGoods();
        for (GoodsInfo goodsInfo : list) {
            if( now.getTime() < goodsInfo.getPreUpperTime().getTime()) {
                continue;
            }
            if (now.getTime() < goodsInfo.getPreDownTime().getTime()) {
                goodsInfoMapper.updateAutoUpperStatus(DateUtil.now());
            } else {
                goodsInfoMapper.updateAutoDownStatus(DateUtil.now());
            }
        }
    }
}
