package com.szhengzhu.schedule;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.ordering.Cart;
import com.szhengzhu.code.IntegralCode;
import com.szhengzhu.mapper.CartMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Component
public class CartSchedule {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    @Scheduled(cron = "0 */1 * * * ?")
    public void autoCleanCart() {
        List<Cart> carts = cartMapper.selectExpire();
        for (Cart cart : carts) {
            addMemberIntegral(cart.getPriceType(), cart.getUserId(), cart.getIntegralPrice(), cart.getQuantity());
            cartMapper.deleteByPrimaryKey(cart.getMarkId());
        }
    }

    /**
     * 退还积分
     *
     * @param userId
     * @param integralPrice
     * @param quantity
     */
    private void addMemberIntegral(int priceType, String userId, int integralPrice, int quantity) {
        if (priceType != 1 || StrUtil.isEmpty(userId)) {
            return;
        }
        IntegralDetail detail = IntegralDetail.builder().userId(userId)
                .integralLimit(integralPrice * quantity).status(1).build();
        detail.setIntegralType(IntegralCode.EXCHANGE_WITHDRAW);
        showMemberClient.addIntegral(detail);
    }
}
