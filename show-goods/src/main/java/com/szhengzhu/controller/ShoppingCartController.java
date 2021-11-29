package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.wechat.vo.CalcData;
import com.szhengzhu.bean.wechat.vo.CartModel;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.service.ShoppingCartService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @PostMapping(value = "/add")
    public ShoppingCart add(@RequestBody @Validated ShoppingCart cart) {
        return shoppingCartService.addCart(cart);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated ShoppingCart cart) {
        shoppingCartService.modifyCart(cart);
    }

    @PatchMapping(value = "/refresh")
    public void Refresh(@RequestBody CartModel cartModel) {
        shoppingCartService.refresh(cartModel.getUserId(), cartModel.getCartList());
    }

    @GetMapping(value = "/list")
    public List<ShoppingCart> list(@RequestParam("userId") @NotBlank String userId) {
        return shoppingCartService.listCart(userId);
    }

    @GetMapping(value = "/addition")
    public Map<String, Object> getCartAddition() {
        return shoppingCartService.getCartAddition();
    }

    @PostMapping(value = "/calc")
    public CalcData calc(@RequestBody @Validated OrderModel orderModel) {
        return shoppingCartService.calcTotal(orderModel);
    }
}
